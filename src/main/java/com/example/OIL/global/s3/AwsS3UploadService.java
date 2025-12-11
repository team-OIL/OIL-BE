package com.example.OIL.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3UploadService {

    private final S3Client s3Client;
    private final AwsS3Properties properties;

    public String upload(MultipartFile file) {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }

        return "https://" + properties.getBucket()
                + ".s3." + properties.getRegion()
                + ".amazonaws.com/" + fileName;
    }
}
