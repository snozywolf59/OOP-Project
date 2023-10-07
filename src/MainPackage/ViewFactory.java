/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import MainPackage.Choice.ChoiceListController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Hiện bảng chọn và thông tin.
 * @author LENOVO.
 */
public final class ViewFactory {
    private final Stage stage = new Stage();
    private final String appName = "Ten App";
    
    private final StringProperty currentSelect;
    
    private AnchorPane choiceList;
    private AnchorPane homeView;
    private AnchorPane searchView;
    private AnchorPane playView;
    
    
    
    public ViewFactory() {
        currentSelect = new SimpleStringProperty();
    }
    
    public StringProperty getCurrentSelect() {
        return currentSelect;
    }
    
    public AnchorPane getChoiceList() {
        if (choiceList == null) {
            try {
            choiceList = new FXMLLoader(getClass().getResource("/MainPackage/Choice/ChoiceList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return choiceList;
    }
    
    public AnchorPane getSearchView() {
        if (searchView == null) {
            try {
            searchView = new FXMLLoader(getClass().getResource("/MainPackage/ContentWindow/SearchWindow/Search.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return searchView;
    }
    
    public AnchorPane getHomeView() {
        if (homeView == null) {
            try {
            homeView = new FXMLLoader(getClass().getResource("/MainPackage/ContentWindow/HomeWindow/Home.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return homeView;
    }
    
    public AnchorPane getPlayView() {
        if (playView == null) {
            try {
            playView = new FXMLLoader(getClass().getResource("/MainPackage/ContentWindow/PlayWindow/GameList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return playView;
    }
    
    public void showWelcome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPackage/WelcomeWindow/Welcome.fxml"));
        
        createStage(loader);
    }
    
    public void showWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPackage/Client.fxml"));
        
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
