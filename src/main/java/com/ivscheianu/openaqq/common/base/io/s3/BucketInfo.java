package com.ivscheianu.openaqq.common.base.io.s3;

import lombok.Data;

@Data
public class BucketInfo {
    private final String bucketName;
    private final String key;
}
