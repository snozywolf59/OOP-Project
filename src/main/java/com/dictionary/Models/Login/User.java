package com.dictionary.Models.Login;

import com.dictionary.Models.API.Translator;
import com.dictionary.Models.search.Dictionary;
import com.dictionary.Models.search.Word;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class User {
    private static User instance;

    private User() {
        initFireStore();
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
    private Firestore db;
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

    private void initFireStore() {
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

    public void pullUserData() throws ExecutionException, InterruptedException {
        DocumentReference documentReference = db.collection("User").document(userName);
        DocumentSnapshot docSnap = documentReference.get().get();
        if (docSnap.exists()) {
            this.name = docSnap.getData().get("name").toString();
            this.born = docSnap.getData().get("born").toString();
            this.gmailAddress = docSnap.getData().get("gmail").toString();
        }
    }

    public boolean exists(String userName, String password) {
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

    /**
     * This is for chat-bot.
     */
    private Translator translator = new Translator();

    public void addQAtoFirestore(String category) {
        translator.setFromLanguage("Tiếng Việt");
        translator.setToLanguage("English");

        Scanner sc = new Scanner(System.in);
        System.out.println("question: ");
        String question = sc.nextLine();
        System.out.println("answer: ");
        String answer = sc.nextLine();
        DocumentReference docRef = db.collection("Question-Answer").document(category);
        Map<String, Object> data = new HashMap<>();
        data.put("Category", category);
        try {
            data.put("Question", translator.translate(question));
        } catch (IOException e) {
            System.out.println("Error translate");
        }
        data.put("Answer", answer);

        ApiFuture<WriteResult> result = docRef.set(data);
        try {
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }

    public void addQAtoTXT(String category) {
        String filePath = "src/main/resources/Bin/faq-categorizer.txt";

        // Nội dung mới bạn muốn thêm vào file
        String contentToAppend = "\n";

        DocumentReference docRef = db.collection("Question-Answer").document(category);
        try {
            DocumentSnapshot docSnap = docRef.get().get();
            if (docSnap.exists()) {
                contentToAppend += Objects.requireNonNull(docSnap.get("Category")).toString()
                        + "\t" + Objects.requireNonNull(docSnap.get("Question")).toString();
            }

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error in document snapshot");
        }

        try {
            // Mở file với tham số true để cho phép ghi thêm vào cuối file
            FileWriter fileWriter = new FileWriter(filePath, true);

            // Sử dụng BufferedWriter để ghi nội dung
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Thêm nội dung vào file
            bufferedWriter.write(contentToAppend);

            // Đóng BufferedWriter
            bufferedWriter.close();

            System.out.println("Nội dung đã được thêm vào file thành công.");

        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi thêm nội dung vào file: " + e.getMessage());
        }
    }

    public Map<String, String> getAnswerFromFireStore() {
        Map<String, String> answer = new HashMap<>();
        ApiFuture<QuerySnapshot> query = db.collection("Question-Answer").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error in query snapshot");
        }
        assert querySnapshot != null;
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            answer.put(document.getId(), Objects.requireNonNull(document.get("Answer")).toString());
        }
        return answer;
    }

    public static void main(String[] args) {
        for (int i = 20; i < 30; i++) {
            User.getInstance().addQAtoFirestore("000" + i);
            User.getInstance().addQAtoTXT("000" + i);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
