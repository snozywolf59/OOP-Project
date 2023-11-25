package com.dictionary.Models.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WordRelationships {
    public Map<String, String> getSynonyms(String wordTarget) {
        Map<String, String> synonyms = new HashMap<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/"
                        + URLEncoder.encode(wordTarget, StandardCharsets.UTF_8).replace("+", "%20")
                        + "/synonyms"))
                .header("X-RapidAPI-Key", "bd5d8adb8cmsh7482d1ccebfd28ep14014ajsn31b2253db85d")
                .header("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Synonym error");
        }
        assert response != null;

        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonObject().get("synonyms").getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            String word = jsonArray.get(i).getAsString();
            synonyms.put(word, getDefinition(word));
        }
        return synonyms;
    }

    public Map<String, String> getAntonyms(String wordTarget) {
        Map<String, String> antonyms = new HashMap<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/"
                        + URLEncoder.encode(wordTarget, StandardCharsets.UTF_8).replace("+", "%20")
                        + "/antonyms"))
                .header("X-RapidAPI-Key", "bd5d8adb8cmsh7482d1ccebfd28ep14014ajsn31b2253db85d")
                .header("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Get antonyms error");
        }
        assert response != null;
        System.out.println(response.body());
        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonObject().get("antonyms").getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            String word = jsonArray.get(i).getAsString();
            antonyms.put(word, getDefinition(word));
        }
        return antonyms;
    }

    private String getDefinition(String word) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/"
                        + URLEncoder.encode(word, StandardCharsets.UTF_8).replace("+","%20") + "/definitions"))
                .header("X-RapidAPI-Key", "bd5d8adb8cmsh7482d1ccebfd28ep14014ajsn31b2253db85d")
                .header("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Get definition error");
        }
        assert response != null;
        StringBuilder definition = new StringBuilder();
        try {
            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonObject().get("definitions").getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                definition.append(jsonArray.get(i).getAsJsonObject().get("definition").getAsString()).append("\n");
            }
        } catch (NullPointerException e) {
            System.out.println(response.body());
        }
        return definition.toString();
    }
}
