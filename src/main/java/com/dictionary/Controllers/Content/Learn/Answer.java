package com.dictionary.Controllers.Content.Learn;

public class Answer {
    private String [] listAnswers = new String[]{};

    public void setListAnswer(int cambridge, int test) {
        if (cambridge == 17) {
            if (test == 1) {
                listAnswers = new String[] {"litter", "dogs", "insects", "butterflies", "wall", "island", "boots", "beginners", "spoons", "35",
                        "11.a", "12.c", "13.b", "14.b", "15.a", "16.d", "17.b", "18.c", "19.d", "20.e",
                        "21.a", "22.b", "23.b", "24.a", "25.c", "26.c", "27.a", "28.e", "29.f", "30.c",
                        "puzzle", "logic", "confusion", "meditation", "stone", "coins", "tree", "breathing", "paper", "anxiety"};
            }
        }
    }

    public int getPoint(String userAnswer) {
        int point = 0;
        userAnswer = userAnswer.toLowerCase().trim();
        for (int i = 0; i < (int) listAnswers.length; i++) {
            if (userAnswer.contains(listAnswers[i])) {
                point++;
            }
        }
        return point;
    }
}
