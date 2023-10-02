/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordHandler;

import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;

/**
 *  Dictionary class contains words.
 * @author Bang
 */
public class Dictionary {

    private Vector <Word> dictionary;

    public Dictionary() {
        dictionary = new Vector<>();
    }
    
    

    /**
     * Check if a word is in dictionary.
     * @param word the word need to check.
     * @return index of word in dictionary, -1 if word doesn't exist.
     */
    private int isIn(String word) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getWord_target().equals(word)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Add a word to the dictionary.
     * @param word word added.
     * @param wordMeaning meaning.
     * @return true if adding is success, false if not.
     */
    public boolean addWord(String word, String wordMeaning) {
        if(isIn(word) == -1) {
            Word newWord = new Word(word,wordMeaning);
            dictionary.add(newWord);
            return true;
        }
        return false;
    }

    /**
     * Find meaning of an English word.
     * @param word English word need to find meaning.
     */
    public String getWordMeaning(String word) {
        int positionOfWord = isIn(word);
        if (positionOfWord == -1) {
            return word + " doesn't exist in dictionary.";
        }
        return dictionary.get(positionOfWord).getWord_explain();
    }


    public void showAllWord() {
        System.out.printf("%-6s%-15s%-20s", "No","English","Vietnamese");
        System.out.println("");
        for (int i = 0; i < dictionary.size(); i++) {
            System.out.printf("%-6s%-15s%-20s",Integer.toString(i + 1), dictionary.get(i).getWord_target()
            ,dictionary.get(i).getWord_explain());
            System.out.println("");
        }
    }
}
