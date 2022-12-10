package com.ivscheianu.openaqq.common.base.generator;


import lombok.SneakyThrows;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.Serializable;
import java.util.concurrent.Callable;

public interface DataGenerator<E extends Serializable> extends Callable<GeneratorResult<E>> {

    @Override
    @SneakyThrows
    default GeneratorResult<E> call() {
        return generateData();
    }

    Dataset<Row> getInitialDataset();

    GeneratorResult<E> generateData();
}
