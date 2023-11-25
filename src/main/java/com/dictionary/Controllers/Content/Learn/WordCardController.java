package com.dictionary.Controllers.Content.Learn;

import com.dictionary.Models.Card.Card;
import com.dictionary.Models.Card.LoadCard;
import com.dictionary.Models.Card.WordCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class WordCardController implements LoadCard, Initializable {
    @FXML
    private Label word_target;

    @FXML
    private TextArea meaning;

    @Override
    public void setData(Card card) {
        if (!(card instanceof WordCard)) {
            throw new RuntimeException("This is not a word card!!!");
        }
        WordCard wc = (WordCard) card;
        word_target.setText(wc.getContext());
        meaning.setText(wc.getMeaning());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        meaning.setWrapText(true);
    }
}
