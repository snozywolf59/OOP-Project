package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    @FXML
    private AnchorPane registerPane;

    private final MediaBackground mediaBackground = new MediaBackground("src/main/resources/Video/HomeBackground.mp4");

    static {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        password.setFocusTraversable(false);
        username.setFocusTraversable(false);
        HandleInput.disable(registerPane);
        registerPane.setVisible(false);
        try {
            mediaBackground.playVideo(mediaView,1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mediaView.setFitHeight(510);
        mediaView.setFitWidth(900);
    }

    public void untarget() {
        pane.requestFocus();
    }

    public void login() {
        String name = username.getText();
        String pass = password.getText();
    }

    public void enter() {
        Model.getInstance().getViewFactory().showWindow();
    }

    public void register() {
        HandleInput.disablePane(pane);
        HandleInput.normalize(registerPane);
        HandleInput.normalizePane(registerPane);
    }
}
