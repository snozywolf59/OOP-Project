package com.dictionaryCommandLine;

public class WordException extends DictionaryException {

    public WordException(String s) {
        super(s + "is an invalid line for parsing word.\n" + s + "không có định dạng phù hợp.");
    }

    public WordException(String word, String error) {
        super(word + " " + error);
    }
}
