/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import MainPackage.Choice.ChoiceListController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Hiện bảng chọn và thông tin.
 * @author LENOVO
 */
public final class ViewFactory {
    private Stage stage = new Stage();
    private AnchorPane dashboardView;
    
    private String appName = "Ten App";
    
    public ViewFactory() {
        
    }
    
    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
            dashboardView = new FXMLLoader(getClass().getResource("/Choice/ChoiceList.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }
    
    public void showWelcome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPackage/WelcomeWindow/Welcome.fxml"));
        
        createStage(loader);
    }
    
    public void showChoiceWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPackage/Client.fxml"));
        ChoiceListController choiceListController = new ChoiceListController();
        loader.setController(choiceListController);
        
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
    
    public void closeStage(Stage stage) {
        stage.close();
    }
}
