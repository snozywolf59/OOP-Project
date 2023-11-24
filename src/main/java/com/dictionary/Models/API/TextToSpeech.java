package com.dictionary.Models.API;

import com.darkprograms.speech.synthesiser.SynthesiserV2;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextToSpeech {
    private Thread speakThread;

    private boolean isSpeaking() {
        return speakThread.isAlive();
    }

    public void speak(String text) {
        if (speakThread == null || !isSpeaking()) {
            speakThread = new Thread(() -> {
                try {
                    AdvancedPlayer player = new AdvancedPlayer(new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw").getMP3Data(text));
                    player.play();
                } catch (IOException | JavaLayerException e) {
                    System.out.println(e.getMessage());
                }
            });

            speakThread.setDaemon(false);

            speakThread.start();
        }
    }
}
