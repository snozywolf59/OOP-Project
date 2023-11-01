package com.dictionary.Controllers.Content.GGTranslate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GGTranslateController {
    @FXML
    private TextArea textOld;

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

    @FXML
    void swap(ActionEvent event) {
        String temp = buttonFromLanguage.getText();
        buttonFromLanguage.setText(buttonToLanguage.getText());
        buttonToLanguage.setText(temp);
        translator.setFromLanguage(buttonFromLanguage.getText());
        translator.setToLanguage(buttonToLanguage.getText());
    }
}
