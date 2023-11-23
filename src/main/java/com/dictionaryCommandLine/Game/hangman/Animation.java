package com.dictionaryCommandLine.Game.hangman;

import java.io.OutputStream;
import java.io.PrintStream;

public class Animation {
    private static final String IMG_FMT =
            "    ________%n" +
                    "    |      \\|%n" +
                    "    o       |%n" +
                    "   /|\\      |%n" +
                    "    |       |%n" +
                    "   / \\      |%n" +
                    " ___________|___%n" +
                    " |   %2d/10      |%n" +
                    " |   R.I.P      |%n";

    private static final String IMG_FMT_MASK =
            "    2222222200" +
                    "    4      3100" +
                    "    5       100" +
                    "   867      100" +
                    "    6       100" +
                    "   9 a      100" +
                    " 00000000000100000" +
                    " 0   000000      000" +
                    " 0   aaaaa      000";

    static { assert(IMG_FMT.length() == IMG_FMT_MASK.length()); }

    public static void show(int stage) {
        char m = Character.forDigit(stage, 36);
        StringBuilder s = new StringBuilder(IMG_FMT.length());
        for (int i = 0; i < IMG_FMT.length(); i++) {
            s.append((IMG_FMT_MASK.charAt(i) <= m) ? IMG_FMT.charAt(i) : ' ');
        }
        System.out.printf(s.toString(), stage);
    }

    public static void main(String[] args) {
        show(10);
    }
}
