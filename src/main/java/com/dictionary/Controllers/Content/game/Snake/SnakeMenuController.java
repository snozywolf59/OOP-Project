package com.dictionary.Controllers.Content.game.Snake;

import com.dictionary.Models.Model;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SnakeMenuController implements Initializable {
    public void play() {
        Snake.getInstance().start(new Stage());
    }

    public void instruction() {
    }

    public void back() {
        Model.setSelect(ViewFactory.BACK);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
