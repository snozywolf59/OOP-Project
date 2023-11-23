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
        System.out.println("""
                You have to guess a word which computer give. Its length is 5.
                Once per turn, you can guess a letter.
                If it is in the word, all character is equal to it will appear.
                You must guess the word in 6 turns.
                Else you will lose.
                """);
        boolean replay;
        do {
            replay = gameLoop();
        } while (replay);

        System.out.println("""
                Thanks for playing.
                """);
    }

    private static boolean gameLoop() {
        String wordNeedToGuess = getRandWord();
        char[] guessedLetters = new char[wordNeedToGuess.length()];
        Arrays.fill(guessedLetters, '_');
        Set<Character> hasGuessed = new HashSet<>();
        int failed = 0;
        char userGuess;
        while (failed < MAX_TRIES) {
            System.out.println( "Current words: " + new String(guessedLetters));
            System.out.println( "Already guessed: " + hasGuessed);
            System.out.println( "Guess a letter: ");
            userGuess = AppCommandLine.getSc().nextLine().charAt(0);
            if (isAlreadyGuessed(userGuess, hasGuessed)) {
                System.out.println("This letter has been guessed. Please choose another letter.");
                continue;
            }
            if (isInWord(userGuess, wordNeedToGuess)) {
                System.out.println("You are true: " + userGuess);
                hasGuessed.add(userGuess);
                update(userGuess, wordNeedToGuess, guessedLetters);
                if (isWordGuessed(guessedLetters, wordNeedToGuess)) {
                    System.out.println("Congratulation. You won!");
                    System.out.println("This word is " + wordNeedToGuess + ".");
                    break;
                }
            } else {
                failed++;
                System.out.println("You are wrong.");
                System.out.println("You only have " + (MAX_TRIES - failed) + " wrong turns.");
            }
        }
        if (failed == MAX_TRIES) {
            System.out.println("You lose.");
        }
        int choice = whenFailed(failed);
        return choice == 1;
    }

    private static int whenFailed(int failed) {
        System.out.println("""
                                Bạn có muốn chơi lại?
                                [1] Chơi lại.
                                [2] Nghỉ thôi.
                                """);
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
