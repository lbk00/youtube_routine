package com.example.youtube_routine.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

// firebase-service-account.json을 사용하여 Firebase 앱을 초기화
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
//            System.out.println("🟡 [Firebase 초기화 시도]");
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/firebase/firebase-service-account.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
//                System.out.println("Firebase 초기화 완료");
            } else {
//                System.out.println("FirebaseApp 이미 초기화됨 → 재초기화 생략");
            }

        } catch (IOException e) {
//            System.err.println("Firebase 설정 파일 로딩 실패: " + e.getMessage());
        }
    }

}
