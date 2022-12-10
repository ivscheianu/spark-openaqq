package com.ivscheianu.openaqq.reporting;

import static java.util.Objects.isNull;

import com.ivscheianu.openaqq.common.base.exception.JobException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportingJobLauncher {

    private static final byte EXPECTED_ARGS = 5;
    private static final String LAUNCHER_NAME = ReportingJobLauncher.class.getSimpleName();

    public static void main(final String[] args) {
        verifyArgs(args);
        try {
            createReportingJob(args).run();
        } catch (final Exception exception) {
            log.error("[{}] - Failed to complete job for args = {}", LAUNCHER_NAME, args, exception);
            throw exception;
        }
    }

    private static ReportingJob createReportingJob(final String[] args) {
        final String datasetLocation = args[0];
        final String requestBucketName = args[1];
        final String requestKey = args[2];
        final String responseBucketName = args[3];
        final String responseKey = args[4];
        return new ReportingJob(datasetLocation, requestBucketName, requestKey, responseBucketName, responseKey);
    }

    private static void verifyArgs(final String[] args) {
        if (isNull(args) || args.length != EXPECTED_ARGS) {
            log.error("[{}] - Invalid number of args. Expected {} but received = {}", LAUNCHER_NAME, EXPECTED_ARGS, args);
            throw new JobException(
                String.format("Job required %d input parameters: <DATASET_LOCATION>, <REQUEST_BUCKET_NAME>, <REQUEST_KEY>, <RESPONSE_BUCKET_NAME>, <RESPONSE_KEY>", EXPECTED_ARGS)
            );
        }
    }
}
