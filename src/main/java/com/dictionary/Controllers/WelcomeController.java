package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private Button enterButton;

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            init();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        enterButton.setOnAction(event-> Model.getInstance().getViewFactory().showWindow());
        mediaView.setFitHeight(510);
        mediaView.setFitWidth(900);
    }

    public void init() throws MalformedURLException {
        String filePath = "C:\\Users\\Admin\\Desktop\\Cod_11_11\\OOP-Project\\src\\main\\resources\\Video\\HomeBackground.mp4";
        File file = new File(filePath);

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });
        mediaPlayer.play();
    }
}
