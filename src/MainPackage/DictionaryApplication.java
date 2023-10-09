/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author bang
 */
public class DictionaryApplication extends Application{
    /**
     *  Chạy chương trình.
     * @param stage stage.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showWelcome();
    } 
}