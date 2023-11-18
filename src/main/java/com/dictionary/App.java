package com.dictionary;

import com.dictionary.Models.Login.User;
import com.dictionary.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static User user = new User();
    /**
     *  Chạy chương trình.
     * @param stage stage.
     * @throws Exception exception.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showWelcome();
    }
    public static void main(String[] args) throws IOException {
        User.initFireStore();
        launch(args);
    }
}
