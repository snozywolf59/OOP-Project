/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordHandler;

import java.util.Scanner;
/**
 *  Dictionary class contains words.
 * @author Bang
 */
public final class Word {
    private String word_target;
    private String word_explain;
    
    
    /**
     * Show English word.
     * @return English word.
     */
    public String getWord_target() {
        return word_target;
    }
    
    /**
     * Change English word.
     * @param word_target the word change to.
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**
     * Find meaning in Vietnamese.
     * @return meaning in Vietnamese.
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * Change Vietnamese's meaning.
     * @param word_explain new meaning.
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
    
    /**
     * constructor by 1 argument..
     * @param target English word.
     */

    public Word(String target) {
        this.word_target = target;
    }
    
    /**
     * Construct by 2 arguments.
     * @param target English word.
     * @param explain Vietnamese's meaning.
     */
    public Word(String target, String explain) {
        this.word_target = target;
        this.word_explain = explain;
    }
    
    /**
     * Check if a String is word or not.
     * @param word String need to check.
     * @return true if String is valid.
     */
    public static boolean isWord(String word) {
        if (word.length() < 2) return false;
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
