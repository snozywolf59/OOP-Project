package com.dictionary.Controllers.Content.game;

import com.dictionary.Models.Card.GameCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameCardController {

    @FXML
    private ImageView image;

    @FXML
    private Button play;

    public void setCard(GameCard card) {
        Image loadImage = new Image(getClass().getResourceAsStream(card.getLinkToImage()));
        image.setImage(loadImage);
        play.setOnAction(event -> {

        });
    }
}
