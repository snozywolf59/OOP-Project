package com.dictionary.Controllers.Content.game;

import com.dictionary.Models.Card.GameCard;
import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class GameCardController implements Initializable {
    @FXML
    private BorderPane pane;

    @FXML
    private ImageView imageView;

    @FXML
    private Button play;

    @FXML
    private Label name;

    public void setCard(GameCard card) {
        Image loadImage = new Image(getClass().getResourceAsStream(card.getLinkToImage()));
        name.setText(card.getContext());
        imageView.setImage(loadImage);
        centerImage();
        pane.setCenter(imageView);
        play.setOnAction(e -> Model.setSelect(card.getLinkToGame()));
    }

    private void centerImage() {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(200);
        imageView.setFitHeight(250);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
