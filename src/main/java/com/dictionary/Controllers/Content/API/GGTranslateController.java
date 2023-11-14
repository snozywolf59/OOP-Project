package com.dictionary.Controllers.Content.API;

import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInUp;
import com.dictionary.Controllers.Content.HomeController;

import com.dictionary.Controllers.MediaBackground;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class GGTranslateController implements Initializable {
    @FXML
    public FontAwesomeIconView recordIcon;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
        Thread thread = new Thread(()->{
            try {
                resultText.setText(translator.translate(originalText.getText()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(false);
        thread.start();
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
        if (!isRecording) {
            recordIcon.setGlyphName("MICROPHONE");
        } else  {
            recordIcon.setGlyphName("STOP");
        }
    }

    @FXML
    void doRecord() {
        if (isRecording) {
            stop();
        } else {
            record();
        }
        changeRecord();
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
        Thread thread = new Thread(()->{
            try {
                resultText.setText(checker.check(originalText.getText()));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(false);
        thread.start();
    }

    @FXML
    void chatAI() throws IOException, InterruptedException {
        Thread thread = new Thread(()->{
            try {
                resultText.setText(chatAI.chatAI(originalText.getText()));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(false);
        thread.start();
    }
    @FXML
    void rewrite() throws IOException, InterruptedException {
        Thread thread = new Thread(()->{
            try {
                resultText.setText(reWriter.rewrite(originalText.getText()));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(false);
        thread.start();
    }
}
