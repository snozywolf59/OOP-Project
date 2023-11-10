package com.dictionary.Controllers.Content.GGTranslate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import java.io.IOException;

public class GGTranslateController {
    @FXML
    private TextArea originalText;

    @FXML
    private TextArea resultText;

    @FXML
    private MenuButton buttonFromLanguage;

    @FXML
    private MenuButton buttonToLanguage;

    private boolean isRecording = false;
    private final TextToSpeech textToSpeech = new TextToSpeech();
    private final Translator translator = new Translator();
    private final Microphone mic = new Microphone(FLACFileWriter.FLAC);
    private Checker checker = new Checker();
    private ChatAI chatAI = new ChatAI();
    private ReWriter reWriter = new ReWriter();
    GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");


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
        resultText.setText(translator.translate(originalText.getText()));
    }

    @FXML
    void speak1(ActionEvent event) {
        textToSpeech.setLanguageCode(translator.getFromLanguage());
        textToSpeech.speak(originalText.getText());
    }

    @FXML
    void speak2(ActionEvent event) {
        textToSpeech.setLanguageCode(translator.getToLanguage());
        textToSpeech.speak(resultText.getText());
    }

    @FXML
    void swap(ActionEvent event) {
        String temp = buttonFromLanguage.getText();
        buttonFromLanguage.setText(buttonToLanguage.getText());
        buttonToLanguage.setText(temp);
        translator.setFromLanguage(buttonFromLanguage.getText());
        translator.setToLanguage(buttonToLanguage.getText());
    }

    void changeRecord() {
        isRecording = !isRecording;
    }

    @FXML
    void doRecord() {
        if (isRecording) {
            stop();
            changeRecord();
        } else {
            record();
            changeRecord();
        }
    }

    @FXML
    void record() {
        duplex.setLanguage("vi");
        new Thread(() -> {
            try {
                duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }).start();
        duplex.addResponseListener(new GSpeechResponseListener() {
            String old_text = "";

            public void onResponse(GoogleResponse gr) {
                String output = "";
                output = gr.getResponse();
                if (gr.getResponse() == null) {
                    if (this.old_text.contains("(")) {
                        this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
                    }
                    System.out.println("Paragraph Line Added");
                    this.old_text = this.old_text.replace(")", "").replace("( ", "");

                    return;
                }
                if (output.contains("(")) {
                    output = output.substring(0, output.indexOf('('));
                }
                if (!gr.getOtherPossibleResponses().isEmpty()) {
                    output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
                }
                originalText.setText(output);
            }
        });
    }

    @FXML
    void stop() {
        mic.close();
        duplex.stopSpeechRecognition();
    }

    @FXML
    void actionCheck() throws IOException, InterruptedException {
        resultText.setText(checker.check(originalText.getText()));
    }
    @FXML
    void chatAI() throws IOException, InterruptedException {
        resultText.setText(chatAI.chatAI(originalText.getText()));
    }
    @FXML
    void rewrite() throws IOException, InterruptedException {
        resultText.setText(reWriter.rewrite(originalText.getText()));
    }
}
