package com.dictionary.Controllers.Content.GGTranslate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReWriter {
    public String convertText(String text) {
        String textConvert = "text=";
        for (int i = 0; i < (int) text.length(); i++) {
            if (text.charAt(i) == ' ') {
                textConvert = textConvert + "%20";
            } else if (text.charAt(i) == '?') {
                textConvert = textConvert + "%3F";
            } else {
                textConvert = textConvert + text.charAt(i);
            }
        }
        return textConvert;
    }
    public String getResult(String body) {
        String result = "";
        String minimal = "";
        String informal = "";
        String formal = "";

        for (int i = body.indexOf("minimal") + 10; i < (int) body.length(); i++) {
            if (body.charAt(i) == '"') {
                result = result + "Minimal: " + minimal + "\n";
                break;
            }
            minimal = minimal + body.charAt(i);
        }

        for (int i = body.indexOf("informal") + 11; i < (int) body.length(); i++) {
            if (body.charAt(i) == '"') {
                result = result + "Informal: " + informal + "\n";
                break;
            }
            informal = informal + body.charAt(i);
        }

        for (int i = body.indexOf("formal") + 9; i < (int) body.length(); i++) {
            if (body.charAt(i) == '"') {
                result = result + "Formal: " + formal + "\n";
                break;
            }
            formal = formal + body.charAt(i);
        }
        return result;
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
}
