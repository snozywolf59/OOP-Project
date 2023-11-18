package com.dictionary.Controllers.Content.Search;

import com.dictionary.Controllers.HandleInput;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.Math.min;

/**
 * FXML Controller class.
 *
 * @author LENOVO.
 */
public class SearchController implements Initializable {
    private final Dictionary dictionary = new Dictionary();

    private Thread speakingThread;
    private List<String> readTxtFile() {
        ArrayList<Word> words = dictionary.ListWordTxt();
        List<String> listWord = new ArrayList<>();
        for (Word word : words) {
            listWord.add(word.getWordTarget());
        }
        dictionary.SetTreeWord(words);
        return listWord;
    }
    
    private void setTree() {
         ArrayList<Word> words = dictionary.ListWordTxt();
         dictionary.SetTreeWord(words);
    }

    List<String> words = readTxtFile();

    @FXML
    private AnchorPane searchView;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label targetWord;
    @FXML
    private TextArea definitionArea;
    @FXML
    private Label pronounceWord;
    @FXML
    private TextArea editTabTextArea;
    @FXML
    private AnchorPane editTab;
    @FXML
    private AnchorPane delTab;
    @FXML
    private AnchorPane addNewWord;
    @FXML
    private TextField addTargetWord;
    @FXML
    private TextArea addExWord;
    @FXML
    private Label haveNotChoose;
    @FXML
    private TextArea delWord;
    @FXML
    public void search(){
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchField.getText() + "  ", words));
        listView.setPrefHeight(min(250, listView.getItems().size() * 20));
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speakingThread = new Thread();
        listView.getItems().addAll(words);
        setTree();
        definitionArea.setEditable(false);
        specialDisable();
    }

    public boolean isSpeaking() {
        return speakingThread.isAlive();
    }

    public void reset() {
        haveNotChoose.setVisible(false);
    }

    private void specialDisable() {
        editTab.setDisable(true);
        editTab.setVisible(false);
        addNewWord.setVisible(false);
        addNewWord.setDisable(true);
        delTab.setVisible(false);
        delTab.setDisable(true);
        haveNotChoose.setVisible(false);
    }

   public List<String> searchList(String searchWords, List<String> listOfStrings) {
    List<String> searchWordArray = Arrays.asList(searchWords.trim().split(","));
    return listOfStrings.stream()
            .filter(input -> {
                String lowercaseInput = input.toLowerCase();
                return searchWordArray.stream().allMatch(word -> lowercaseInput.startsWith(word.toLowerCase()));
            })
            .collect(Collectors.toList());
}
    public void onMouseClickListView() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        Word selectWord = dictionary.search(selectedItem);
        if (selectWord != null) {
            targetWord.setText(selectWord.getWordTarget());
            definitionArea.setText(selectWord.getWordExplain().toString().replaceAll("\\n", "\n"));
            pronounceWord.setText(selectWord.getWordPronoun());
            haveNotChoose.setVisible(false);
        }
    }
     
    public void onActionEditWord() {
        reset();
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            HandleInput.disablePane(searchView);
            HandleInput.normalize(editTab);
        } else {
            haveNotChoose.setVisible(true);
        }
    }
    
    public void onActionAddWord() {
        reset();
        HandleInput.disablePane(searchView);
        HandleInput.normalize(addNewWord);
    }

    public void onActionDelWord() {
        reset();
        HandleInput.disablePane(searchView);
        HandleInput.normalize(delTab);

    }

    public void confirmDelWord() {
        String word = delWord.getText();
        if(listView.getItems().contains(word)) {
            listView.getItems().remove(word);
            words.remove(word);
            closeDelTab();
            this.delWord.clear();
        }
    }
    /**
     * Xử lí nút ok ở add word.
     */
    public void confirmAddWord() {
        String addTargetWord = this.addTargetWord.getText().toLowerCase().trim();
        String addExWord = this.addExWord.getText();
        StringBuilder addEx = new StringBuilder(addExWord);
        Word newWord = new Word();
        newWord.setWordTarget(addTargetWord);
        newWord.setWordExplain(addEx);
        if (!addExWord.isEmpty()) {
            dictionary.addWord(newWord);
            words.add(addTargetWord);
            listView.getItems().add(addTargetWord);
            closeAddNewWord();
            this.addTargetWord.clear();
            this.addExWord.clear();
        }
    }

    /**
     * Xử lí khi ấn ok ở edit word.
     */
    public void confirmEditWord() {
       //An nut ok o phan editTab
        String descriptionEdited = editTabTextArea.getText().toLowerCase().trim();
        if (!descriptionEdited.isEmpty()) {
            String selectedWord = targetWord.getText();
            System.out.print(selectedWord);
            dictionary.editWord(selectedWord, descriptionEdited);
            definitionArea.setText(dictionary.search(selectedWord).getWordExplain().toString().replaceAll("\\n", "\n"));
            closeEditTab();
            editTabTextArea.clear();
        }
    }

    public void onActionSpeakBtn() {
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        if (!speakingThread.isAlive() && selectedWord != null ) {
            speakingThread = new Thread(()-> Dictionary.textToSpeech(selectedWord));
            speakingThread.start();
        } else if (selectedWord == null) {
            haveNotChoose.setVisible(true);
        }
    }

    public void onActionFavorite() {
        String filePath = "src\\main\\resources\\Word\\FavoriteWord.txt";
        String line = targetWord.getText();
        if(line != null) {
            try (
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.write(line);
                    writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public void closeAddNewWord() {
        HandleInput.normalizePane(searchView);
        specialDisable();
    }

    public void closeEditTab() {
        HandleInput.normalizePane(searchView);
        specialDisable();
    }
    public void closeDelTab() {
        HandleInput.normalizePane(searchView);
        specialDisable();
    }
}
