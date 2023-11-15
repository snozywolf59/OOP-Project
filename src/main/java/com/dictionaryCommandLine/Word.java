package com.dictionaryCommandLine;

public class Word implements Comparable<Word> {
    private String word_target;
    private String word_explain;

    public Word(String word_target, String word_explain) {
        this.word_explain = word_explain;
        this.word_target = word_target;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_target(String s) {
        word_target = s;
    }

    public void setWord_explain(String s) {
        word_explain = s;
    }

    @Override
    public int compareTo(Word o) {
        if (this == o) return 0;
        return this.word_target.compareTo(o.word_target);
    }
}