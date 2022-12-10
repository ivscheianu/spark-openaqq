package com.ivscheianu.openaqq.common.base.dataset;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Optimize the dataset by dropping unused columns, caching the dataset or applying repartition/coalesce
 */
public interface DatasetOptimizer {
    Dataset<Row> optimize(final Dataset<Row> dataset);
}
