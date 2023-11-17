package com.dictionary.Controllers.Content;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceListController implements Initializable {

    @FXML
    private ToggleButton exitBtn;

    @FXML
    private ToggleButton gameBtn;

    @FXML
    private ToggleButton homeBtn;

    @FXML
    private ToggleButton learnBtn;

    @FXML
    private ToggleButton searchBtn;

    @FXML
    private ToggleButton apiBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeBtn.setSelected(true);
        addListener();
    }    
    
    private void addListener() {
        homeBtn.setOnAction(e->{
            setSelect("Home");
            HomeController.isSelected = true;
            disableExcept(homeBtn);
        });
        searchBtn.setOnAction(e->{
            setSelect("Search");
            disableExcept(searchBtn);
        });
        gameBtn.setOnAction(e->{
            setSelect("Play");
            disableExcept(gameBtn);
        });
        learnBtn.setOnAction(e->{
            setSelect("Learn");
            disableExcept(learnBtn);
        });
        exitBtn.setOnAction(e->setSelect("Exit"));
        apiBtn.setOnAction(e->{
            setSelect("GoogleTranslate");
            disableExcept(apiBtn);
        });
    }
    
    private void setSelect(String t) {
        Model.getInstance().getViewFactory().getCurrentSelect().set(t);
        if (!t.equals("Home")) Model.getInstance().getViewFactory().resetHome();
    }

    private void disableExcept(ToggleButton x) {
        gameBtn.setSelected(false);
        homeBtn.setSelected(false);
        learnBtn.setSelected(false);
        searchBtn.setSelected(false);
        apiBtn.setSelected(false);
        x.setSelected(true);
    }
}
