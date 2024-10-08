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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class DeletedListController implements Initializable {
    @FXML
    HBox wordLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<WordCard> wordCards = loadDeletedList();
        for (WordCard wc : wordCards) {
            addWordCard(wc);
        }
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
    public void addWordCard(WordCard wc) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Effect.class.getResource("/FXML/Content/learn/WordCard.fxml"));
            AnchorPane cardBox = loader.load();
            VBox box = (VBox) cardBox.getChildren().getFirst();
            Button insertButton = new Button("Xóa");
            insertButton.setOnAction(e -> {
                Label word = (Label) box.getChildren().getFirst();
                wordLayout.getChildren().remove(insertButton.getParent().getParent());
                User.getInstance().deleteDeletedWord(word.getText());
            });
            box.getChildren().add(insertButton);
            cardBox.getStylesheets().add(Effect.class.getResource("/Css/learn.css").toExternalForm());
            WordCardController wordCardController = loader.getController();
            wordCardController.setData(wc);
            wordLayout.getChildren().add(cardBox);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
