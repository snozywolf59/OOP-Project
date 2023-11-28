package com.dictionary.Models.Card;

public abstract class Card {
    private String context;

    public Card(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
