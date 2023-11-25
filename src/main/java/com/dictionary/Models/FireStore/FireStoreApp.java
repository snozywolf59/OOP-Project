package com.dictionary.Models.FireStore;

import com.dictionary.Models.Login.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FireStoreApp {
    private static FireStoreApp instance;
    private Firestore db;

    public Firestore getDB() {
        return db;
    }

    private FireStoreApp() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/Json/ServiceAccount.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore(app);
        } catch (IOException e) {
            System.out.println("Init firestore error");
        }
    }

    public static synchronized FireStoreApp getInstance() {
        if (instance == null) {
            instance = new FireStoreApp();
        }
        return instance;
    }
}
