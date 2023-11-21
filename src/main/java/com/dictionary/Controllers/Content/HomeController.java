package com.dictionary.Controllers.Content;

import animatefx.animation.BounceInDown;
import com.dictionary.Controllers.Content.Home.ChatBotRepController;
import com.dictionary.Controllers.Content.Home.UserRepController;
import com.dictionary.Models.API.Translator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private Translator translator = new Translator();

    public Label getHome() {
        return home;
    }

    @FXML
    private Label home;

    public static volatile boolean isSelected = false;

    @FXML
    private TextField userMessage;

    @FXML
    private VBox view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new BounceInDown(home).play();
        translator.setFromLanguage("Tiếng Việt");
        translator.setToLanguage("English");
    }

    @FXML
    void animation() {
        //new BounceInLeft(home).play();
    }

    @FXML
    void sendMessage(ActionEvent event) throws IOException {
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
