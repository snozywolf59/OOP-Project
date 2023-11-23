package com.dictionary.Models.Login;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class User {
    private String userName;
    private String password;
    private String name;
    private String born;
    private String gmailAddress;
    private static Firestore db;

    private Map<String, String> favoriteWords = new HashMap<>();

    public User() {};

    public Map<String, String> getFavoriteWords() {
        return favoriteWords;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String userName, String password, String name, String born, String gmailAddress) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.born = born;
        this.gmailAddress = gmailAddress;
    }

    public void setUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static void initFireStore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/Json/ServiceAccount.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore(app);
    }

    public void pullUserData() throws ExecutionException, InterruptedException {
        DocumentReference documentReference = db.collection("User").document(userName);
        DocumentSnapshot docSnap = documentReference.get().get();
        if (docSnap.exists()) {
            this.name = docSnap.getData().get("name").toString();
            this.born = docSnap.getData().get("born").toString();
            this.gmailAddress = docSnap.getData().get("gmail").toString();
        }
    }

    public static boolean exists(String userName, String password) {
        try {
            DocumentReference docRef = db.collection("User").document(userName);
            DocumentSnapshot docSnap = docRef.get().get();
            if (docSnap.exists()) {
                if (docSnap.getData().get("userName").toString().equals(userName)
                && docSnap.getData().get("password").toString().equals(password)) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void createNewUserToFSCloud() throws ExecutionException, InterruptedException {
        if (userName.isEmpty() || password.isEmpty() || name.isEmpty() || born.isEmpty() || gmailAddress.isEmpty()) {
            System.out.println("Error: Empty info");
            return;
        }
        DocumentReference docRef = db.collection("User").document(userName);
        Map<String, Object> data = new HashMap<>();
        data.put("userName", userName);
        data.put("password", password);
        data.put("name", name);
        data.put("born", born);
        data.put("gmail", gmailAddress);
        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public void addFavoriteWord(String word, String meaning) {
        DocumentReference docRef = db.collection("User").document(userName).collection("FavoriteWords").document(word);
        Map<String, Object> addNewFavoriteWord = new HashMap<>();
        addNewFavoriteWord.put("Word", word);
        addNewFavoriteWord.put("Meaning", meaning);
        ApiFuture<WriteResult> writeResult = docRef.set(addNewFavoriteWord, SetOptions.merge());
        try {
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Update error.");
        }
    }

    public void deleteFavoriteWord(String word) {
        DocumentReference docRef = db.collection("User").document(userName).collection("FavoriteWords").document(word);
        docRef.delete();
    }

    public void readFavoriteWords() {
        favoriteWords.clear();
        ApiFuture<QuerySnapshot> query = db.collection("User").document(userName).collection("FavoriteWords").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Query snapshot error.");
        }
        assert querySnapshot != null;
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            favoriteWords.put(document.getString("Word"), document.getString("Meaning"));
        }
        System.out.println(favoriteWords);
    }

    public int getNumberOfFavoriteWords() {
        return favoriteWords.size();
    }

    @Override
    public String toString() {
        return name;
    }
}