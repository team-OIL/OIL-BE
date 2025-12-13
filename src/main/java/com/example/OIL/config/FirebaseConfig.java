package com.example.OIL.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final String FIREBASE_CONFIG_PATH =
            "firebase/oilproject-1747a-firebase-adminsdk-fbsvc-b4b6750a15.json";

    @PostConstruct
    public void initialize() {
        try (InputStream serviceAccount =
                     getClass().getClassLoader().getResourceAsStream(FIREBASE_CONFIG_PATH)) {

            if (serviceAccount == null) {
                throw new IllegalStateException("Firebase 설정 파일을 찾을 수 없습니다: " + FIREBASE_CONFIG_PATH);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Firebase 초기화 실패", e);
        }
    }
}