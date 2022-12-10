package com.ivscheianu.openaqq.reporting;

import com.google.common.collect.ImmutableMap;
import com.ivscheianu.openaqq.common.base.generator.DataGenerator;
import com.ivscheianu.openaqq.common.base.generator.GeneratorResult;
import com.ivscheianu.openaqq.reporting.generator.SummaryDataGenerator;
import com.ivscheianu.openaqq.reporting.generator.TopPeakDataGenerator;
import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import com.ivscheianu.openaqq.reporting.response.ReportingResponse;
import com.ivscheianu.openaqq.reporting.response.SummaryData;
import com.ivscheianu.openaqq.reporting.response.TopPeakData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ResponseFactory {

    private static final ReportingResponse EMPTY_RESPONSE = new ReportingResponse();

    private final ReportingRequest request;

    ReportingResponse computeResponse(final Dataset<Row> dataset) {
        if (dataset.isEmpty()) {
            return EMPTY_RESPONSE;
        }
        final Map<Class<?>, DataGenerator<?>> tasks = getAllTasks(dataset);
        final Map<Class<?>, GeneratorResult<?>> results = launchTasks(tasks);
        return buildResponse(results);
    }

    private Map<Class<?>, DataGenerator<?>> getAllTasks(final Dataset<Row> dataset) {
        return ImmutableMap.<Class<?>, DataGenerator<?>>builder()
            .put(SummaryData.class, new SummaryDataGenerator(dataset))
            .put(TopPeakData.class, new TopPeakDataGenerator(dataset))
            .build();
    }

    private Map<Class<?>, GeneratorResult<?>> launchTasks(final Map<Class<?>, DataGenerator<?>> tasks) {
        final int poolSize = Math.min(Runtime.getRuntime().availableProcessors(), tasks.size());
        final ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        try {
            return tasks
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> executorService.submit(entry.getValue())
                    )
                )
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> getGeneratorResult(entry.getValue())
                    )
                );
        } finally {
            executorService.shutdown();
        }
    }

    @SneakyThrows
    private GeneratorResult<?> getGeneratorResult(Future<? extends GeneratorResult<?>> task) {
        return task.get();
    }

    private ReportingResponse buildResponse(final Map<Class<?>, GeneratorResult<?>> results) {
        return ReportingResponse
            .builder()
            .summaryData(getResult(results, SummaryData.class))
            .topPeakData(getResults(results, TopPeakData.class))
            .build();
    }

    private <E extends Serializable> E getResult(final Map<Class<?>, GeneratorResult<?>> results, final Class<E> resultType) {
        final GeneratorResult<?> generatorResult = results.get(resultType);
        return resultType.cast(generatorResult.getResult());
    }

    private <E extends Serializable> List<E> getResults(final Map<Class<?>, GeneratorResult<?>> results, final Class<E> resultType) {
        final GeneratorResult<?> generatorResult = results.get(resultType);
        return generatorResult
            .getResults()
            .stream()
            .map(resultType::cast)
            .collect(Collectors.toList());
    }
}
