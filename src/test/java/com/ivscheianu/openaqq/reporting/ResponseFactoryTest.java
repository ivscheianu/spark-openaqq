package com.ivscheianu.openaqq.reporting;

import static org.junit.jupiter.api.Assertions.*;

import com.ivscheianu.openaqq.TestUtils;
import com.ivscheianu.openaqq.common.base.spark.SparkSessionProvider;
import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import com.ivscheianu.openaqq.reporting.response.ReportingResponse;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

class ResponseFactoryTest {

    private static final SparkSession LOCAL_SPARK_SESSION = SparkSessionProvider.getLocalSparkSession("Testing job");


    private final ClassLoader classLoader = ResponseFactoryTest.class.getClassLoader();

    @ParameterizedTest
    @MethodSource("getResponseTestProvider")
    void getResponseTest(final Dataset<Row> dataset,
                         final ReportingRequest request,
                         final ReportingResponse expectedResponse) {
        //when
        final ReportingResponse result = new ResponseFactory(request).computeResponse(dataset);

        //then
        assertEquals(expectedResponse, result);
    }

    private static Stream<Arguments> getResponseTestProvider() {
        ReportingRequest de20221201 = TestUtils.getRequestFromFile("request/de_2022-12-01-03.json");
        return Stream.of(
            Arguments.of(
                getDataset("dataset/de_2022-12-01.json.deflate", de20221201),
                de20221201,
                null
            )
        );
    }

    private static Dataset<Row> getDataset(final String path, final ReportingRequest request) {
        final File file = TestUtils.getFile(path);
        return new ReportingDatasetProvider(LOCAL_SPARK_SESSION, request).getDataset(file.getAbsolutePath());
    }
}