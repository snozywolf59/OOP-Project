package com.dictionary.Controllers.Content;

import com.dictionary.Controllers.MediaBackground;
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

    private MediaBackground mediaBackground = new MediaBackground("src/main/resources/Video/HomeBackground.mp4");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoApp.setText("Chào mừng bạn đã đến với UETED");
        mediaBackground.playVideo(mediaView, 0.6);
    }
}
