package com.dictionaryCommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppCommandLine {
    public static Scanner sc = new Scanner(System.in);

    public static synchronized Scanner getSc() {
        return sc;
    }
    public static void main(String[] args) {
        DictionaryManagement.insertFromFile();
        DictionaryCommandLine.showAllWords();
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
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
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    DictionaryManagement.insertFromCommandLine();
                    break;
                case 3:
                    DictionaryManagement.changeWordFromCommandLine();
                    break;
                case 4:
                    DictionaryCommandLine.showAllWords();
                    break;
                default:
                    System.out.println("Action not supported");
            }
        } while (choice != 0);
        sc.close();
//        List<Integer> a = new ArrayList<>();
//        a.add(0, 2);
//
//        a.add(1, 3);
//        a.add(1, 4);
//        System.out.println(a);
    }
}
