package com.ivscheianu.openaqq.common.base.io.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3ClientFactory {
    public AmazonS3 createClient() {
        return AmazonS3ClientBuilder
            .standard()
            .build();
    }
}
