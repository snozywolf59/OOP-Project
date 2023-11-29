package com.dictionaryCommandLine;

import java.util.ArrayList;

public class Dictionary extends BoardWord {
    public static Dictionary dictionary = new Dictionary();
    public Dictionary() {
        super();
    }

    public static synchronized Dictionary getInstance() {
        return dictionary;
    }

    @Override
    public boolean contains(Object string) {
        if (!(string instanceof String)) return false;
        String find = (String) string;
        int index = insertBinary(find);
        if (0 > index || index >= this.size()) return false;
        if (this.get(index).getWord_target().equals(find)) return true;
        return false;
    }

    public Word get(String word_target) {
        int index = insertBinary( word_target);
        if (index < 0 || index >= this.size()) return null;
        if (!this.get(index).getWord_target().equals(word_target)) return null;
        return this.get(index);
    }

    public void remove(String string) {
        int index = insertBinary(string);
        if (0 > index || index > this.size() - 1) {
            return;
        }
        this.remove(index);
    }

    private int insertBinary(String newWordTarget) {
        int left = 0;
        int right = this.size() - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            Word midWord = this.get(mid);
            int cmp = midWord.getWord_target().compareTo(newWordTarget);
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}

