package com.dictionary.Controllers.Content.Home;

import com.dictionary.App;
import com.dictionary.Models.Home.ChatBot;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChatBotRepController {
    @FXML
    private Text chatBotRep;

    public void setChatBotRep(String userInput) {
        String category = ChatBot.getInstance().bestCategory(userInput);
        chatBotRep.setText(ChatBot.getInstance().answer(category));
    }
}
