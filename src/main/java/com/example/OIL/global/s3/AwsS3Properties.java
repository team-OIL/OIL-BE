package com.example.OIL.global.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws.s3")
public class AwsS3Properties {
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String region;
    private String url;
}