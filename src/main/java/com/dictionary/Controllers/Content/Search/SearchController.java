package com.dictionary.Controllers.Content.Search;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class SearchController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private List<String> readTxtFile() {
        ArrayList<Word> words = dictionary.ListWordTxt();
        List<String> listWord = new ArrayList<>();
        for(int i = 0;i < words.size();i++){
            listWord.add(words.get(i).getWordTarget());
        }
        dictionary.SetTreeWord(words);
        return listWord;
    }
    
    private void setTree() {
         ArrayList<Word> wordss = dictionary.ListWordTxt();
         dictionary.SetTreeWord(wordss);
    }
    
    List<String>words = readTxtFile();
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> ListView;
    @FXML
    private Label TargetWord;
    @FXML
    private TextArea  definitionArea;
    @FXML
    private Label pronounceWord;
    @FXML
    private TextArea editTabTextArea;
    @FXML
    private AnchorPane editTab;
    @FXML
    private AnchorPane addNewWord;
    @FXML
    private TextField addTargetWord;
    @FXML
    private TextField addExWord;
    @FXML
    public void search(){
          ListView.getItems().clear();
          ListView.getItems().addAll(searchList(searchField.getText() + "  ", words));
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTree();
        ListView.getItems().addAll(words);//Khởi tạo list ban đầu
        editTab.setVisible(false);
        addNewWord.setVisible(false);
    }
   public List<String> searchList(String searchWords, List<String> listofStrings) {
    List<String> searchWordArray = Arrays.asList(searchWords.trim().split(" "));
    return listofStrings.stream()
            .filter(input -> {
                String lowercaseInput = input.toLowerCase();
                return searchWordArray.stream().allMatch(word -> lowercaseInput.startsWith(word.toLowerCase()));
            })
            .collect(Collectors.toList());
}
    public void onMouseClickListView() {
         String selectedItem = ListView.getSelectionModel().getSelectedItem();
         Word selectWord = dictionary.search(selectedItem);
        if (selectWord != null) {
            TargetWord.setText(selectWord.getWordTarget());
            definitionArea.setText(selectWord.getWordExplain().toString().replaceAll("\\n", "\n"));
            pronounceWord.setText(selectWord.getWordPronoun());
        }
    }
     
    public void onActionEditWord() {
        String selectedWord = ListView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            editTab.setVisible(true);
        }
    }
    
    public void onActionAddWord() {
        addNewWord.setVisible(true);
    }
    
    public void onActionConfirmBtn() {
        String descriptionEdited = editTabTextArea.getText().toLowerCase().trim();
        
        if (!descriptionEdited.equals("")) {
            String selectedWord = TargetWord.getText();
            System.out.print(selectedWord);
            dictionary.editWord(selectedWord, descriptionEdited);
            definitionArea.setText(dictionary.search(selectedWord).getWordExplain().toString().replaceAll("\\n", "\n"));
            editTab.setVisible(false);
            editTabTextArea.clear();
        }
    }
    
    public void onMouseClickedXBtn() {
        editTab.setVisible(false);
        editTabTextArea.clear();
    }
    
    public void onMouseClickedXBtn2() {
        addNewWord.setVisible(false);
        addExWord.clear();
        addTargetWord.clear();
    }
    
    public void onActionConfirmAddBtn() {
        String addTargetWord = this.addTargetWord.getText().toLowerCase().trim();
        String addExWord = this.addExWord.getText();
        StringBuilder addEx = new StringBuilder(addExWord);
        Word newWord = new Word();
        newWord.setWordTarget(addTargetWord);
        newWord.setWordExplain(addEx);
        if (!addExWord.equals("")) {
            dictionary.addWord(newWord);
            words.add(addTargetWord);
            ListView.getItems().add(addTargetWord);
            addNewWord.setVisible(false);
            this.addTargetWord.clear();
            this.addExWord.clear();
        }
    }

    public void onMouseRemoveWord() {
        
    }
    
}
