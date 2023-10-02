
import WordHandler.Dictionary;
import WordHandler.DictionaryManagement;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Main extends Application{
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        int n;
        Scanner sc = new Scanner(System.in);
        while (true) {
            n = sc.nextInt();
            if (n == 1) {
                DictionaryManagement.insertFromCommandline(dictionary);
            }
            if (n == 2) {
                dictionary.showAllWord();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
