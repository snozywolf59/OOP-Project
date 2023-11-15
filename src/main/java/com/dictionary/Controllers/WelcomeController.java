package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rePassword;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane registerPane;

    static {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        password.setFocusTraversable(false);
        username.setFocusTraversable(false);
        HandleInput.disable(registerPane);
        registerPane.setVisible(false);
    }
    @FXML
    public void untarget() {
        pane.requestFocus();
    }
    @FXML
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

    public void createAccount() {
        createAccount(username.getText(), password.getText());
        closeRegister();
    }

    private void createAccount(String username, String password) {

    }

    public void closeRegister() {
        HandleInput.normalize(pane);
        HandleInput.normalizePane(pane);
        HandleInput.disablePane(registerPane);
        HandleInput.disable(registerPane);
    }
}
