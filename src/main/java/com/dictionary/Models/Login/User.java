package com.dictionary.Models.Login;

import com.dictionary.Models.API.Translator;
import com.dictionary.Models.FireStore.FireStoreApp;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class User {
    private static User instance;

    private User() {

    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private String userName;
    private String password;
    private String name;
    private String born;
    private String gmailAddress;
    private Map<String, String> favoriteWords = new HashMap<>();

    public Map<String, String> getFavoriteWords() {
        return favoriteWords;
    }

    public String getName() {
        return name;
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

    public void pullUserData() throws ExecutionException, InterruptedException {
        DocumentReference documentReference = FireStoreApp.getInstance().getUser().document(userName);
        DocumentSnapshot docSnap = documentReference.get().get();
        if (docSnap.exists()) {
            this.name = Objects.requireNonNull(docSnap.getData()).get("name").toString();
            this.born = docSnap.getData().get("born").toString();
            this.gmailAddress = docSnap.getData().get("gmail").toString();
        }
    }

    public boolean exists(String userName, String password) {
        try {
            DocumentReference docRef = FireStoreApp.getInstance().getUser().document(userName);
            DocumentSnapshot docSnap = docRef.get().get();
            if (docSnap.exists()) {
                if (Objects.requireNonNull(docSnap.getData()).get("userName").toString().equals(userName)
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
        DocumentReference docRef = FireStoreApp.getInstance().getUser().document(userName);
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
        DocumentReference docRef = FireStoreApp.getInstance().getUser().document(userName).collection("FavoriteWords").document(word);
        add(word, meaning, docRef);
    }

    public void deleteFavoriteWord(String word) {
        DocumentReference docRef = FireStoreApp.getInstance().getUser().document(userName).collection("FavoriteWords").document(word);
        docRef.delete();
    }

    public void readFavoriteWords() {
        favoriteWords.clear();
        ApiFuture<QuerySnapshot> query = FireStoreApp.getInstance().getUser().document(userName).collection("FavoriteWords").get();
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

    public void clear() {
        this.userName = null;
        this.password = null;
        this.name = null;
        this.born = null;
        this.favoriteWords = null;
        this.gmailAddress = null;
    }

    public int getNumberOfFavoriteWords() {
        return favoriteWords.size();
    }

    public void deleteDeletedWord(String word) {
        DocumentReference docRef = FireStoreApp.getInstance().getUser().document(userName).collection("DeletedWords").document(word);
        docRef.delete();
    }

    public void addDeletedWord(String word, String meaning) {
        DocumentReference docRef = FireStoreApp.getInstance().getUser().document(userName).collection("DeletedWords").document(word);
        add(word, meaning, docRef);
    }

    private void add(String word, String meaning, DocumentReference docRef) {
        Map<String, Object> addNewDeletedWord = new HashMap<>();
        addNewDeletedWord.put("Word", word);
        addNewDeletedWord.put("Meaning", meaning);
        ApiFuture<WriteResult> writeResult = docRef.set(addNewDeletedWord, SetOptions.merge());
        try {
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Update error.");
        }
    }



    @Override
    public String toString() {
        return name;
    }
}
