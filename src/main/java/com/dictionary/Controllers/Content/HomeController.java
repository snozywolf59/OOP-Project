package com.dictionary.Controllers.Content;

import animatefx.animation.BounceInLeft;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Button getHome() {
        return home;
    }

    @FXML
    private Button home;

    public static volatile boolean isSelected = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Thread thread = new Thread(()->{
//            //while (true) {
//                if (isSelected) {
//                    new BounceInLeft(home).play();
//                }
//            //}
//        });
//        thread.setDaemon(false);
//        thread.start();
//        while (true) {
//            if (isSelected) {
//                new BounceInLeft(home).play();
//            }
//        }
    }

    @FXML
    void animation() {
        //new BounceInLeft(home).play();
    }
}
