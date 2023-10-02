/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordHandler;

import java.util.Scanner;

/**
 *
 * @author LENOVO
 */
public class DictionaryManagement {
    public static void insertFromCommandline(Dictionary dictionary) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();

            while (n > 0) {
                System.out.print("Enter English word:");
                String word =  sc.next();
                while (!Word.isWord(word)) {
                    System.out.print(word + " is not a word. Please write it true");
                    word = sc.next();
                }
                System.out.print("Enter meaning of " + word + ":");
                String explain = sc.next();

                dictionary.addWord(word, explain);

                n--;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertFromFile(Dictionary dictionary) {
        
    }
    
    public static void dictionaryLookup() {
        
    }

}
