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
    private ChatAI chatAI = new ChatAI();
    private ReWriter reWriter = new ReWriter();

    @FXML
    void actionCheck(ActionEvent event) throws IOException, InterruptedException {
        textResult.setText(checker.check(textOriginal.getText()));
    }
    @FXML
    void chatAI(ActionEvent event) throws IOException, InterruptedException {
        textResult.setText(chatAI.chatAI(textOriginal.getText()));
    }
    @FXML
    void rewrite(ActionEvent event) throws IOException, InterruptedException {
        textResult.setText(reWriter.rewrite(textOriginal.getText()));
    }
}
