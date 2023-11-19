package com.dictionaryCommandLine;

import java.util.ArrayList;

public class BoardWord extends ArrayList<Word> {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("%-6s|%-30s|%-40s\n", "No", "English", "Vietnamese"));
        int i = 1;
        for (Word word : this) {
            sb.append(String.format("%-6d|%-30s|%-40s\n",i++, word.getWord_target(),word.getWord_explain() ));
        }
        return sb.toString();
    }
}
