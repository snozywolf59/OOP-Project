package com.dictionary.Views;

import com.dictionary.Controllers.Content.Learn.ListeningTest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public final class ViewFactory {
    private final Stage stage = new Stage();
    private final String appName = "Ten App";

    private final StringProperty currentSelect;
    private AnchorPane choiceList;
    private AnchorPane homeView;
    private AnchorPane searchView;
    private AnchorPane playView;
    private ScrollPane learnView;
    private AnchorPane ggTranslateView;
    private AnchorPane listeningTestView;

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
                ggTranslateView = new FXMLLoader(getClass().getResource("/FXML/Content/API.fxml")).load();
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

    public AnchorPane getListeningTestView() {
        if (listeningTestView == null) {
            try {
                listeningTestView = new FXMLLoader(getClass().getResource("/FXML/Content/ListeningTest.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listeningTestView;
    }

    public void showWelcome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Welcome.fxml"));
        createStage(loader);
        stage.setResizable(false);
        stage.setFullScreen(false);
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

        stage.setScene(scene);
        stage.setTitle(appName);

        stage.centerOnScreen();
        stage.show();
    }

    public void closeStage() {
        ListeningTest.stopAudio();
        stage.close();
    }
}
