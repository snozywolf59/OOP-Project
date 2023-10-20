/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainPackage.ContentWindow.SearchWindow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class SearchController implements Initializable {
    
    List<String> words = readTxtFile("src\\WordHandler\\VieToEngDictionary.txt");
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;

    
    //Đọc txt
    private List<String> readTxtFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public void search() {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchField.getText(), words));

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        listView.getItems().addAll(words);//Khởi tạo list ban đầu
    }

    public List<String> searchList(String searchWords, List<String> listofStrings) {
        List<String> searchWordArray = Arrays.asList(searchWords.trim().split(" "));

        return listofStrings.stream().filter(input -> {
            return searchWordArray.stream().allMatch(word
                    -> input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
}
