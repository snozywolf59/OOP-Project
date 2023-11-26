package com.dictionary.Controllers.Content.Learn;

import com.dictionary.Models.Card.WordCard;
import com.dictionary.Models.Login.User;
import com.dictionary.Models.Model;
import com.dictionary.Views.Effect;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class DeletedListController implements Initializable {
    @FXML
    HBox wordLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Effect.addAll(wordLayout, loadDeletedList());
    }
    public void back() {
        Model.setSelect(ViewFactory.BACK);
    }

    private List<WordCard> loadDeletedList() {
        User.getInstance().readDeletedWord();
        List<WordCard> lwc = new ArrayList<>();
        for (Map.Entry<String, String> entry : User.getInstance().getDeletedWords().entrySet()) {
            lwc.add(new WordCard(entry.getKey(), entry.getValue()));
        }
        return lwc;
    }
}
