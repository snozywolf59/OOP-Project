package com.dictionary.Controllers.Content.Home;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class UserRepController {
    @FXML
    private Text userRep;

    public void setData(String userInput) {
        userRep.setText(userInput);
    }
}
