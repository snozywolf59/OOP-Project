package com.dictionary.Controllers.Content;

import animatefx.animation.BounceInDown;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Label getHome() {
        return home;
    }

    @FXML
    private Label home;

    public static volatile boolean isSelected = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new BounceInDown(home).play();
    }

    @FXML
    void animation() {
        //new BounceInLeft(home).play();
    }
}
