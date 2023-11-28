package com.dictionary.Controllers.Content;

import com.dictionary.Controllers.Content.game.GameCardController;
import com.dictionary.Models.Card.GameCard;
import com.dictionary.Models.Model;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class GameListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int cnt = 0;
    @FXML
    private HBox listGame;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameCard snakeGame = new GameCard("WORDM", ViewFactory.SNAKE_GAME, "/snake/img/icon.png");
        addGame(snakeGame);
        GameCard hangman = new GameCard("HANGMAN",ViewFactory.HANGMAN, "/hangman/icon.png");
        addGame(hangman);
    }

    private void addGame(GameCard gameCard) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/Content/Game/GameCard.fxml"));
            BorderPane cardBox = loader.load();
            GameCardController gameCardController = loader.getController();
            cardBox.getStylesheets().add(getClass().getResource("/Css/gamelist.css").toExternalForm());
            cardBox.setStyle("-fx-border-radius: 5px;\n" +
                    "    -fx-border-color: green;");
            cardBox.setPadding(new Insets(0, 3, 0, 3));
            gameCardController.setCard(gameCard);
            cardBox.setPrefSize(255, 300);
            listGame.getChildren().add(cardBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
