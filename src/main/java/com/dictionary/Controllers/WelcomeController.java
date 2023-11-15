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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enterButton.setOnAction(event-> {
            Model.getInstance().getViewFactory().showWindow();
        });
        mediaView.setFitHeight(510);
        mediaView.setFitWidth(900);
    }
}
