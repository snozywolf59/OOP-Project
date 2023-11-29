package com.dictionary.Controllers.Content;

import com.dictionary.Models.Model;
import com.dictionary.Views.ViewFactory;
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
            setSelect(ViewFactory.HOME);
            HomeController.isSelected = true;
            disableExcept(homeBtn);
        });
        searchBtn.setOnAction(e->{
            setSelect(ViewFactory.SEARCH);
            disableExcept(searchBtn);
        });
        gameBtn.setOnAction(e->{
            setSelect(ViewFactory.PLAY);
            disableExcept(gameBtn);
        });
        learnBtn.setOnAction(e->{
            setSelect(ViewFactory.LEARN);
            disableExcept(learnBtn);
        });
        exitBtn.setOnAction(e->setSelect(ViewFactory.EXIT));
        apiBtn.setOnAction(e->{
            setSelect(ViewFactory.API);
            disableExcept(apiBtn);
        });
    }
    
    private void setSelect(String t) {
        Model.setSelect(t);
        if (!t.equals(ViewFactory.HOME)) Model.getInstance().getViewFactory().resetHome();
        if (!t.equals(ViewFactory.FAVOURITE_LIST)) Model.getInstance().getViewFactory().resetFavoriteWordList();
        if (!t.equals(ViewFactory.DELETED_LIST)) Model.getInstance().getViewFactory().resetDeletedList();
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
