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
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();

        while (n > 0) {
            System.out.print("Enter English word:");
            String word =  sc.nextLine();
            while (!Word.isWord(word)) {
                System.out.print(word + " is not a word. Please write it true: ");
                word = sc.nextLine();
            }
            System.out.print("Enter meaning of " + word + ":");
            String explain = sc.nextLine();

            dictionary.addWord(word, explain);
            System.out.println("");
            n--;
        }
        System.out.println("Finish Inserting");
    }

    public static void insertFromFile(Dictionary dictionary) {
        
    }
    
    public static void dictionaryLookup() {
        
    }

}
