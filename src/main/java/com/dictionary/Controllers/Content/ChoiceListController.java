package com.dictionary.Controllers.Content;

import java.net.URL;
import java.util.ResourceBundle;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
