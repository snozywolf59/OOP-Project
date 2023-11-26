package com.dictionary.Models.game.hangMan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HangmanEngine {
    private final ArrayList<String> words = new ArrayList<>();
    public final String linkData = "src/main/resources/Word/fiveletter.csv";
    public static final int MAX_MOVES = 6;
    private int falseMove = 0;
    private String givenWord;
    private char[] currentWord = new char[5];

    public int getFalseMove() {
        return falseMove;
    }

    public void increase() {
        falseMove++;
    }



    public String getGivenWord() {
        return givenWord;
    }

    public char[] getCurrentWord() {
        return currentWord;
    }

    public HangmanEngine() {
        loadData();
        reset();
    }

    public void reset() {
        givenWord = randWord();
        for (int i = 0; i < 5; ++i) {
            currentWord[i] = ' ';
        }
        falseMove = 0;
        System.out.println(givenWord);
    }

    private String randWord() {
        Collections.shuffle(words);
        return words.get(0);
    }

    private void loadData() {
        words.clear();
        try {
            Scanner read = new Scanner(new File(linkData));
            while (read.hasNextLine()) {
                String s = read.nextLine();
                if (s.length() == 5) {
                    words.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isInWord(char x) {
        for (int i = 0; i < givenWord.length(); ++i) {
            if (givenWord.charAt(i) == x) {
                return true;
            }
        }
        return false;
    }

    public void update(char x) {
        for (int i = 0; i < 5; ++i) {
            if (givenWord.charAt(i) == x) {
                currentWord[i] = x;
            }
        }
    }

    public boolean win() {
        for (int i = 0; i < 5; i++) {
            if (currentWord[i] != givenWord.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
