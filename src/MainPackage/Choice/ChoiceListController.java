/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainPackage.Choice;

import MainPackage.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class ChoiceListController implements Initializable {

    
    @FXML
    private Button exitBtn;

    @FXML
    private Button gameBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button learnBtn;

    @FXML
    private Button searchBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
    }    
    
    private void addListener() {
        homeBtn.setOnAction(e->setSelect("Home"));
        searchBtn.setOnAction(e->setSelect("Search"));
        gameBtn.setOnAction(e->setSelect("Play"));
        learnBtn.setOnAction(e->setSelect("Learn"));
        exitBtn.setOnAction(e->setSelect("Exit"));
    }
    
    private void setSelect(String t) {
        Model.getInstance().getViewFactory().getCurrentSelect().set(t);
    }
    
}
