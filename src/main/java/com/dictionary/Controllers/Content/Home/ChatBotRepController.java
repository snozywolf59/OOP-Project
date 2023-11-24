package com.dictionary.Controllers.Content.Home;

import com.dictionary.App;
import com.dictionary.Models.Home.ChatBot;
import com.dictionary.Models.Login.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChatBotRepController {
    @FXML
    private Text chatBotRep;

    public void setChatBotRep(String userInput) {
        if (userInput.equals("@@")) {
            chatBotRep.setText("Chào " + User.getInstance().getName() + ", tớ là M, cậu thắc mắc gì thì cứ hỏi mình");
            return;
        }
        String category = ChatBot.getInstance().bestCategory(userInput);
        chatBotRep.setText(ChatBot.getInstance().answer(category));
    }
}
