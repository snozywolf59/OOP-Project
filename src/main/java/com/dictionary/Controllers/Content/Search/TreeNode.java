package com.dictionary.Controllers.Content.Search;
/**
 *
 * @author Admin
 */
import java.util.ArrayList;

public class TreeNode {
   private char a;
   private Word WordAll;
   public TreeNode[] listNode;

   public TreeNode() {
      this.a = ' ';
      this.listNode = new TreeNode[33]; // Số lượng node con tối đa
      this.WordAll = null;
   }

   public void setA(char a) {
      this.a = a;
   }

   public char getA() {
      return a;
   }

   public void setExplainWord(Word WordALl) {
      this.WordAll = new Word( WordALl.getWordTarget(), WordALl.getWordExplain(),WordALl.getWordPronoun());
   }

   public Word getExplainWord() {
      return this.WordAll;
   }

   public TreeNode[] getListNode() {
      return this.listNode;
   }

   public void setListNode() {
      for(int i = 1;i < listNode.length; ++i) {
         listNode[i] = new TreeNode();
      }
   }
}
