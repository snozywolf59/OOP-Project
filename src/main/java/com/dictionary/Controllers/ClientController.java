package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private BorderPane parent_pane;
    /**
     * Initializes the controller class.
     * Chuyển màn hình dựa trên trạng thái của currentSelect ở ViewFactory.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Model.getInstance().getViewFactory().getCurrentSelect().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> o, String oldValue, String newValue) {
                switch (newValue) {
                    case "Search":
                        parent_pane.setCenter(Model.getInstance().getViewFactory().getSearchView());
                        break;
                    case "Play":
                        parent_pane.setCenter(Model.getInstance().getViewFactory().getPlayView());
                        break;
                    case "Exit":
                        Model.getInstance().getViewFactory().closeStage();
                        break;
                    case "Learn":

                        break;
                    case "GoogleTranslate":
                        parent_pane.setCenter(Model.getInstance().getViewFactory().getGGTranslateView());
                        break;
                    case "Home":
                    default:
                        parent_pane.setCenter(Model.getInstance().getViewFactory().getHomeView());

                }
            }
        });
    }

}