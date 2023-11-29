package com.dictionary.Controllers.Content.game.hangman;

import com.dictionary.Models.Login.User;
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
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HangManController implements Initializable {
    private final HangmanEngine hangmanEngine = new HangmanEngine();

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

    @FXML
    private Label triesView;

    @FXML
    private Label scoreView;

    @FXML
    private Label highScoreView;

    private final int MIN_CHILDREN = 12;
    private int score = 0;

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
        reset(true);
    }

    private void reset(boolean isLose) {
        highScoreView.setText("High Score: " + User.getInstance().getHighScoreHangman());
        hangmanEngine.reset();
        triesView.setText(String.valueOf((HangmanEngine.MAX_MOVES)));
        label1.setText("");
        label2.setText("");
        label3.setText("");
        label4.setText("");
        label5.setText("");
        input.setText("");
        Image load = new Image(getClass().getResourceAsStream("/hangman/1.png"));
        image.setImage(load);
        if (isLose) {
            setScore(0);
        }
    }

    @FXML
    public void replay() {
        reset(true);
    }

    @FXML
    public void back() {
        Model.setSelect(ViewFactory.BACK);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() != KeyCode.ENTER) {
            return;
        }
        AnchorPane parent = (AnchorPane) image.getParent();
        if (parent.getChildren().size() > MIN_CHILDREN) {
            return;
        }
        if (hangmanEngine.getFalseMove() < HangmanEngine.MAX_MOVES) {
            guess();
        }
    }

    @FXML
    public void untarget() {
    }

    private void guess() {
        if (input == null || input.getText().isEmpty()) {
            System.out.println("Input is invalid");
            throw new RuntimeException("Break at guess, HangManController");
        }
        char guessedLetter = Character.toLowerCase(input.getText().charAt(0));
        input.setText("");
        input.setFocusTraversable(false);
        if (!hangmanEngine.isInWord(guessedLetter)) {
            wrongTurn();
            return;
        }

        int pointForThisLetter= hangmanEngine.update(guessedLetter);
        setScore(score + pointForThisLetter);
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
           setScore(score + 10);
            Image load = new Image(new File("src/main/resources/hangman/gif/victory.gif").toURI().toString());
            AnchorPane parent = (AnchorPane) image.getParent();
            ImageView gifView = new ImageView(load);
            gifView.setOnMouseClicked(e->{
                parent.getChildren().remove(gifView);
                reset(false);
            });
            gifView.setOnKeyPressed(e->{
                if (e.getCode() == KeyCode.ENTER) {
                    parent.getChildren().remove(gifView);
                    reset(false);
                }
            });
            parent.getChildren().add(gifView);
        }
    }

    private void wrongTurn() {
        hangmanEngine.increase();
        triesView.setText(String.valueOf(HangmanEngine.MAX_MOVES - hangmanEngine.getFalseMove()));

        Image load;
        if (hangmanEngine.getFalseMove() < HangmanEngine.MAX_MOVES) {
            String link = "/hangman/" + (hangmanEngine.getFalseMove() + 1) + ".png";
            load = new Image(getClass().getResourceAsStream(link));
            image.setImage(load);
        }
        System.out.println("False move" + hangmanEngine.getFalseMove());
        if (hangmanEngine.getFalseMove() == HangmanEngine.MAX_MOVES) {
            addScoreToCloud();
            load = new Image(new File("src/main/resources/hangman/gif/loss.gif").toURI().toString());
            AnchorPane parent = (AnchorPane) image.getParent();
            ImageView gifView = new ImageView(load);
            gifView.setOnMouseClicked(e->{
                parent.getChildren().remove(gifView);
                reset(true);
            });
            gifView.setOnKeyPressed(e->{
                if (e.getCode() == KeyCode.ENTER) {
                    System.out.println(0);
                    parent.getChildren().remove(gifView);
                    reset(true);
                }
            });
            parent.getChildren().add(gifView);
        }
    }

    private void addScoreToCloud() {
        User.getInstance().setHighScoreHangman(score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        scoreView.setText("SCORE: " + score);
    }
}
