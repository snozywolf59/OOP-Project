package com.dictionaryCommandLine.Game.hangman;

import com.dictionaryCommandLine.AppCommandLine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Hangman {
    private Hangman(){}

    private static final int MAX_TRIES = 6;
    private static String getRandWord() {
        return "apple";
    }
    public static void run() {
        boolean replay;
        do {
            replay = gameLoop();
        } while (replay);

        System.out.println("""
                Cảm ơn đã chơi.
                """);
    }

    private static boolean gameLoop() {
        String wordNeedToGuess = getRandWord();
        char[] guessedLetters = new char[wordNeedToGuess.length()];
        Arrays.fill(guessedLetters, '_');
        Set<Character> hasGuessed = new HashSet<>();
        int failed = 0;
        char userGuess;
        int choice = 0;
        while (failed < MAX_TRIES) {
            System.out.println( "Hiện tại: " + new String(guessedLetters));
            System.out.println( "Các từ đã đoán: " + hasGuessed);
            System.out.println( "Hãy đoán một chữ: ");
            userGuess = AppCommandLine.getSc().nextLine().charAt(0);
            if (isInWord(userGuess, wordNeedToGuess)) {
                System.out.println("Bạn đã đoán trúng ký tự: " + userGuess);
                if (isAlreadyGuessed(userGuess, hasGuessed)) {
                    System.out.println("Từ này đã được đoán. Hãy chọn từ khác.");
                    continue;
                }
                hasGuessed.add(userGuess);
                if (isInWord(userGuess, wordNeedToGuess)) {
                    update(userGuess, wordNeedToGuess, guessedLetters);
                    if (isWordGuessed(guessedLetters, wordNeedToGuess)) {
                        System.out.println("Chúc mừng bạn đã đoán đúng.");
                        System.out.println("Từ cần đoán là" + wordNeedToGuess);
                    } else {
                        failed++;
                        choice = whenFailed(failed);
                    }
                }
            }
        }

        return choice == 1;
    }

    private static int whenFailed(int failed) {
        System.out.println("Bạn đoán sai từ rồi.");
        System.out.println("Bạn còn được sai lầm " + (MAX_TRIES - failed) + " lần nữa.");
        if (failed == MAX_TRIES) {
            System.out.println("Bạn đã hết lượt chơi.");
            System.out.println("""
                                    Bạn có muốn chơi lại?
                                    [1] Chơi lại.
                                    [2] Nghỉ thôi.
                                    """);
        }
        String choice;
        do {
            choice = AppCommandLine.getSc().nextLine();
        } while (!isNumber(choice) || !isValidChoice(Integer.parseInt(choice)));
        return Integer.parseInt(choice);
    }

    private static boolean isNumber(String in) {
        try {
            Integer.parseInt(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidChoice(int x) {
        return x == 1 || x == 2;
    }

    private static boolean isAlreadyGuessed(char letter, Set<Character> hasGuessed) {
        return hasGuessed.contains(letter);
    }

    private static boolean isInWord(char letter, String word) {
        return word.indexOf(letter) != -1;
    }

    private static void update(char letter, String word, char[] guessedLetters) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedLetters[i] = letter;
            }
        }
    }

    private static boolean isWordGuessed(char[] guessedLetters, String word) {
        for (int i = 0; i < guessedLetters.length; ++i) {
            if (guessedLetters[i] !=  word.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
