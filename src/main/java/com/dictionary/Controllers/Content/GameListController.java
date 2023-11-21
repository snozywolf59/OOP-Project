package com.dictionary.Controllers.Content;

import java.net.URL;
import java.util.ResourceBundle;

import com.dictionary.Models.Model;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class GameListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void toSnakeGame() {
        Model.setSelect(ViewFactory.SNAKE_GAME);
    }
}
