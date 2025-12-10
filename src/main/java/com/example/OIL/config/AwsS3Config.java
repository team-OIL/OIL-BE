package com.example.OIL.config;

import com.example.OIL.global.s3.AwsS3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableConfigurationProperties(AwsS3Properties.class)
@RequiredArgsConstructor
public class AwsS3Config {

    private final AwsS3Properties awsS3Properties;

    @Bean
    public S3Client s3Client(AwsS3Properties properties) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                properties.getAccessKey(),
                properties.getSecretKey()
        );

        return S3Client.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
