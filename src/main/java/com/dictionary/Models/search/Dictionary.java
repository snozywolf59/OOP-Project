package com.dictionary.Models.search;

import com.dictionary.Models.Login.User;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
public class Dictionary {
    private static final String linkToDictionary = "src/main/resources/Word/eng-vie.txt";

    private final TreeNode headNode;

    private final List<String> SameWord;
    public Dictionary() {
        headNode = new TreeNode();
        SameWord = new ArrayList<>();
    }
    public void SetTreeWord(List<Word> wordList) {
        this.headNode.setA(' ');

        for (Word word : wordList) {
            TreeNode tmp = this.headNode;
            String target;
            target = word.getWordTarget();
            for (int v = 0; v < target.length(); v++) {
                char s = target.charAt(v);
                TreeNode newNode = new TreeNode();
                newNode.setA(s);
                int i = switch (s) {
                    case '-' -> 0;
                    case ' ' -> 29;
                    default -> (s) - 96;
                };
                if (tmp.listNode[i] == null) {
                    tmp.listNode[i] = newNode;
                }
                if (v == target.length() - 1) {
                    tmp.listNode[i].setExplainWord(word);
                }
                tmp = tmp.listNode[i];
            }
        }
    }
    

    public Word search(String word) {
        Word result = new Word();
        TreeNode temp = this.headNode;
        for (int cnt = 0; cnt < word.length(); cnt++) {
            char ss = word.charAt(cnt);
            int exp;
            if(ss == '-')exp = 0;
            else if(ss == ' ')exp = 29;
            else exp = (ss) - 96;
            if (cnt == word.length() - 1) {
                result  =  temp.listNode[exp].getExplainWord();break;}
            temp = temp.listNode[exp];
        }
        return result;
    }

    public void travelNode(TreeNode node) {
        if(node == null) return;
        for(int i = 0;i < node.listNode.length;i++) {
            if(node.listNode[i] != null) {
                if(node.listNode[i].getExplainWord() != null) {
                this.SameWord.add(node.listNode[i].getExplainWord().getWordTarget());}
            }
            travelNode(node.listNode[i]);
        }
    }

     public List<String> searchWordSame(String word) {
         TreeNode tmp = headNode;
        for(int i = 0;i < word.length();i++) {
            char s = word.charAt(i);
            int k;
            if(!((int) (s) >= 97 && (int) (s) <= 122)) k = (s);
            else k = (s) - 96;
            tmp = tmp.listNode[k];
        }
        travelNode(tmp);
         return new ArrayList<>(SameWord);
    }  

    public ArrayList<Word> ListWordTxt() {
        ArrayList<Word> listWord = new ArrayList<>();
        File file;
        file = new File(linkToDictionary);

        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            Word wordAdd = new Word();
            StringBuilder tmp = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '@' && wordAdd.getWordTarget() != null) {
                        wordAdd.setWordExplain(tmp);
                        listWord.add(wordAdd);
                        tmp = new StringBuilder();
                        wordAdd= new Word();
                    }
                    if (line.charAt(0) == '@' && wordAdd.getWordTarget() == null) {
                        int s = line.length();
                        for (int i = 0; i < line.length(); i++) {
                            if (line.charAt(i) == '/') {
                                s = i;
                                break;
                            }
                        }
                        boolean kt = true;
                        if (s != line.length()) {
                            for (int cnt = 0; cnt < line.substring(1, s - 1).length(); cnt++) {
                                int intChar = line.substring(1, s - 1).charAt(cnt);
                                char c = (char) intChar;
                                if (!((intChar >= 97 && intChar <= 122) | c == ' ')) {
                                    kt = false;break;
                                }
                            }
                            if (kt) {
                                wordAdd.setWordTarget(line.substring(1, s - 1));
                                wordAdd.setWordPronoun(line.substring(s));
                            }
                        } else {
                            for (int cnt = 0; cnt < line.substring(1).length(); cnt++) {
                                int intChar = (line.substring(1).charAt(cnt));
                                char c = line.substring(1).charAt(cnt);
                                if (!((intChar >= 97 && intChar <= 122) | c == ' ')) {
                                    kt = false;
                                    break;
                                }
                            }
                            if(kt) {
                                wordAdd.setWordTarget(line.substring(1));
                            }
                        }
                    }else {
                        tmp.append(line);
                        tmp.append('\n');
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        User.getInstance().readFavoriteWords();
        Map<String, String> listFavorite = User.getInstance().getFavoriteWords();
        for (String key : listFavorite.keySet()) {
            if (listWord.contains(key)) {
                int cnt = listWord.indexOf(key);
                Word words = new Word();
                words.setWordTarget(key);
                StringBuilder wordAdd = new StringBuilder(listFavorite.get(key));
                words.setWordExplain(wordAdd);
                listWord.set(cnt, words);
            } else {
                Word words = new Word();
                words.setWordTarget(key);
                StringBuilder wordAdd = new StringBuilder(listFavorite.get(key));
                words.setWordExplain(wordAdd);
                listWord.add(words);
            }
        }
        return listWord;
    }
    
    public void addWord(Word word) {
        TreeNode tmp = this.headNode;
        String target = word.getWordTarget();
        for (int v = 0; v < target.length(); v++) {
            char s = target.charAt(v);
            TreeNode newNode = new TreeNode();
            newNode.setA(s);
            int i;
            if(s == ' ') i = 29;
            else if(s == '-') i = 0;
            else i = (s) - 96;
            if (tmp.listNode[i] == null) {
                tmp.listNode[i] = newNode;
            }
            if (v == target.length() - 1 ) {
                if(tmp.listNode[i].getExplainWord() == null)
                {tmp.listNode[i].setExplainWord(word);}
                else {
                    System.out.println("Tu da ton tai");
                    return;
                }
            }
            tmp = tmp.listNode[i];
        }
    }
    
    public void editWord(String target, String explain) {
        TreeNode tmp = headNode;
        for(int v = 0;v < target.length(); v++) {
            char s = target.charAt(v);
            TreeNode newNode = new TreeNode();
            newNode.setA(s);
            int i;
            if(!((int) (s) >=97 && (int) (s) <=122)) i = (s);
            else i = (s) - 96;

            if(tmp.listNode[i] == null) {tmp.listNode[i] = newNode;}
            if(v == target.length() - 1) { StringBuilder a = tmp.listNode[i].getExplainWord().getWordExplain();
            a.append('-');
            a.append(explain);
            Word word =  tmp.listNode[i].getExplainWord();
            word.setWordExplain(a);
            tmp.listNode[i].setExplainWord(word);
            }
            tmp =  tmp.listNode[i];
        }
    }


    public static void textToSpeech(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(text);
        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
}