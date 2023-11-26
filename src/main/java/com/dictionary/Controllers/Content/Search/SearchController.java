package com.dictionary.Controllers.Content.Search;

import com.dictionary.Models.Card.WordCard;
import com.dictionary.Models.Login.User;
import com.dictionary.Models.search.Dictionary;
import com.dictionary.Models.search.Word;
import com.dictionary.Models.search.WordRelationships;
import com.dictionary.Views.Effect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

/**
 * FXML Controller class.
 *
 * @author LENOVO.
 */
public class SearchController implements Initializable {
    //TODO tương tác hình ảnh với từ trong favourite và trong deleted
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

    private final String notChoose = "(Chưa chọn từ)";

    List<String> words = readTxtFile();

    @FXML
    private Label antonym;
    @FXML
    AnchorPane wordLayout;
    @FXML
    HBox box;
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
        targetWord.setText(notChoose);
        Effect.disable(wordLayout);
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
        Effect.disable(wordLayout);
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
            Effect.disablePane(searchView);
            Effect.enable(editTab);
        } else {
            haveNotChoose.setVisible(true);
        }
    }
    
    public void onActionAddWord() {
        reset();
        Effect.disablePane(searchView);
        Effect.enable(addNewWord);
    }

    public void onActionDelWord() {
        reset();
        Effect.disablePane(searchView);
        Effect.enable(delTab);
    }

    public void confirmDelWord() {
        String word = delWord.getText().trim();
        if(listView.getItems().contains(word)) {
            Word delWord = dictionary.search(word);
            User.getInstance().addDeletedWord(delWord.getWordTarget(), delWord.getWordExplain().toString());
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
        if (targetWord.getText().equals(notChoose)) {
            return;
        }
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        if (!speakingThread.isAlive() && selectedWord != null ) {
            speakingThread = new Thread(()-> Dictionary.textToSpeech(selectedWord));
            speakingThread.start();
        } else if (selectedWord == null) {
            haveNotChoose.setVisible(true);
        }
    }

    public void onActionFavorite() {
        if (targetWord.getText().equals(notChoose)) {
            return;
        }
        User.getInstance().addFavoriteWord(targetWord.getText(), definitionArea.getText());
    }

    public void closeAddNewWord() {
        Effect.enablePane(searchView);
        specialDisable();
    }

    public void closeEditTab() {
        Effect.enablePane(searchView);
        specialDisable();
    }
    public void closeDelTab() {
        Effect.enablePane(searchView);
        specialDisable();
    }

    public void showSyms() {
        if (targetWord.getText().equals(notChoose)) {
            return;
        }
        String word = targetWord.getText();
        antonym.setText("Các từ đồng nghĩa");
        List<WordCard> get = getWordsFromDb(word, WordRelationships.SYM);
        box.getChildren().clear();
        Effect.addAll(box, get);
        Effect.disablePane(searchView);
        Effect.enable(wordLayout);
    }


    private List<WordCard> getWordsFromDb(String word, String type) {
        List<WordCard> lc = new LinkedList<>();
        Map<String, String> symMap;
        if (type.equals(WordRelationships.SYM)) {
            symMap = WordRelationships.getSynonyms(word);
        } else {
            symMap = WordRelationships.getAntonyms(word);
        }
        for (String w : symMap.keySet()) {
            lc.add(new WordCard(w, symMap.get(w)));
        }
        return lc;
    }

    public void showAnms() {
        if (targetWord.getText().equals(notChoose)) {
            return;
        }
        antonym.setText("Các từ trái nghĩa");
        String word = targetWord.getText();
        List<WordCard> get = getWordsFromDb(word, WordRelationships.ANM);
        box.getChildren().clear();
        Effect.addAll(box, get);
        Effect.disablePane(searchView);
        Effect.enable(wordLayout);
    }

    public void close() {
        Effect.enablePane(searchView);
        specialDisable();
    }
}
