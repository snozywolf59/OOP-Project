package com.example.learn2.TranslateController;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextToSpeech {
    private final String API_KEY = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";
    private static final String GOOGLE_SYNTHESISER_URL = "https://www.google.com/speech-api/v2/synthesize?enc=mpeg"
                                                        + "&client=chromium";
    private String languageCode = null;

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
    public InputStream getMP3Data(String synthText) throws IOException {

        String languageCode = this.languageCode;//Ensures retention of language settings if set to auto

        if(languageCode == null || languageCode.isEmpty()){
            languageCode = "en-us";
        }

        String encoded = URLEncoder.encode(synthText, StandardCharsets.UTF_8); //Encode

        StringBuilder sb = new StringBuilder(GOOGLE_SYNTHESISER_URL);
        sb.append("&key=" + API_KEY);
        sb.append("&text=").append(encoded);
        sb.append("&lang=").append(languageCode);

        URL url = new URL(sb.toString());

        URLConnection urlConn = url.openConnection();

        urlConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");

        return urlConn.getInputStream();
    }
    public void speak(String text) {
        Thread thread = new Thread(() -> {
            try {
                AdvancedPlayer player = new AdvancedPlayer(this.getMP3Data(text));
                player.play();
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        });

        thread.setDaemon(false);

        thread.start();
    }
}
