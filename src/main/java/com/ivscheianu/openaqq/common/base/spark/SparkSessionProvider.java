package com.ivscheianu.openaqq.common.base.spark;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.apache.spark.sql.SparkSession;

@UtilityClass
public class SparkSessionProvider {

    public SparkSession getClusterSparkSession(final String appName) {
        return getSparkSession(SessionType.ON_CLUSTER, appName);
    }

    public SparkSession getLocalSparkSession(final String appName) {
        return getSparkSession(SessionType.LOCAL, appName);
    }

    private SparkSession getSparkSession(final SessionType sessionType, final String appName) {
        final SparkSession.Builder builder = SparkSession
            .builder()
            .appName(appName)
            .config("fs.s3a.endpoint", "s3.amazonaws.com")
            .config("spark.sql.files.ignoreMissingFiles", "true");
        if (SessionType.LOCAL.equals(sessionType)) {
            builder.master("local");
        }
        return builder.getOrCreate();
    }

    @Getter
    @RequiredArgsConstructor
    private enum SessionType {
        LOCAL(1),
        ON_CLUSTER(2);
        private final int id;
    }
}
