package com.ivscheianu.openaqq.reporting;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.ArrayUtils.nullToEmpty;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.ivscheianu.openaqq.common.base.exception.JobException;
import com.ivscheianu.openaqq.common.base.io.s3.S3ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Slf4j
class PathValidator {

    private final AmazonS3 s3Client = new S3ClientFactory().createClient();

    public String[] validateLocations(final String[] paths) {
        final String[] validLocations = getValidLocations(paths);
        if (isEmpty(validLocations)) {
            final String errorMessage =
                String.format("No valid location found, job aborted. Provided paths = %s", Arrays.toString(paths));
            throw new JobException(errorMessage);
        }
        return validLocations;
    }

    private String[] getValidLocations(final String[] paths) {
        try {
            return Arrays.stream(nullToEmpty(paths))
                .filter(StringUtils::isBlank)
                .distinct()
                .map(AmazonS3URI::new)
                .filter(this::pathExists)
                .map(uri -> uri.getURI().getPath())
                .toArray(String[]::new);
        } catch (final Exception exception) {
            log.error("Failed to validate paths = {}", Arrays.toString(paths), exception);
            throw exception;
        }
    }

    private boolean pathExists(final AmazonS3URI amazonS3URI) {
        return s3Client.listObjectsV2(amazonS3URI.getBucket(), amazonS3URI.getKey()).getKeyCount() > 0;
    }
}
