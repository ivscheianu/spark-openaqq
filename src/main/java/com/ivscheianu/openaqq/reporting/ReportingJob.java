package com.ivscheianu.openaqq.reporting;

import com.ivscheianu.openaqq.common.base.io.s3.BucketInfo;
import com.ivscheianu.openaqq.common.base.io.s3.S3ObjectRetriever;
import com.ivscheianu.openaqq.common.base.io.s3.S3ObjectWriter;
import com.ivscheianu.openaqq.common.base.job.Job;
import com.ivscheianu.openaqq.common.base.spark.SparkSessionProvider;
import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import com.ivscheianu.openaqq.reporting.response.ReportingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@Slf4j
@RequiredArgsConstructor
class ReportingJob implements Job {

    private final String datasetLocation;
    private final String requestBucketName;
    private final String requestKey;
    private final String responseBucketName;
    private final String responseKey;

    private static final String REPORTING_JOB_NAME = "ReportingJob";

    @Override
    public void run() {
        final SparkSession sparkSession = SparkSessionProvider.getClusterSparkSession(REPORTING_JOB_NAME);
        final ReportingRequest reportingRequest = new S3ObjectRetriever().retrieve(ReportingRequest.class, new BucketInfo(requestBucketName, requestKey));
        final String[] paths = new PathGenerator(datasetLocation).getDataSourceInputPathsLocations(reportingRequest);
        final String[] validPaths = new PathValidator().validateLocations(paths);
        final Dataset<Row> dataset = new ReportingDatasetProvider(sparkSession, reportingRequest).getDataset(validPaths);
        final ReportingResponse reportingResponse = new ResponseFactory(reportingRequest).computeResponse(dataset);
        new S3ObjectWriter().write(reportingResponse, new BucketInfo(responseBucketName, responseKey));
    }
}
