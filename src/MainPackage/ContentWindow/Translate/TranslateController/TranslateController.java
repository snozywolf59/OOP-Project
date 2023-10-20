package com.example.learn2.TranslateController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class TranslateController {
    @FXML
    private TextField textOld;

    @FXML
    private TextArea textNew;

    @FXML
    private MenuButton buttonFromLanguage;

    @FXML
    private MenuButton buttonToLanguage;

    private TextToSpeech textToSpeech = new TextToSpeech();
    private Translator translator = new Translator();


    @FXML
    void getLangFrom(ActionEvent event) {
        String s = ((MenuItem) event.getSource()).getText();
        buttonFromLanguage.setText(s);
        translator.setFromLanguage(s);
    }

    @FXML
    void getLangTo(ActionEvent event) {
        String s = ((MenuItem) event.getSource()).getText();
        buttonToLanguage.setText(s);
        translator.setToLanguage(s);
    }

    @FXML
    protected void translate() throws IOException {
        textNew.setText(translator.translate(textOld.getText()));
    }

    @FXML
    void speak1(ActionEvent event) {
        textToSpeech.setLanguageCode(translator.getFromLanguage());
        textToSpeech.speak(textOld.getText());
    }

    @FXML
    void speak2(ActionEvent event) {
        textToSpeech.setLanguageCode(translator.getToLanguage());
        textToSpeech.speak(textNew.getText());
    }
}
