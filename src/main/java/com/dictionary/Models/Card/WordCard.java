package com.dictionary.Models.Card;

public class WordCard extends Card{
    private String meaning;

    public WordCard(String context, String meaning) {
        setContext(context);
        this.meaning = meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
