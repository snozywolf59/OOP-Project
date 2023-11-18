package com.dictionary.Models.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.commons.lang3.StringEscapeUtils;

public class ChatAI {
    public String chatAI(String askRequest) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-best-price.p.rapidapi.com/v1/chat/completions"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "f0ed03548cmshc9365222d7c5d9bp1cec3fjsn53227c8fc767")
                .header("X-RapidAPI-Host", "chatgpt-best-price.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "{\r\n" +
                                "    \"model\": \"gpt-3.5-turbo\",\r\n" +
                                "    \"messages\": [\r\n" +
                                "        {\r\n" +
                                "            \"role\": \"user\",\r\n" +
                                "            \"content\": \"" + askRequest + "\"\r\n" +
                                "        }\r\n" +
                                "    ]\r\n}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return getResult(response.body());
    }

    private String getResult(String body) {
        StringBuilder result = new StringBuilder();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        JsonArray jsonArray = (JsonArray) jsonObject.get("choices");
        for (int i = 0; i < jsonArray.size(); i++) {
            result.append("Message from ");
            result.append(jsonArray.get(i).getAsJsonObject().get("message").getAsJsonObject().get("role"));
            result.append(":\n");
            result.append(jsonArray.get(i).getAsJsonObject().get("message").getAsJsonObject().get("content"));
        }
        return StringEscapeUtils.unescapeJava(result.toString());
    }
}
