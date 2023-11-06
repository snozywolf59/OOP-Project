package com.dictionary.Controllers.Content.Learn;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ListeningTest {
    private int cambridge;
    private int test;
    private int section;
    private String exam;
    private String urlAudio;
    private Answer answer = new Answer();
    private int point = 0;

    public int getPoint(String userAnswer) {
        point = answer.getPoint(userAnswer);
        return point;
    }

    public void setListeningTest(int cambridge, int test, int section) {
        this.cambridge = cambridge;
        this.test = test;
        this.section = section;
        if (cambridge == 17) {
            if (test == 1) {
                answer.setListAnswer(17, 1);
                if (section == 1) {
                    exam = CAMBRIDGE17_TEST1_SECTION1;
                    urlAudio = CAMBRIDGE17_TEST1_SECTION1_AUDIO;
                }
                if (section == 2) {
                    exam = CAMBRIDGE17_TEST1_SECTION2;
                    urlAudio = CAMBRIDGE17_TEST1_SECTION2_AUDIO;
                }
                if (section == 3) {
                    exam = CAMBRIDGE17_TEST1_SECTION3;
                    urlAudio = CAMBRIDGE17_TEST1_SECTION3_AUDIO;
                }
                if (section == 4) {
                    exam = CAMBRIDGE17_TEST1_SECTION4;
                    urlAudio = CAMBRIDGE17_TEST1_SECTION4_AUDIO;
                }
            }
        }
    }

    public String getExam() {
        return exam;
    }

    public void playAudio() {
        try {
            URL url = new URL(urlAudio);
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = url.openStream();
                    AdvancedPlayer player = new AdvancedPlayer(inputStream);
                    player.play();
                } catch (IOException | JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(false);

            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String CAMBRIDGE17_TEST1_SECTION1_AUDIO = "https://drive.google.com/uc?id=13jMijQ8LhgePqb_OoKdaNClfxF9uLH71";
    public static final String CAMBRIDGE17_TEST1_SECTION2_AUDIO = "https://drive.google.com/uc?id=1bXbHsn5nTKciBi9zL1FpZxT0zMHmmuJM";
    public static final String CAMBRIDGE17_TEST1_SECTION3_AUDIO = "https://drive.google.com/uc?id=1yozZn7FbNVkxcwQCFttsyvenAVy1wGsQ";
    public static final String CAMBRIDGE17_TEST1_SECTION4_AUDIO = "https://drive.google.com/uc?id=1g3BIgvNux0yUyDtzh7wiQhI6CYgo9Ogc";
    public static final String CAMBRIDGE17_TEST1_SECTION1 = "Questions 1 – 10\n" +
            "\n" +
            "Complete the notes below.\n" +
            "Write ONE WORD AND/OR A NUMBER for each answer.\n" +
            "\n" +
            "Buckworth Conservation Group\n" +
            "\n" +
            "Regular activities\n" +
            "Beach\n" +
            "• making sure the beach does not have 1 _______ on it\n" +
            "• no 2 _______\n" +
            "\n" +
            "Nature reserve\n" +
            "• maintaining paths\n" +
            "• nesting boxes for birds installed\n" +
            "• next task is taking action to attract 3 _______ to the place\n" +
            "• identifying types of 4 _______\n" +
            "• building a new 5 _______\n" +
            "\n" +
            "Forthcoming events\n" +
            "Saturday\n" +
            "• meet at Dunsmore Beach car park\n" +
            "• walk across the sands and reach the 6 _______\n" +
            "• take a picnic\n" +
            "• wear appropriate 7 _______\n" +
            "\n" +
            "Woodwork session\n" +
            "• suitable for 8 _______ to participate in\n" +
            "• making 9 _______ out of wood\n" +
            "• 17th, from 10 a.m. to 3 p.m.\n" +
            "• cost of session (no camping): £ 10 _______";
    public static final String CAMBRIDGE17_TEST1_SECTION2 = "Questions 11–14\n" +
            "Choose the correct letter, A, B or C.\n" +
            "\n" +
            "Boat trip round Tasmania\n" +
            "\n" +
            "11. What is the maximum number of people who can stand on each side of the boat?\n" +
            "A. 9\n" +
            "B. 15\n" +
            "C. 18\n" +
            "\n" +
            "12. What colour are the tour boats?\n" +
            "A. dark red\n" +
            "B. jet black\n" +
            "C. light green\n" +
            "\n" +
            "13. Which lunchbox is suitable for someone who doesn’t eat meat or fish?\n" +
            "A. Lunchbox 1\n" +
            "B. Lunchbox 2\n" +
            "C. Lunchbox 3\n" +
            "\n" +
            "14. What should people do with their litter?\n" +
            "A. take it home\n" +
            "B. hand it to a member of staff\n" +
            "C. put it in the bins provided on the boat\n" +
            "\n" +
            "Questions 15 and 16\n" +
            "Choose TWO letters, A–E.\n" +
            "Which TWO features of the lighthouse does Lou mention?\n" +
            "A. why it was built\n" +
            "B. who built it\n" +
            "C. how long it took to build\n" +
            "D. who staffed it\n" +
            "E. what it was built with\n" +
            "\n" +
            "Questions 17 and 18\n" +
            "Choose TWO letters, A–E.\n" +
            "Which TWO types of creature might come close to the boat?\n" +
            "A. sea eagles\n" +
            "B. fur seals\n" +
            "C. dolphins\n" +
            "D. whales\n" +
            "E. penguins\n" +
            "\n" +
            "Questions 19 and 20\n" +
            "Choose TWO letters, A–E.\n" +
            "Which TWO points does Lou make about the caves?\n" +
            "A. Only large tourist boats can visit them.\n" +
            "B. The entrances to them are often blocked.\n" +
            "C. It is too dangerous for individuals to go near them.\n" +
            "D. Someone will explain what is inside them.\n" +
            "E. They cannot be reached on foot.";
    private static final String CAMBRIDGE17_TEST1_SECTION3 = "Questions 21–26\n" +
            "Choose the correct letter, A, B or C.\n" +
            "\n" +
            "21. What problem did both Diana and Tim have when arranging their work experience?\n" +
            "A. making initial contact with suitable farms\n" +
            "B. organising transport to and from the farm\n" +
            "C. finding a placement for the required length of time\n" +
            "\n" +
            "22. Tim was pleased to be able to help\n" +
            "A. a lamb that had a broken leg.\n" +
            "B. a sheep that was having difficulty giving birth.\n" +
            "C. a newly born lamb that was having trouble feeding.\n" +
            "\n" +
            "23. Diana says the sheep on her farm\n" +
            "A. were of various different varieties.\n" +
            "B. were mainly reared for their meat.\n" +
            "C. had better quality wool than sheep on the hills.\n" +
            "\n" +
            "24. What did the students learn about adding supplements to chicken feed?\n" +
            "A. These should only be given if specially needed.\n" +
            "B. It is worth paying extra for the most effective ones.\n" +
            "C. The amount given at one time should be limited.\n" +
            "\n" +
            "25. What happened when Diana was working with dairy cows?\n" +
            "A. She identified some cows incorrectly.\n" +
            "B. She accidentally threw some milk away.\n" +
            "C. She made a mistake when storing milk.\n" +
            "\n" +
            "26. What did both farmers mention about vets and farming?\n" +
            "A. Vets are failing to cope with some aspects of animal health.\n" +
            "B. There needs to be a fundamental change in the training of vets.\n" +
            "C. Some jobs could be done by the farmer rather than by a vet.\n" +
            "\n" +
            "Questions 27–30\n" +
            "What opinion do the students give about each of the following modules on their veterinary science course?\n" +
            "\n" +
            "Choose FOUR answers from the box and write the correct letter, A–F, next to questions 27–30.\n" +
            "A. Tim found this easier than expected.\n" +
            "B. Tim thought this was not very clearly organised.\n" +
            "C. Diana may do some further study on this.\n" +
            "D. They both found the reading required for this was difficult.\n" +
            "E. Tim was shocked at something he learned on this module.\n" +
            "F. They were both surprised how little is known about some aspects of this.\n" +
            "\n" +
            "Modules on Veterinary Science course\n" +
            "27. Medical terminology: ______\n" +
            "28. Diet and nutrition: ______\n" +
            "29. Animal disease: ______\n" +
            "30. Wildlife medication: ______";
    private final String CAMBRIDGE17_TEST1_SECTION4 = "Questions 31–40\n" +
            "\n" +
            "Complete the notes below.\n" +
            "Write ONE WORD ONLY for each answer.\n" +
            "\n" +
            "Definition\n" +
            "• a winding spiral path leading to a central area\n" +
            "\n" +
            "Labyrinths compared with mazes\n" +
            "• Mazes are a type of 31 ______\n" +
            "− 32 ______ is needed to navigate through a maze\n" +
            "− the word ‘maze’ is derived from a word meaning a feeling of 33 ______\n" +
            "• Labyrinths represent a journey through life\n" +
            "− they have frequently been used in 34 ______ and prayer\n" +
            "\n" +
            "Early examples of the labyrinth spiral\n" +
            "• Ancient carvings on 35 ______ have been found across many cultures\n" +
            "• The Pima, a Native American tribe, wove the symbol on baskets\n" +
            "• Ancient Greeks used the symbol on 36 ______\n" +
            "\n" +
            "Walking labyrinths\n" +
            "• The largest surviving example of a turf labyrinth once had a big 37 ______ at its centre\n" +
            "\n" +
            "Labyrinths nowadays\n" +
            "• Believed to have a beneficial impact on mental and physical health, e.g., walking a maze can reduce a person’s 38 ______ rate\n" +
            "• Used in medical and health and fitness settings and also prisons\n" +
            "• Popular with patients, visitors and staff in hospitals\n" +
            "− patients who can’t walk can use ‘finger labyrinths’ made from 39 ______\n" +
            "− research has shown that Alzheimer’s sufferers experience less 40 ______";
}

