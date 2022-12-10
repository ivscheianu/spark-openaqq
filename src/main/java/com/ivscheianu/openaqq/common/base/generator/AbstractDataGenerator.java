package com.ivscheianu.openaqq.common.base.generator;

import com.ivscheianu.openaqq.common.base.rowmapper.AbstractRowMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Row;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public abstract class AbstractDataGenerator<E extends Serializable> implements DataGenerator<E> {

    protected final Dataset<Row> initialDataset;

    @Override
    public GeneratorResult<E> generateData() {
        final List<E> computedData = getPreparedDataset()
            .map(
                getMapper(),
                getEncoder()
            )
            .javaRDD()
            .collect()
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        return new GeneratorResult<>(computedData);
    }

    protected abstract Dataset<Row> getPreparedDataset();

    protected abstract AbstractRowMapper<E> getMapper();

    protected abstract Encoder<E> getEncoder();
}
