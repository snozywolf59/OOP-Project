package com.dictionary.Controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class MediaBackground {
    private MediaPlayer mediaPlayer;
    private Thread thread;
    public MediaBackground(String inputPath) {
        File file = new File(inputPath);
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void playVideo(MediaView mediaView, double opacity) {
        thread = new Thread(() -> {
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setOpacity(opacity);
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.play();
                System.out.println(thread.getPriority());
            });
            mediaPlayer.play();
        });
        thread.setDaemon(false);
        thread.start();
    }
}
