package com.dictionary.Controllers.Content.Grammarly;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Checker {
    private int indexOfDescription;
    private int indexOfBad;
    private int indexOfBetter;
    private int indexOfType;

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

    public static String repairText(String text) {
        String tmp = "";
        for (int i = 0; i < (int) text.length(); i++) {
            if (text.charAt(i) == '\\') {
                i = i + 6;
                tmp = tmp + "'";
            }
            tmp = tmp + text.charAt(i);
        }
        System.out.println(tmp);
        return tmp;
    }

    public String getResult(String body) {
        indexOfDescription = body.indexOf("description", 0);
        indexOfBad = body.indexOf("bad", 0);
        indexOfBetter = body.indexOf("better", 0);
        indexOfType = body.indexOf("type", 0);

        String result = "";

        String description = "";
        String bad = "";
        String better = "";
        String type = "";

        while (true) {
            for (int i = indexOfDescription + 20; i < (int) body.length(); i++) {
                if (body.charAt(i) == '"') {
                    result = result + "Error:\n";
                    result = result + "\tDescription: " + description + "\n";
                    description = "";
                    break;
                }
                description = description + body.charAt(i);
            }
            indexOfDescription = body.indexOf("description", indexOfDescription + 1);

            for (int i = indexOfBad + 6; i < (int) body.length(); i++) {
                if (body.charAt(i) == '"') {
                    result = result + "\tBad: " + bad + "\n";
                    bad = "";
                    break;
                }
                bad = bad + body.charAt(i);
            }
            indexOfBad = body.indexOf("bad", indexOfBad + 1);

            for (int i = indexOfBetter + 10; i < (int) body.length(); i++) {
                if (body.charAt(i) == '"') {
                    result = result + "\tBetter: " + better + "\n";
                    better = "";
                    break;
                }
                better = better + body.charAt(i);
            }
            indexOfBetter = body.indexOf("better", indexOfBetter + 1);

            for (int i = indexOfType + 7; i < (int) body.length(); i++) {
                if (body.charAt(i) == '"') {
                    result = result + "\tType: " + type + "\n";
                    type = "";
                    break;
                }
                type = type + body.charAt(i);
            }
            indexOfType = body.indexOf("type", indexOfType + 1);
            if (indexOfType == -1) break;
        }
        return this.repairText(result);
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
}
