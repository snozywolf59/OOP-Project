package com.dictionaryCommandLine;

import com.dictionaryCommandLine.Game.hangman.Hangman;

public class DictionaryCommandLine {
    public static void showAllWords() {
        System.out.printf("%-6s|%-30s|%-40s\n", "No", "English", "Vietnamese");
        int i = 1;
        for (Word word : Dictionary.getInstance()) {
            System.out.printf("%-6d|%-30s|%-40s\n", i++, word.getWord_target(),word.getWord_explain());
        }
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandLine();
        showAllWords();
    }

    public static BoardWord dictionarySearcher() {
        System.out.println("Bạn muốn tìm từ bắt đầu bằng? Nhập: ");
        String searchPart = AppCommandLine.getSc().nextLine();
        BoardWord ls = new BoardWord();
        for (Word word: Dictionary.getInstance()) {
            if (word.getWord_target().startsWith(searchPart)) {
                ls.add(word);
            }
        }
        return ls;
    }

    public static void dictionaryAdvanced() {
        int choice;
        System.out.println("\t\t!!!Welcome to UETED!!!");
        do {
            System.out.println("Press enter to continue.");
            AppCommandLine.getSc().nextLine();
            System.out.println("""
                    [0] Exit
                    [1] Add
                    [2] Remove
                    [3] Update
                    [4] Display
                    [5] Lookup
                    [6] Search
                    [7] Game
                    [8] Import from file
                    [9] Export to file
                    """);
            String s = AppCommandLine.getSc().nextLine();
            while (true) {
                try {
                    choice = Integer.parseInt(s);
                    break;
                } catch(NumberFormatException er) {
                    s = AppCommandLine.getSc().nextLine();
                }
            }
            switch (choice) {
                case 0 -> {
                    System.out.println("Thank you for using!");
                    return;
                }
                case 1 ->
                        DictionaryManagement.insertFromCommandLine();
                case 2 ->
                        DictionaryManagement.deleteWordFromCommandLine();
                case 3 ->
                        DictionaryManagement.changeWordFromCommandLine();
                case 4 ->
                        DictionaryCommandLine.showAllWords();
                case 5 -> {
                    System.out.println("Bạn muốn tìm từ chứa gì? Nhập: ");
                    s = AppCommandLine.getSc().nextLine();
                    System.out.println(DictionaryManagement.dictionaryLookup(s));
                }
                case 6 -> System.out.println(DictionaryCommandLine.dictionarySearcher());
                case 7 -> Hangman.getInstance().run();
                case 8 -> DictionaryManagement.insertFromFile();
                case 9 -> DictionaryManagement.dictionaryExportToFile();
                default ->
                        System.out.println("Action not supported");
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        dictionaryBasic();
    }
}
