package com.dictionary.Controllers.Content.game;

import com.dictionary.Models.Card.GameCard;
import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class GameCardController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Button play;

    public void setCard(GameCard card) {
        Image loadImage = new Image(getClass().getResourceAsStream(card.getLinkToImage()));

        image.setImage(loadImage);
        image.setFitHeight(256);
        image.setFitWidth(200);
        play.setOnAction(e -> Model.setSelect(card.getLinkToGame()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
