package com.dictionary.Controllers;

import com.dictionary.App;
import com.dictionary.Models.Login.User;
import com.dictionary.Models.Model;
import com.dictionary.Views.Effect;
import com.dictionary.Views.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private BorderPane parent_pane;

    @FXML
    private AnchorPane grandParentPane;

    @FXML
    private Label userName;
    
    @FXML
    private ImageView test;

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
        Thread otherThread = new Thread(()->{
            Duration duration = Duration.seconds(40);
            KeyValue keyValue1 = new KeyValue(test.translateXProperty(), 400);
            KeyValue keyValue2 = new KeyValue(test.translateXProperty(), -400);

            KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);
            KeyFrame keyFrame2 = new KeyFrame(duration, keyValue2);

            Timeline timeline = new Timeline(keyFrame1, keyFrame2);
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Timeline.INDEFINITE);

            // Bắt đầu Timeline
            timeline.play();
        });
        otherThread.setDaemon(false);
        otherThread.start();


        userName.setText(User.getInstance().getName());
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
                        parent_pane.setCenter(Model.getInstance().getViewFactory().getAPIView());
                case ViewFactory.SNAKE_GAME -> {
                    Effect.disable(parent_pane);
                    grandParentPane.getChildren().add(Model.getInstance().getViewFactory().getSnakeGame());
                }
                default -> parent_pane.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }

        });
    }
}