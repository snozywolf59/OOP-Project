package com.dictionaryCommandLine;

import java.util.ArrayList;
import java.util.Comparator;

public class DictionaryCommandLine {
    public static void showAllWords(Dictionary dictionary) {
        System.out.printf("%-6s|%-30s|%-40s\n", "No","English","Vietnamese");
        dictionary.sort(Comparator.comparing(Word::getWord_target));
        int i = 1;
        for (Word word : dictionary) {
            System.out.printf("%-6d|%-30s|%-40s\n", i++, word.getWord_target(),word.getWord_explain());
        }
    }

    public static void dictionaryBasic(Dictionary dictionary) {
        DictionaryManagement.insertFromCommandLine(dictionary);
        showAllWords(dictionary);
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionaryBasic(dictionary);
    }
}
