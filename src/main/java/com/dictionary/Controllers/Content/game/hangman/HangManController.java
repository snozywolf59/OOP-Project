package com.dictionary.Controllers.Content.game.hangman;

import com.dictionary.Models.Model;
import com.dictionary.Models.game.hangMan.HangmanEngine;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HangManController implements Initializable {
    private HangmanEngine hangmanEngine = new HangmanEngine();

    @FXML
    private ImageView won;

    @FXML
    private ImageView image;

    @FXML
    private TextField input;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    public HangManController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null ;
            } else {
                return change ;
            }
        }));
        reset();
        won.setVisible(false);
    }

    private void reset() {
        hangmanEngine.reset();
        label1.setText("");
        label2.setText("");
        label3.setText("");
        label4.setText("");
        label5.setText("");
        input.setText("");
        Image load = new Image(getClass().getResourceAsStream("/hangman/1.png"));
        image.setImage(load);
    }

    @FXML
    public void replay() {
        hangmanEngine.reset();
        reset();
    }

    @FXML
    public void back() {
        Model.setSelect(ViewFactory.BACK);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        won.setVisible(false);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("enter");
            guess();
        }
    }

    @FXML
    public void untarget() {
        won.setVisible(false);
    }

    private void guess() {
        if (input.getText().isEmpty()) {
            return;
        }
        char guessedLetter = Character.toLowerCase(input.getText().charAt(0));
        input.setText("");
        input.setFocusTraversable(false);
        if (!hangmanEngine.isInWord(guessedLetter)) {
            wrongTurn();
            return;
        }

        hangmanEngine.update(guessedLetter);
        for (int i = 0; i < 5; ++i) {
            if (hangmanEngine.getGivenWord().charAt(i) != guessedLetter) {
                continue;
            }
            switch (i) {
                case 0 -> label1.setText(String.valueOf(Character.toUpperCase(guessedLetter)));
                case 1 -> label2.setText(String.valueOf(Character.toUpperCase(guessedLetter)));
                case 2 -> label3.setText(String.valueOf(Character.toUpperCase(guessedLetter)));
                case 3 -> label4.setText(String.valueOf(Character.toUpperCase(guessedLetter)));
                case 4 -> label5.setText(String.valueOf(Character.toUpperCase(guessedLetter)));
            }
        }
        if (hangmanEngine.win()) {
            won.setVisible(true);
        }
    }

    private void wrongTurn() {
        hangmanEngine.increase();
        String link = "/hangman/" + (hangmanEngine.getFalseMove() + 1) + ".png";
        Image load = new Image(getClass().getResourceAsStream(link));
        image.setImage(load);
        System.out.println(hangmanEngine.getFalseMove());
        if (hangmanEngine.getFalseMove() == HangmanEngine.MAX_MOVES) {

            reset();
        }
    }
}
