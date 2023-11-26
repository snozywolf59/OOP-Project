package com.dictionary.Views;

import com.dictionary.Controllers.Content.Learn.WordCardController;
import com.dictionary.Models.Card.WordCard;
import com.dictionary.Models.Login.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class Effect {
    private Effect() {

    }

    public static final double WINDOW_WIDTH = 1280.0;
    public static final double WINDOW_HEIGHT = 720.0;

    public static boolean mouseClickInside(Node node, MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        double nodeX = node.localToScene(node.getBoundsInLocal()).getMinX();
        double nodeY = node.localToScene(node.getBoundsInLocal()).getMinY();
        double nodeWidth = node.getBoundsInLocal().getWidth();
        double nodeHeight = node.getBoundsInLocal().getHeight();

        return mouseX >= nodeX && mouseX <= nodeX + nodeWidth &&
                mouseY >= nodeY && mouseY <= nodeY + nodeHeight;
    }

    public static void enable(Node x) {
        x.setVisible(true);
        x.setDisable(false);
        x.setOpacity(1.0);
    }

    public static void disable(Node x) {
        x.setVisible(false);
        x.setDisable(true);
    }

    public static void enablePane(Pane x) {
        for (Node y : x.getChildren()) {
            enable(y);
        }
    }

    public static void disablePane(Pane x) {
        for (Node y : x.getChildren()) {
            disable(y);
        }
    }

    public static void addWordCard(Pane wordLayout, WordCard wc) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Effect.class.getResource("/FXML/Content/learn/WordCard.fxml"));
            AnchorPane cardBox = loader.load();
            cardBox.getStylesheets().add(Effect.class.getResource("/Css/learn.css").toExternalForm());
            WordCardController wordCardController = loader.getController();
            wordCardController.setData(wc);
            wordLayout.getChildren().add(cardBox);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void addAll(Pane box, List<WordCard> listWordCard) {
        for (WordCard wc : listWordCard) {
            addWordCard(box, wc);
        }
    }
}
