package com.dictionary.Controllers.Content.API;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ReWriter {
    private String convertText(String text) {
        return "text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    public String rewrite(String text) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://paraphrase-mate.p.rapidapi.com/rewrite"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "f0ed03548cmshc9365222d7c5d9bp1cec3fjsn53227c8fc767")
                .header("X-RapidAPI-Host", "paraphrase-mate.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(this.convertText(text)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return getResult(response.body());
    }

    private String getResult(String body) {
        StringBuilder result = new StringBuilder();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        result.append("There are 3 ways to rewrite your sentence as follows:");
        result.append("\n\t* Minimal: ");
        result.append(jsonObject.get("output").getAsJsonObject().get("minimal"));
        result.append("\n\t* Informal: ");
        result.append(jsonObject.get("output").getAsJsonObject().get("informal"));
        result.append("\n\t* Formal: ");
        result.append(jsonObject.get("output").getAsJsonObject().get("formal"));
        return result.toString();
    }
}
