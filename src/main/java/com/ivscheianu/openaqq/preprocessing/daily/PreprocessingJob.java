package com.ivscheianu.openaqq.preprocessing.daily;

import static com.ivscheianu.openaqq.common.ColumnEnum.COUNTRY;

import com.ivscheianu.openaqq.common.base.job.Job;
import com.ivscheianu.openaqq.common.base.spark.SparkSessionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;

/**
 * Reads the ndjson data, normalizes it and save it in a compressed way
 */

@Slf4j
@RequiredArgsConstructor
public class PreprocessingJob implements Job {

    private static final String PREPROCESSING_JOB_NAME = "PreprocessingJob";

    private final String[] loadLocations;
    private final String saveLocation;

    @Override
    public void run() {
        final SparkSession sparkSession = SparkSessionProvider.getClusterSparkSession(PREPROCESSING_JOB_NAME);
        new PreprocessingDatasetProvider(sparkSession)
            .getDataset(loadLocations)
            .repartition(COUNTRY.getColumn())
            .write()
            .partitionBy(COUNTRY.getName())
            .option("compression", "deflate")
            .json(saveLocation);
    }
}
