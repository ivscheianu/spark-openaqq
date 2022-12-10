package com.ivscheianu.openaqq.common.base.io.s3;

import static com.ivscheianu.openaqq.common.Constants.JSON_EXTENSION;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivscheianu.openaqq.common.base.exception.JobException;
import com.ivscheianu.openaqq.common.base.io.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3ObjectWriter implements ObjectWriter<BucketInfo> {

    private final S3ClientFactory s3ClientFactory = new S3ClientFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> void write(final T object, final BucketInfo saveInfo) {
        try {
            final String json = objectMapper.writeValueAsString(object);
            s3ClientFactory
                .createClient()
                .putObject(saveInfo.getBucketName(), saveInfo.getKey() + JSON_EXTENSION, json);
        } catch (final Exception exception) {
            log.error("Failed to save object = {} with key = {} to s3 bucket = {}", object, saveInfo.getKey(), saveInfo.getBucketName(), exception);
            throw new JobException(exception);
        }
    }
}

