/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainPackage.WelcomeWindow;

import MainPackage.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.jws.WebParam;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class WelcomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button enterButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enterButton.setOnAction(event-> Model.getInstance().getViewFactory().showChoiceWindow());
    }    
    
}
