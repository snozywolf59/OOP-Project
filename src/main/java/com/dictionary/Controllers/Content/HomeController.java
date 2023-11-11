package com.dictionary.Controllers.Content;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private MediaView mediaView;
    @FXML
    private TextArea infoApp;
    public static MediaPlayer mediaPlayer;
    private Thread thread;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoApp.setText("Chào mừng bạn đã đến với UETED");
        try {
            init();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() throws MalformedURLException {
        File file = new File("src/main/resources/Video/HomeBackground.mp4");

        Media media = new Media(file.toURI().toString());
        thread = new Thread(() -> {
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setOpacity(0.4);
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.play();
            });
            mediaPlayer.play();
        });
        thread.setDaemon(false);

        thread.start();
    }
}
