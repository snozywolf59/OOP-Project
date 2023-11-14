package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private Button enterButton;

    @FXML
    private MediaView mediaView;

    private final MediaBackground mediaBackground = new MediaBackground("src/main/resources/Video/HomeBackground.mp4");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mediaBackground.playVideo(mediaView,1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        enterButton.setOnAction(event -> Model.getInstance().getViewFactory().showWindow());
        mediaView.setFitHeight(510);
        mediaView.setFitWidth(900);
    }
}
