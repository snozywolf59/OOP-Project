package com.dictionaryCommandLine;

import java.util.ArrayList;
import java.util.Comparator;

public class DictionaryCommandLine {
    public static void showAllWords() {
        System.out.printf("%-6s|%-30s|%-40s\n", "No", "English", "Vietnamese");
        int i = 1;
        for (Word word : Dictionary.getInstance()) {
            System.out.printf("%-6d|%-30s|%-40s\n", i++, word.getWord_target(),word.getWord_explain());
        }
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandLine();
        showAllWords();
    }

    public static void main(String[] args) {
        dictionaryBasic();
    }
}
