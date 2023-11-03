package com.dictionary.Controllers.Content.Grammarly;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatAI {
    public String repairText(String text) {
        String tmp = "";
        for (int i = 0; i < (int) text.length(); i++) {
            if (text.charAt(i) == '\\') {
                tmp = tmp + "\n";
                i = i + 1;
                continue;
            }
            tmp = tmp + text.charAt(i);
        }
        return tmp;
    }

    public String getResult(String body) {
        String result = "";
        for (int i = body.indexOf("content") + 11; i < (int) body.length(); i++) {
            if (body.charAt(i) == '"') break;
            result = result + body.charAt(i);
        }
        return this.repairText(result);
    }

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
}
