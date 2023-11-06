package com.dictionary.Controllers.Content.Learn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

public class ListeningTestController implements Initializable {
    private final ListeningTest listeningTest = new ListeningTest();

    @FXML
    private TextArea text;

    @FXML
    private TextArea userAnswer;

    @FXML
    void c17_t1_s1(ActionEvent event) {
        listeningTest.setListeningTest(17, 1, 1);
        text.setText(listeningTest.getExam());
    }

    @FXML
    void c17_t1_s2(ActionEvent event) {
        listeningTest.setListeningTest(17, 1, 2);
        text.setText(listeningTest.getExam());
    }

    @FXML
    void c17_t1_s3(ActionEvent event) {
        listeningTest.setListeningTest(17, 1, 3);
        text.setText(listeningTest.getExam());
    }

    @FXML
    void c17_t1_s4(ActionEvent event) {
        listeningTest.setListeningTest(17, 1, 4);
        text.setText(listeningTest.getExam());
    }

    @FXML
    void playAudio(ActionEvent event) {
        listeningTest.playAudio();
    }

    @FXML
    void stopAudio(ActionEvent event) {
        ListeningTest.stopAudio();
    }

    @FXML
    void submit(ActionEvent event) {
        System.out.println(userAnswer.getText());
        System.out.println(listeningTest.getPoint(userAnswer.getText()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //init();
    }
}