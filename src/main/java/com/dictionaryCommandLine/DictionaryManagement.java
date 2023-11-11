package com.dictionaryCommandLine;

import java.util.Scanner;

public class DictionaryManagement {
    public static void insertFromCommandLine(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số từ cần thêm: n = ");
        final int n = sc.nextInt();
        sc.nextLine();
        System.out.println( "Nhập n từ cần thêm theo mẫu: " +
                            "Dòng 1 là từ tiếng Anh, " +
                            "dòng 2 là từ tiếng Việt:");
        String word_target, word_explain;
        for (int i = 0; i < n; ++i) {
            System.out.print("Nhập từ tiếng Anh: ");
            word_target = sc.nextLine();
            System.out.println();
            System.out.println("Nhập từ tiếng Việt: ");
            word_explain = sc.nextLine();
            System.out.println();
            dictionary.add(new Word(word_target, word_explain));
        }
        sc.close();
    }

    public static void insertFromFile(Dictionary dictionary, String link) {

    }
}
