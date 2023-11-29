package com.dictionaryCommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class DictionaryManagement {
    public static final String linkDictionary = "src/main/resources/Word/dictionaries.txt";
    public static void insertFromCommandLine() {
        System.out.print("Nhập số từ cần thêm: n = ");
        final int n = AppCommandLine.getSc().nextInt();
        AppCommandLine.getSc().nextLine();
        System.out.println( "Nhập n từ cần thêm theo mẫu: " +
                            "Dòng 1 là từ tiếng Anh, " +
                            "dòng 2 là từ tiếng Việt:");
        String word_target, word_explain;
        for (int i = 0; i < n; ++i) {
            System.out.print("Nhập từ tiếng Anh: ");
            word_target = AppCommandLine.getSc().nextLine();
            System.out.println();
            System.out.println("Nhập từ tiếng Việt: ");
            word_explain = AppCommandLine.getSc().nextLine();
            System.out.println();
            Dictionary.getInstance().add(new Word(word_target, word_explain));
        }
        Dictionary.getInstance().sort(Word::compareTo);
    }

    public static void insertFromFile() {
        File file = new File(linkDictionary);
        try {
            Scanner reader = new Scanner(file);
            insertFromFile(reader);
        } catch (WordException e) {
            System.out.println("File contains invalid words.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertFromFile(Scanner reader) {
        while (reader.hasNext()) {
            String s = reader.nextLine();
            String[] word = s.split(" {4}");
            if (word.length != 2) throw new WordException(s);
            if (Dictionary.getInstance().contains(word[0])) {
                for (int i = 0; i < Dictionary.getInstance().size(); i++) {
                    if (Dictionary.getInstance().get(i).getWord_target().equals(word[0])) {
                        Dictionary.getInstance().set(i, new Word(word[0],
                                Dictionary.getInstance().get(i).getWord_explain()));
                        break;
                    }
                }
            } else {
                Dictionary.getInstance().add(new Word(word[0].toLowerCase(), word[1].toLowerCase()));
            }
        }
        Dictionary.getInstance().sort(Word::compareTo);
    }

    public static BoardWord dictionaryLookup(String s) {
        BoardWord foundWords = new BoardWord();
        for (Word word : Dictionary.getInstance()) {
            if (word.getWord_target().contains(s)) {
                foundWords.add(word);
            }
        }
        return foundWords;
    }

    public static void dictionaryExportToFile() {
        String linkToFile = AppCommandLine.getSc().nextLine();
        try {
            File exportTo = new File(linkToFile);
            FileWriter exporter = new FileWriter(exportTo);
            for (Word word: Dictionary.getInstance()) {
                exporter.write(word.getWord_target() + "\t" + word.getWord_explain() + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void deleteWordFromCommandLine() {
        System.out.println("Nhập từ muốn xóa (tiếng Anh): ");
        final String s = AppCommandLine.getSc().nextLine();
        boolean b = deleteWord(s);
        if (b) {
            System.out.println("Đã xóa thành công " + s + ".");
        } else {
            System.out.println("Không xóa được " + s + ".");
        }
    }

    public static void changeWordFromCommandLine() {
        System.out.println("Nhập từ muốn sửa (tiếng Anh): ");
        String s = AppCommandLine.getSc().nextLine();
        if (!Dictionary.getInstance().contains(s)) {
            System.out.println("Từ điển không chứa từ này");
            return;
        }
        System.out.println("""
                Nhập 1 để sửa từ tiếng Anh.
                Nhập 2 để sửa nghĩa tiếng Việt.
                """);
        String c = AppCommandLine.getSc().nextLine();
        int choice = Integer.parseInt(c);

        switch (choice) {
            case 1-> {
                System.out.println("Nhập từ thay thế: ");
                String newWord = AppCommandLine.getSc().nextLine();
                for (int i = 0; i < Dictionary.getInstance().size(); i++) {
                    if (Dictionary.getInstance().get(i).getWord_target().equals(s)) {
                        Dictionary.getInstance().set(i, new Word(newWord,
                                Dictionary.getInstance().get(i).getWord_explain()));
                        Dictionary.getInstance().sort(Word::compareTo);
                        break;
                    }
                }
            }
            case 2-> {
                System.out.println("Nhập nghĩa thay thế: ");
                String newWord = AppCommandLine.getSc().nextLine();
                changeWordMeaning(s, newWord);
            }
        }
    }

    private static boolean changeWordMeaning(String word_target, String newMeaning) {
        if (Dictionary.getInstance().contains(word_target)) {
            Dictionary.getInstance().get(word_target).setWord_explain(newMeaning);
            return true;
        }
        return false;
    }

    private static boolean deleteWord(String word_target) {
        if (Dictionary.getInstance().contains(word_target)) {
            Dictionary.getInstance().remove(word_target);
            return true;
        }
        System.out.println("Từ điển không chứa từ này.");
        return false;
    }
}
