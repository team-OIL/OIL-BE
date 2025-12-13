package com.example.OIL.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.base64}")
    private String firebaseBase64;

    @PostConstruct
    public void initialize() {
        try {
            // 1) Base64 문자열 → JSON 바이너리로 변환
            byte[] decodedBytes = Base64.getDecoder().decode(firebaseBase64);

            // 2) InputStream 형태로 Firebase SDK 에 전달
            InputStream serviceAccount = new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Firebase 중복 초기화 방지
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("🔥 Firebase Initialized with Base64 Key");

        } catch (Exception e) {
            throw new RuntimeException("❌ Firebase 초기화 실패: " + e.getMessage(), e);
        }
    }
}