package com.example.demo.config;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.example.demo.config.exception.FirebaseInitException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    void init() throws FirebaseInitException{
        try {
            Resource resource = resourceLoader.getResource("classpath:config/firebase.json");
            FileInputStream serviceAccount =
                new FileInputStream(resource.getFile());
            // GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();// on google cloud
            GoogleCredentials credentials2 = GoogleCredentials.fromStream(serviceAccount);// on other server
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials2)
                .setProjectId("springboot-demo")
                .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            throw new FirebaseInitException("Firebase初始化失败: "+e.getMessage(), e.getCause());
        }
    }

    @Bean
	public Firestore firestore() {
		return FirestoreClient.getFirestore();
	}
}
