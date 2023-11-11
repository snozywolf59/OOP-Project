
package com.dictionary.Controllers.Content.Search;

/**
 *
 * @author Admin
 */
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private static final String linkToDictionary = "src/main/resources/Word/eng-vie.txt";

    private final TreeNode headNode;

    private final List<String> SameWord;
    public Dictionary() {
        headNode = new TreeNode();
        SameWord = new ArrayList<String>();
    }
    public void SetTreeWord(List<Word> wordList) {
        this.headNode.setA(' ');

        for(int z = 0;z < wordList.size();z++) {
           Word word = wordList.get(z);
            TreeNode tmp = this.headNode;
            String target = "";
            target = word.getWordTarget();
            for(int v = 0;v < target.length(); v++) {
                char s = target.charAt(v);
                TreeNode newNode = new TreeNode();
                newNode.setA(s);
                int i;
               switch (s) {
                   case '-':
                       i = 0;
                       break;
                   case ' ':
                       i = 29;
                       break;
                   default:
                       i = (s) - 96;
                       break;
               }
                if(tmp.listNode[i] == null) {tmp.listNode[i] = newNode;}
                    if(v == target.length() - 1) {tmp.listNode[i].setExplainWord(word);}
                    tmp =  tmp.listNode[i];
            }
        }
    }
    

    public Word search(String word) {
        Word result = new Word();
        TreeNode temp = this.headNode;
        int i = 0;
            for (int cnt = 0; cnt < word.length(); cnt++) {
                char ss = word.charAt(cnt);
                int exp = 0;
                int x = 0;
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
        List<String> result = new ArrayList<>();
        TreeNode tmp = headNode;
        for(int i = 0;i < word.length();i++) {
            char s = word.charAt(i);
            int k;
            int x = (s);
            if(!(x >= 97 && x <= 122)) k = (s);
            else if(s == '-')k = 0;
            else if(s == ' ')k = 29;
            else k = (s) - 96;
            tmp = tmp.listNode[k];
        }
        travelNode(tmp);
       for(String wordSame : SameWord) {
           result.add(wordSame);
       }
        return result;
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
                if (line.length() > 0) {
                    if (line.charAt(0) == '@' && wordAdd.getWordTarget() != null) {
                        wordAdd.setWordExplain(tmp);
                        listWord.add(wordAdd);
                        tmp = new StringBuilder();
                        Word w = new Word();
                        wordAdd= w;
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
                                int intChar = (line.substring(1, s - 1).charAt(cnt));
                                char c = line.substring(1, s - 1).charAt(cnt);
                                if ((intChar >= 97 && intChar <= 122) | c == ' ') {
                                    kt = true;
                                }
                                else {kt = false;break;}
                            }
                            if (kt == true) {
                                wordAdd.setWordTarget(line.substring(1, s - 1));
                                wordAdd.setWordPronoun(line.substring(s));
                            }
                        } else {
                            for (int cnt = 0; cnt < line.substring(1).length(); cnt++) {
                                int intChar = (line.substring(1).charAt(cnt));
                                char c = line.substring(1).charAt(cnt);
                                if ((intChar >= 97 && intChar <= 122) | c == ' ') {
                                    kt = true;
                                }
                                else {kt = false;break;}
                            }
                            if(kt == true) {
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
            int x = (s);
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
            int x = (s);
            if(!(x >=97 && x <=122)) i = (s);
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
