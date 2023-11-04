package com.dictionary.Controllers.Content.GGTranslate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Checker {

    public Checker() {}

    public static String convertText(String text) {
        String tmp = "text=";
        for (int i = 0; i < (int) text.length(); i++) {
            if (text.charAt(i) == ' ') {
                tmp = tmp + "%20";
            } else if (text.charAt(i) == '?') {
                tmp = tmp + "%3F";
            } else {
                tmp = tmp + text.charAt(i);
            }
        }
        return tmp;
    }

    public String check(String text) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://textgears-textgears-v1.p.rapidapi.com/grammar"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "f0ed03548cmshc9365222d7c5d9bp1cec3fjsn53227c8fc767")
                .header("X-RapidAPI-Host", "textgears-textgears-v1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(this.convertText(text)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        String body = response.body();
        return this.getResult(body);
    }

    private String getResult(String body) {
        StringBuilder result = new StringBuilder();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        JsonObject jsonObject1 = JsonParser.parseString(String.valueOf(jsonObject.get("response"))).getAsJsonObject();
        JsonArray jsonArray = (JsonArray) jsonObject1.get("errors");
        for (int i = 0; i < (int) jsonArray.size(); i++) {
            result.append("Error ").append(i + 1).append(":");
            result.append("\n\tDescription: ");
            result.append((jsonArray.get(i)).getAsJsonObject().get("description").getAsJsonObject().get("en"));
            result.append("\n\tBad: ");
            result.append(jsonArray.get(i).getAsJsonObject().get("bad"));
            result.append("\n\tBetter: ");
            result.append(jsonArray.get(i).getAsJsonObject().get("better"));
            result.append("\n\tType: ");
            result.append(jsonArray.get(i).getAsJsonObject().get("type"));
            result.append("\n");
        }
        if (result.isEmpty()) return "Chúc mừng! Câu của bạn không có lỗi!";
        else return result.toString();
    }
}
