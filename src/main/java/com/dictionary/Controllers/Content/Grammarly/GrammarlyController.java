package com.dictionary.Controllers.Content.Grammarly;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class GrammarlyController {
    @FXML
    private TextArea textOriginal;

    @FXML
    private TextArea textResult;

    private Checker checker = new Checker();

    @FXML
    void actionCheck(ActionEvent event) throws IOException, InterruptedException {
        textResult.setText(checker.check(textOriginal.getText()));
    }
}
