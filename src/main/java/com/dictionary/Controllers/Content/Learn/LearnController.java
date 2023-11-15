package com.dictionary.Controllers.Content.Learn;

import com.dictionary.Models.Model;
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
        add();
    }
    private void add() {
        enterLisTest.setOnAction(e->setSelect("ListeningTest"));
    }
    private void setSelect(String s) {
        Model.getInstance().getViewFactory().getCurrentSelect().set(s);
    }
}
