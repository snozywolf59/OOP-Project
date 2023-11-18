package com.dictionary.Models.search;

/**
 *
 * @author Admin
 */
import java.security.PublicKey;

public class Word {
    private String wordTarget;
    private StringBuilder wordExplain;
    private String wordPronoun;

    public Word() {

    }

    public Word(String wordTarget, StringBuilder wordExplain, String wordPronoun) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.wordPronoun = wordPronoun;
    }

    public String getWordTarget() {
        return this.wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public StringBuilder getWordExplain() {
        return this.wordExplain;
    }

    public void setWordExplain(StringBuilder wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getWordPronoun() {
        return this.wordPronoun;
    }

    public void setWordPronoun(String wordPronoun) {
        this.wordPronoun = wordPronoun;
    }

}
