package com.dictionary.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public final class ViewFactory {
    private final Stage stage = new Stage();
    private final String appName = "Ten App";

    //Trạng thái của chương trình.
    private final StringProperty currentSelect;

    private AnchorPane choiceList;
    private AnchorPane homeView;
    private AnchorPane searchView;
    private AnchorPane playView;
    private ScrollPane learnView;
    private AnchorPane ggTranslateView;

    public ViewFactory() {
        currentSelect = new SimpleStringProperty();
    }

    public StringProperty getCurrentSelect() {
        return currentSelect;
    }

    public AnchorPane getChoiceList() {
        if (choiceList == null) {
            try {
                choiceList = new FXMLLoader(getClass().getResource("/FXML/Content/ChoiceList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return choiceList;
    }

    public AnchorPane getSearchView() {
        if (searchView == null) {
            try {
                searchView = new FXMLLoader(getClass().getResource("/FXML/Content/SearchView.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return searchView;
    }

    public AnchorPane getHomeView() {
        if (homeView == null) {
            try {
                homeView = new FXMLLoader(getClass().getResource("/FXML/Content/Home.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return homeView;
    }

    public ScrollPane getLearnView() {
        if (learnView == null) {
            try {
                learnView = new FXMLLoader(getClass().getResource("/FXML/Content/Learn.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return learnView;
    }
    public AnchorPane getGGTranslateView() {
        if (ggTranslateView == null) {
            try {
                ggTranslateView = new FXMLLoader(getClass().getResource("/FXML/Content/GGTranslate.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ggTranslateView;
    }

    public AnchorPane getPlayView() {
        if (playView == null) {
            try {
                playView = new FXMLLoader(getClass().getResource("/FXML/Content/GameList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return playView;
    }

    public void showWelcome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Welcome.fxml"));

        createStage(loader);
    }

    public void showWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client.fxml"));

        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(appName);

        //Làm cho stage ở giữa màn hình
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Tính toán vị trí x và y để hiển thị Stage ở giữa màn hình
        double X = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double Y = (screenBounds.getHeight() - stage.getHeight()) / 2;

        // Đặt vị trí của Stage
        stage.setX(X);
        stage.setY(Y);

        stage.show();
    }

    public void closeStage() {
        stage.close();
    }
}
