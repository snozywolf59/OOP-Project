package com.dictionary.Views;

import com.dictionary.Models.Model;
import com.dictionary.Models.learn.ListeningTest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class ViewFactory {
    private final Stage stage = new Stage();
    private static final String appName = "UETED";

    public static final String HOME = "Home";
    public static final String SEARCH = "Search";
    public static final String PLAY = "Play";
    public static final String EXIT = "Exit";
    public static final String BACK = "Back";
    public static final String LEARN = "Learn";
    public static final String LISTENING_TEST = "ListeningTest";

    public static final String API = "GoogleTranslate";
    public static final String FAVOURITE_LIST = "FavouriteList";
    public static final String DELETED_LIST = "Deleted";
    public static final String SNAKE_GAME = "snake";
    private final StringProperty currentSelect;
    private AnchorPane choiceList;
    private AnchorPane homeView;
    private volatile AnchorPane searchView;
    private AnchorPane playView;
    private ScrollPane learnView;
    private AnchorPane APIView;
    private AnchorPane listeningTestView;
    private AnchorPane favoriteWordList;
    private AnchorPane snakeGame;
    private AnchorPane loading;
    private AnchorPane deletedWordList;

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
            searchView = getLoading();
            Thread thread = new Thread(()->{
                try {
                    searchView = new FXMLLoader(getClass().getResource("/FXML/Content/SearchView.fxml")).load();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            });
            thread.start();
            return searchView;
        }
        return searchView;
    }

    public AnchorPane getLoading() {
        if (loading == null) {
            try {
                loading = new FXMLLoader(getClass().getResource("/FXML/Loading.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loading;
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
    public AnchorPane getAPIView() {
        if (APIView == null) {
            try {
                APIView = new FXMLLoader(getClass().getResource("/FXML/Content/API.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return APIView;
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

    public AnchorPane getFavoriteWordList() {
        if (favoriteWordList == null) {
            try {
                favoriteWordList = new FXMLLoader(getClass().getResource("/FXML/Content/learn/FavouriteList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return favoriteWordList;
    }

    public AnchorPane getDeletedWordList() {
        if (deletedWordList == null) {
            try {
                deletedWordList = new FXMLLoader(getClass().getResource("/FXML/Content/learn/DeletedList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return deletedWordList;
    }

    public void resetHome() {
        //this.homeView = null;
    }
    public void resetFavoriteWordList() {
        this.favoriteWordList = null;
    }

    public AnchorPane getListeningTestView() {
        if (listeningTestView == null) {
            try {
                listeningTestView = new FXMLLoader(getClass().getResource("/FXML/Content/learn/ListeningTest.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
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

    public AnchorPane getSnakeGame() {
        if (snakeGame == null) {
            try {
                snakeGame = new FXMLLoader(getClass().getResource("/FXML/Content/Game/SnakeGame.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return snakeGame;
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
            stage.getIcons().add(new Image(ViewFactory.class.getResourceAsStream("/Images/icon.png")));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        stage.setScene(scene);
        stage.setTitle(appName);

        stage.centerOnScreen();
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void closeStage() {
        ListeningTest.stopAudio();
        stage.close();
    }
}
