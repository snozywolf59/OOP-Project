package com.dictionary.Views;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

}
