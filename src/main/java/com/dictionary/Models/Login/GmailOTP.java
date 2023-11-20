package com.dictionary.Models.Login;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GmailOTP {
    private String gmail;

    public GmailOTP(String gmail) {
        this.gmail = gmail;
    }

    public String getAuthenticationCode() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < gmail.length(); i++) {
            if (gmail.charAt(i) == '@') break;
            text.append(gmail.charAt(i));
        }
        System.out.println(text);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://email-authentication-system.p.rapidapi.com/?recipient="+ text.toString() + "%40gmail.com&app=UETED%20APPLICATION"))
                .header("X-RapidAPI-Key", "bd5d8adb8cmsh7482d1ccebfd28ep14014ajsn31b2253db85d")
                .header("X-RapidAPI-Host", "email-authentication-system.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("No response in get authentication code.");
            throw new RuntimeException(e);
        }
        return this.getResult(response.body());
    }

    private String getResult(String body) {
        StringBuilder result = new StringBuilder();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        result.append(jsonObject.get("authenticationCode").toString());
        result.deleteCharAt(0);
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }
}
