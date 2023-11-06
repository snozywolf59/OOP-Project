package com.dictionary.Controllers.Content.Learn;

import com.dictionary.Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class LearnController implements Initializable {
    @FXML
    private Button enterLisTest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add();
    }
    private void add() {
        enterLisTest.setOnAction(e->setSelect("ListeningTest"));
    }
    private void setSelect(String s) {
        Model.getInstance().getViewFactory().getCurrentSelect().set(s);
    }
}
