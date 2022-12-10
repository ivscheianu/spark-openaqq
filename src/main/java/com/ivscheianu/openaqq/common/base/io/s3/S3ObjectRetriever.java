package com.ivscheianu.openaqq.common.base.io.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivscheianu.openaqq.common.base.exception.JobException;
import com.ivscheianu.openaqq.common.base.io.ObjectRetriever;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class S3ObjectRetriever implements ObjectRetriever<BucketInfo> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final S3ClientFactory s3ClientFactory = new S3ClientFactory();

    @Override
    public <E> E retrieve(final Class<E> type, final BucketInfo bucketInfo) {
        final AmazonS3 s3Client = s3ClientFactory.createClient();
        final S3Object object = s3Client.getObject(bucketInfo.getBucketName(), bucketInfo.getKey());
        try (final InputStream inputStream = object.getObjectContent()) {
            return objectMapper.readValue(inputStream, type);
        } catch (final Exception exception) {
            log.error("Failed to retrieve object with type = {} from bucket = {}, key = {}", type, bucketInfo.getBucketName(), bucketInfo.getKey(), exception);
            throw new JobException(exception);
        }
    }
}

