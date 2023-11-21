package com.dictionary.Models.game.snake;

import javafx.scene.image.Image;

/**
 * @author Admin
 */
public class FoodWord {
    private char word;
    private Image Image;
    private int foodX;
    private int foodY;

    public FoodWord() {

    }

    public Image getImage() {
        return this.Image;
    }

    public void setImage(String path) {
        this.Image = new Image(getClass().getResourceAsStream(path));
    }

    public int getX() {
        return this.foodX;
    }

    public void setX(int x) {
        this.foodX = x;
    }

    public int getY() {
        return this.foodY;
    }

    public void setY(int y) {
        this.foodY = y;
    }

    public char getWord() {
        return this.word;
    }

    public void setWord(char word) {
        this.word = word;
    }
}
