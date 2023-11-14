package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private Button enterButton;

    @FXML
    private MediaView mediaView;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField username;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane pane;

    private final MediaBackground mediaBackground = new MediaBackground("src/main/resources/Video/HomeBackground.mp4");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        password.setFocusTraversable(false);
        username.setFocusTraversable(false);
        try {
            mediaBackground.playVideo(mediaView,1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        enterButton.setOnAction(event -> Model.getInstance().getViewFactory().showWindow());
        mediaView.setFitHeight(510);
        mediaView.setFitWidth(900);
    }

    public void untarget() {
        pane.requestFocus();
    }

    public void login() {
    }

    public void enterButton() {
    }

    public void register() {
    }
}
