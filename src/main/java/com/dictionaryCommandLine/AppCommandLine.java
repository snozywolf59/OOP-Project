package com.dictionaryCommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppCommandLine {
    static Scanner sc = new Scanner(System.in);

    public static synchronized Scanner getSc() {
        return sc;
    }
    public static void main(String[] args) {
        DictionaryManagement.insertFromFile();
        DictionaryCommandLine.showAllWords();
        DictionaryCommandLine.dictionaryAdvanced();
        sc.close();
//        List<Integer> a = new ArrayList<>();
//        a.add(0, 2);
//
//        a.add(1, 3);
//        a.add(1, 4);
//        System.out.println(a);
    }
}
