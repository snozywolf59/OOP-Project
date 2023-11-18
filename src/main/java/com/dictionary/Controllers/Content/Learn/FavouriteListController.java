package com.dictionary.Controllers.Content.Learn;

import com.dictionary.Models.Card.WordCard;
import com.dictionary.Models.Model;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
                cardBox.getStylesheets().add(getClass().getResource("/Css/learn.css").toExternalForm());
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
        List<WordCard> lwc = new ArrayList<>();
        lwc.add(new WordCard("a", "aaaaaaa"));
        lwc.add(new WordCard("b", "bbbbbbb"));
        lwc.add(new WordCard("c", "cccccccc"));
        lwc.add(new WordCard("d", "dddddddddd"));
        return lwc;
    }
}
