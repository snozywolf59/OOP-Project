package com.dictionary.Controllers;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HandleInput {
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

    public static void normalize(Node x) {
        x.setVisible(true);
        x.setDisable(false);
        x.setOpacity(1.0);
    }

    public static void disable(Node x) {
        x.setOpacity(0.3);
        x.setDisable(true);
    }

    public static void normalizePane(Pane x) {
        for (Node y : x.getChildren()) {
            normalize(y);
        }
    }

    public static void disablePane(Pane x) {
        for (Node y : x.getChildren()) {
            disable(y);
        }
    }

}
