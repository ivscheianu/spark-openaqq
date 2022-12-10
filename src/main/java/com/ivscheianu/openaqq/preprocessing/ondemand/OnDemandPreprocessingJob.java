package com.ivscheianu.openaqq.preprocessing.ondemand;

import com.ivscheianu.openaqq.common.base.job.Job;
import com.ivscheianu.openaqq.preprocessing.daily.PreprocessingJob;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OnDemandPreprocessingJob implements Job {

    private final String[] args;

    @Override
    public void run() {
        executeAllPreprocessJobs(args);
    }

    private void executeAllPreprocessJobs(final String[] args) {
        new OnDemandPreprocessingArgsParser()
            .parseArgs(args)
            .forEach(this::executePreprocessJob);
    }

    private void executePreprocessJob(final LoadSaveLocation loadSaveLocation) {
        new PreprocessingJob(loadSaveLocation.getLoadLocations(), loadSaveLocation.getSaveLocation()).run();
    }
}
