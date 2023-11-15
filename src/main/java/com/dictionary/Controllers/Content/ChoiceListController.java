package com.dictionary.Controllers.Content;

import com.dictionary.Controllers.Content.API.GGTranslateController;
import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    private Button ggTranslate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
    }    
    
    private void addListener() {
        homeBtn.setOnAction(e->{
            setSelect("Home");
            HomeController.isSelected = true;
        });
        searchBtn.setOnAction(e->setSelect("Search"));
        gameBtn.setOnAction(e->setSelect("Play"));
        learnBtn.setOnAction(e->setSelect("Learn"));
        exitBtn.setOnAction(e->setSelect("Exit"));
        ggTranslate.setOnAction(e->{
            setSelect("GoogleTranslate");
        });
    }
    
    private void setSelect(String t) {
        Model.getInstance().getViewFactory().getCurrentSelect().set(t);
        if (!t.equals("Home"))Model.getInstance().getViewFactory().resetHome();
    }
}
