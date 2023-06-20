package com.ivscheianu.openaqq.reporting;

import com.ivscheianu.openaqq.common.base.dataset.DatasetOptimizer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class ReportingDatasetOptimizer implements DatasetOptimizer {

    private static final int NUMBER_OF_PARTITIONS = 1_000;

    @Override
    public Dataset<Row> optimize(final Dataset<Row> dataset) {
        return dataset
                .repartition(NUMBER_OF_PARTITIONS)
                .cache();
    }
}
