package com.example.learn2.TranslateController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {
    public static final String LANG_AU_ENGLISH = "en-au";
    public static final String LANG_US_ENGLISH = "en-us";
    public static final String LANG_UK_ENGLISH = "en-GB";
    public static final String LANG_VI_VIETNAM = "vi";

    private String fromLanguage;
    private String toLanguage;
    public String setLanguageCode(String stringLanguage) {
        if(stringLanguage.equals("Vietnamese")) return LANG_VI_VIETNAM;
        if(stringLanguage.equals("English")) return LANG_US_ENGLISH;
        return null;
    }

    public void setFromLanguage(String stringLanguage) {
        this.fromLanguage = setLanguageCode(stringLanguage);
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setToLanguage(String stringLanguage) {
        this.toLanguage = setLanguageCode(stringLanguage);
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public Translator() {
        this.fromLanguage = LANG_US_ENGLISH;
        this.toLanguage = LANG_VI_VIETNAM;
    }

    public String translate(String text) throws IOException {
        if (fromLanguage.equals(toLanguage)) return text;
        String urlStr = "https://script.google.com/macros/s/AKfycbwvGZFU2Vr75wlueNO9AqqsPnJSjFGSoYr--arQHi7jCFjHUfcQ62_xEKJpeRhpEDU7/exec"
                + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8)
                + "&target=" + toLanguage
                + "&source=" + fromLanguage;
        System.out.println(urlStr);
        URL url = new URL(urlStr);
        InputStream inputStream = url.openStream();
        StringBuilder response = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        inputStream.close();
        return response.toString();
    }
}
