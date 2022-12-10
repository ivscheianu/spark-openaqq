package com.ivscheianu.openaqq.preprocessing.daily;

import static com.ivscheianu.openaqq.common.Constants.COMMA;
import static java.util.Objects.isNull;

import com.ivscheianu.openaqq.common.base.exception.JobException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreprocessingJobLauncher {

    private static final String LAUNCHER_NAME = PreprocessingJobLauncher.class.getSimpleName();

    private static final byte EXPECTED_ARGS = 2;

    public static void main(final String[] args) {
        verifyArgs(args);
        try {
            createPreprocessingJob(args).run();
        } catch (final Exception exception) {
            log.error("[{}] - Failed to complete job for args = {}", LAUNCHER_NAME, args, exception);
            throw exception;
        }
    }

    private static void verifyArgs(final String[] args) {
        if (isNull(args) || args.length != EXPECTED_ARGS) {
            log.error("[{}] - Invalid number of args. Expected {} but received = {}", LAUNCHER_NAME, EXPECTED_ARGS, args);
            throw new JobException(
                String.format("Job required %d input parameters: <DAILY_DATASET_LOCATION>, <SAVE_LOCATION>", EXPECTED_ARGS)
            );
        }
    }

    private static PreprocessingJob createPreprocessingJob(final String[] args) {
        final String[] loadLocations = args[0].split(COMMA);
        final String saveLocation = args[1];
        return new PreprocessingJob(loadLocations, saveLocation);
    }
}
