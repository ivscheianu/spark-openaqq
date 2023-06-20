package com.ivscheianu.openaqq.preprocessing.ondemand;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;

import com.ivscheianu.openaqq.common.base.exception.JobException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnDemandPreprocessingJobLauncher {

    private static final String LAUNCHER_NAME = OnDemandPreprocessingJobLauncher.class.getSimpleName();

    public static void main(final String[] args) {
        try {
            verifyArgs(args);
            new OnDemandPreprocessingJob(args).run();
        } catch (final Exception exception) {
            log.error("[{}] - Failed to complete on-demand preprocess job for args = {}", LAUNCHER_NAME, args, exception);
            throw exception;
        }
    }

    private static void verifyArgs(final String[] args) {
        if (isEmpty(args)) {
            log.error("[{}] - Invalid number of args. Expected a least 1 argument with form = load_folder -> save_folder", LAUNCHER_NAME);
            throw new JobException(
                "Invalid number of args. Expected a least 1 argument with form = load_folder -> save_folder"
            );
        }
    }
}
