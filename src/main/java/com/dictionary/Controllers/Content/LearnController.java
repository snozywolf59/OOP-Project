package com.dictionary.Controllers.Content;

import com.dictionary.Models.Model;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LearnController implements Initializable {
    @FXML
    private Button enterLisTest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toListeningTest();
    }
    private void toListeningTest() {
        enterLisTest.setOnAction(e->Model.setSelect(ViewFactory.LISTENING_TEST));
    }

    public void toFavorList() {
        Model.setSelect(ViewFactory.FAVOURITE_LIST);
    }
}
