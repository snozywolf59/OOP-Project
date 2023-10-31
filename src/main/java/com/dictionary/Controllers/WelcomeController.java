package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private Button enterButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enterButton.setOnAction(event-> Model.getInstance().getViewFactory().showWindow());
    }
}
