package com.dictionary.Models.Card;

public class GameCard extends Card {
    private String linkToGame;
    private String linkToImage;

    public GameCard(String context, String linkToGame, String linkToImage) {
        super(context);
        this.linkToGame = linkToGame;
        this.linkToImage = linkToImage;
    }

    public String getLinkToGame() {
        return linkToGame;
    }

    public void setLinkToGame(String linkToGame) {
        this.linkToGame = linkToGame;
    }


    public String getLinkToImage() {
        return linkToImage;
    }

    public void setLinkToImage(String linkToImage) {
        this.linkToImage = linkToImage;
    }
}
