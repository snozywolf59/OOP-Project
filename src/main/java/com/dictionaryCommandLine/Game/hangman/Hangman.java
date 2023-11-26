package com.dictionaryCommandLine.Game.hangman;

import com.dictionaryCommandLine.AppCommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public final class Hangman {
    private static Hangman hangman;
    private final List<String> words = new ArrayList<>();
    private final String linkData = "src/main/resources/Word/fiveletter.csv";

    private Hangman(){
        loadData();
    }

    public static Hangman getInstance() {
        if (hangman == null) {
            hangman = new Hangman();
        }
        return hangman;
    }

    private final int MAX_TRIES = 10;
    public String getRandWord() {
        int randomNumber = (int) (Math.random() * words.size());
        return words.get(randomNumber);
    }

    public void run() {
        System.out.println(words.size());
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

    private boolean gameLoop() {
        String wordNeedToGuess = getRandWord();
        char[] guessedLetters = new char[wordNeedToGuess.length()];
        Arrays.fill(guessedLetters, '_');
        Set<Character> hasGuessed = new HashSet<>();
        int failed = 0;
        char userGuess;
        while (failed < MAX_TRIES) {
            System.out.println( "Current words: " + new String(guessedLetters));
            System.out.println( "Already guessed: " + hasGuessed);
            System.out.println("You only have " + (MAX_TRIES - failed) + " wrong turns.");
            System.out.println( "Guess a letter: ");
            String input = AppCommandLine.getSc().nextLine();
            while (input.isEmpty() || !Character.isLetter(input.charAt(0))) {
                System.out.println("Please write a letter!");
                input = AppCommandLine.getSc().nextLine();
            }
            userGuess = Character.toLowerCase(input.charAt(0));
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
                System.out.println("You are wrong. The word does not contain " + userGuess + ".");
            }
            Animation.show(failed);
        }
        if (failed == MAX_TRIES) {
            System.out.println("You lose.");
        }
        int choice = endGame();
        return choice == 1;
    }

    private int endGame() {
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

    private boolean isNumber(String in) {
        try {
            Integer.parseInt(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidChoice(int x) {
        return x == 1 || x == 2;
    }

    private boolean isAlreadyGuessed(char letter, Set<Character> hasGuessed) {
        return hasGuessed.contains(letter);
    }

    private boolean isInWord(char letter, String word) {
        return word.indexOf(letter) != -1;
    }

    private void update(char letter, String word, char[] guessedLetters) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedLetters[i] = letter;
            }
        }
    }

    private boolean isWordGuessed(char[] guessedLetters, String word) {
        for (int i = 0; i < guessedLetters.length; ++i) {
            if (guessedLetters[i] !=  word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void loadData() {
        File file = new File(linkData);
        try {
            Scanner fileRead = new Scanner(file);
            while (fileRead.hasNextLine()) {
                words.add(fileRead.nextLine());
            }
            fileRead.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found.");
            System.out.println(fileNotFoundException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(words.size());
    }
}
