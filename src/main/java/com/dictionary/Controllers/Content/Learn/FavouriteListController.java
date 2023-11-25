package com.dictionary.Controllers.Content.Learn;

import com.dictionary.App;
import com.dictionary.Models.Card.WordCard;
import com.dictionary.Models.Login.User;
import com.dictionary.Models.Model;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FavouriteListController implements Initializable {
    private List<WordCard> favourite;
    @FXML
    private HBox wordLayout;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        favourite = getFavorite();
        try {
            for (WordCard wc : favourite) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/Content/learn/WordCard.fxml"));
                AnchorPane cardBox = loader.load();
                VBox box = (VBox) cardBox.getChildren().getFirst();
                Button deleteButton = new Button("Xóa");
                deleteButton.setOnAction(e -> {
                    Label word = (Label) box.getChildren().getFirst();
                    wordLayout.getChildren().remove(deleteButton.getParent().getParent());
                    User.getInstance().deleteFavoriteWord(word.getText());
                });
                box.getChildren().add(deleteButton);
                cardBox.getStylesheets().add(getClass().getResource("/Css/learn.css").toExternalForm());
                System.out.println(cardBox.getChildren().getFirst() instanceof VBox);
                WordCardController wordCardController = loader.getController();
                wordCardController.setData(wc);
                wordLayout.getChildren().add(cardBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void back() {
        Model.setSelect(ViewFactory.BACK);
    }

    private List<WordCard> getFavorite() {
        User.getInstance().readFavoriteWords();
        List<WordCard> lwc = new ArrayList<>();
        for (Map.Entry<String, String> entry : User.getInstance().getFavoriteWords().entrySet()) {
            lwc.add(new WordCard(entry.getKey(), entry.getValue()));
        }
        return lwc;
    }
}
