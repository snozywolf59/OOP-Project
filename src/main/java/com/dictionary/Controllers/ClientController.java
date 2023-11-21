package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import com.dictionary.Views.Effect;
import com.dictionary.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private BorderPane parent_pane;

    @FXML
    private AnchorPane grandParentPane;

    public BorderPane getParent_pane() {
        return parent_pane;
    }

    public AnchorPane getGrandParentPane() {
        return grandParentPane;
    }

    /**
     * Initializes the controller class.
     * Chuyển màn hình dựa trên trạng thái của currentSelect ở ViewFactory.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Model.getInstance().getViewFactory().getCurrentSelect().addListener((o, oldValue, newValue) -> {
            switch (newValue) {
                case ViewFactory.LISTENING_TEST -> {
                    Effect.disable(parent_pane);
                    grandParentPane.getChildren().add(Model.getInstance().getViewFactory().getListeningTestView());
                }

                case ViewFactory.BACK -> {
                    grandParentPane.getChildren().removeLast();
                    Effect.enable(parent_pane);
                }
                case ViewFactory.FAVOURITE_LIST -> {
                    Effect.disable(parent_pane);
                    grandParentPane.getChildren().add(Model.getInstance().getViewFactory().getFavoriteWordList());
                }
                case ViewFactory.SEARCH -> parent_pane.setCenter(Model.getInstance().getViewFactory().getSearchView());
                case ViewFactory.PLAY -> parent_pane.setCenter(Model.getInstance().getViewFactory().getPlayView());
                case ViewFactory.EXIT -> Model.getInstance().getViewFactory().closeStage();
                case ViewFactory.LEARN -> parent_pane.setCenter(Model.getInstance().getViewFactory().getLearnView());
                case ViewFactory.API ->
                        parent_pane.setCenter(Model.getInstance().getViewFactory().getGGTranslateView());
                case ViewFactory.SNAKE_GAME -> {
                    Effect.disable(parent_pane);
                    grandParentPane.getChildren().add(Model.getInstance().getViewFactory().getSnakeGame());
                }
                default -> parent_pane.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }

        });
    }
}