package com.ivscheianu.openaqq.common.base.spark;

import org.apache.spark.sql.SparkSession;

public interface SparkConfigurer {
    void configureSparkSession(final SparkSession sparkSession);
}
