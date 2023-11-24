package com.dictionary.Controllers.Content;

import animatefx.animation.*;
import com.dictionary.Controllers.Content.Home.ChatBotRepController;
import com.dictionary.Controllers.Content.Home.UserRepController;
import com.dictionary.Models.API.Translator;
import com.dictionary.Models.Model;
import com.dictionaryCommandLine.Game.hangman.Hangman;
import com.dictionaryCommandLine.Word;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonBar.ButtonData.LEFT;
import static javafx.scene.input.KeyCode.*;

public class HomeController implements Initializable {
    private Translator translator = new Translator();

    public static volatile boolean isSelected = false;

    @FXML
    private TextField userMessage;

    @FXML
    private ImageView imageHome;

    @FXML
    private VBox view;

    @FXML
    private Text wordDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        wordDay.setText("Chào mừng bạn đã tới ứng dụng học tiếng anh EDUET!\n"
                + "Từ mới mà chúng ta sẽ học trong ngày hôm nay là: "
                + Hangman.getInstance().getRandWord());

        Thread thread = new Thread(()->{
            Pulse pulse = new Pulse(imageHome);
            pulse.setSpeed(0.5);
            pulse.play();
            pulse.setOnFinished(event -> {
                pulse.setDelay(Duration.seconds(2));
                pulse.play();
            });

            Pulse pulseOther = new Pulse(wordDay);
            pulseOther.setSpeed(0.5);
            pulseOther.play();
            pulseOther.setOnFinished(event -> {
                pulseOther.setDelay(Duration.seconds(2));
                pulseOther.play();
            });
        });
        thread.setDaemon(false);
        thread.start();

        translator.setFromLanguage("Tiếng Việt");
        translator.setToLanguage("English");
        FXMLLoader other = new FXMLLoader();
        other.setLocation(getClass().getResource("/FXML/Content/Home/chat-bot-rep.fxml"));
        HBox chatBotBox = null;
        try {
            chatBotBox = other.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChatBotRepController chatBotRep = other.getController();
        chatBotRep.setChatBotRep("@@");
        view.getChildren().add(chatBotBox);
    }

    @FXML
    void animation() {
        //new BounceInLeft(home).play();
    }

    @FXML
    void sendMessage(ActionEvent event) throws IOException {
        if (userMessage.getText() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Content/Home/user-rep.fxml"));
        HBox userBox = loader.load();
        UserRepController userRep = loader.getController();
        userRep.setData(userMessage.getText());
        view.getChildren().add(userBox);

        FXMLLoader other = new FXMLLoader();
        other.setLocation(getClass().getResource("/FXML/Content/Home/chat-bot-rep.fxml"));
        HBox chatBotBox = other.load();
        ChatBotRepController chatBotRep = other.getController();
        chatBotRep.setChatBotRep(translator.translate(userMessage.getText()));
        view.getChildren().add(chatBotBox);

        userMessage.setText("");
    }
}
