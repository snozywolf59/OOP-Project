package com.dictionary;

import com.dictionary.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    /**
     *  Chạy chương trình.
     * @param stage stage.
     * @throws Exception exception.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showWelcome();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
