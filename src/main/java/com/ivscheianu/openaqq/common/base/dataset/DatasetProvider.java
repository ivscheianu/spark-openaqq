package com.ivscheianu.openaqq.common.base.dataset;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Provides the initial dataset of the app, usually reading some avro/orc/csv files
 */
public interface DatasetProvider {
    Dataset<Row> getDataset(final String... locations);
}
