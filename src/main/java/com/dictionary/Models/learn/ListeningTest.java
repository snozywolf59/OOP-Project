package com.dictionary.Models.learn;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ListeningTest {
    private String exam;
    private String urlAudio;
    private final Answer answer = new Answer();
    private static Thread thread;
    private static AdvancedPlayer player;

    public int getPoint(String userAnswer) {
        return answer.getPoint(userAnswer);
    }

    public void setListeningTest(int cambridge, int test, int section) {
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
                    String CAMBRIDGE17_TEST1_SECTION4 = """
                            Questions 31–40

                            Complete the notes below.
                            Write ONE WORD ONLY for each answer.

                            Definition
                            • a winding spiral path leading to a central area

                            Labyrinths compared with mazes
                            • Mazes are a type of 31 ______
                            − 32 ______ is needed to navigate through a maze
                            − the word ‘maze’ is derived from a word meaning a feeling of 33 ______
                            • Labyrinths represent a journey through life
                            − they have frequently been used in 34 ______ and prayer

                            Early examples of the labyrinth spiral
                            • Ancient carvings on 35 ______ have been found across many cultures
                            • The Pima, a Native American tribe, wove the symbol on baskets
                            • Ancient Greeks used the symbol on 36 ______

                            Walking labyrinths
                            • The largest surviving example of a turf labyrinth once had a big 37 ______ at its centre

                            Labyrinths nowadays
                            • Believed to have a beneficial impact on mental and physical health, e.g., walking a maze can reduce a person’s 38 ______ rate
                            • Used in medical and health and fitness settings and also prisons
                            • Popular with patients, visitors and staff in hospitals
                            − patients who can’t walk can use ‘finger labyrinths’ made from 39 ______
                            − research has shown that Alzheimer’s sufferers experience less 40 ______""";
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
        if (thread != null && thread.isAlive()) {
            stopAudio();
        }
        try {
            URL url = new URL(urlAudio);
            thread = new Thread(() -> {
                try {
                    InputStream inputStream = url.openStream();
                    player = new AdvancedPlayer(inputStream);
                    player.play();
                } catch (IOException | JavaLayerException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            });
            thread.setDaemon(false);

            thread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void stopAudio() {
        if (player != null) {
            player.close();
        }
    }

    public static final String CAMBRIDGE17_TEST1_SECTION1_AUDIO = "https://drive.google.com/uc?id=13jMijQ8LhgePqb_OoKdaNClfxF9uLH71";
    public static final String CAMBRIDGE17_TEST1_SECTION2_AUDIO = "https://drive.google.com/uc?id=1bXbHsn5nTKciBi9zL1FpZxT0zMHmmuJM";
    public static final String CAMBRIDGE17_TEST1_SECTION3_AUDIO = "https://drive.google.com/uc?id=1yozZn7FbNVkxcwQCFttsyvenAVy1wGsQ";
    public static final String CAMBRIDGE17_TEST1_SECTION4_AUDIO = "https://drive.google.com/uc?id=1g3BIgvNux0yUyDtzh7wiQhI6CYgo9Ogc";
    public static final String CAMBRIDGE17_TEST1_SECTION1 = """
            Questions 1 – 10

            Complete the notes below.
            Write ONE WORD AND/OR A NUMBER for each answer.

            Buckworth Conservation Group

            Regular activities
            Beach
            • making sure the beach does not have 1 _______ on it
            • no 2 _______

            Nature reserve
            • maintaining paths
            • nesting boxes for birds installed
            • next task is taking action to attract 3 _______ to the place
            • identifying types of 4 _______
            • building a new 5 _______

            Forthcoming events
            Saturday
            • meet at Dunsmore Beach car park
            • walk across the sands and reach the 6 _______
            • take a picnic
            • wear appropriate 7 _______

            Woodwork session
            • suitable for 8 _______ to participate in
            • making 9 _______ out of wood
            • 17th, from 10 a.m. to 3 p.m.
            • cost of session (no camping): £ 10 _______""";
    public static final String CAMBRIDGE17_TEST1_SECTION2 = """
            Questions 11–14
            Choose the correct letter, A, B or C.

            Boat trip round Tasmania

            11. What is the maximum number of people who can stand on each side of the boat?
            A. 9
            B. 15
            C. 18

            12. What colour are the tour boats?
            A. dark red
            B. jet black
            C. light green

            13. Which lunchbox is suitable for someone who doesn’t eat meat or fish?
            A. Lunchbox 1
            B. Lunchbox 2
            C. Lunchbox 3

            14. What should people do with their litter?
            A. take it home
            B. hand it to a member of staff
            C. put it in the bins provided on the boat

            Questions 15 and 16
            Choose TWO letters, A–E.
            Which TWO features of the lighthouse does Lou mention?
            A. why it was built
            B. who built it
            C. how long it took to build
            D. who staffed it
            E. what it was built with

            Questions 17 and 18
            Choose TWO letters, A–E.
            Which TWO types of creature might come close to the boat?
            A. sea eagles
            B. fur seals
            C. dolphins
            D. whales
            E. penguins

            Questions 19 and 20
            Choose TWO letters, A–E.
            Which TWO points does Lou make about the caves?
            A. Only large tourist boats can visit them.
            B. The entrances to them are often blocked.
            C. It is too dangerous for individuals to go near them.
            D. Someone will explain what is inside them.
            E. They cannot be reached on foot.""";
    private static final String CAMBRIDGE17_TEST1_SECTION3 = """
            Questions 21–26
            Choose the correct letter, A, B or C.

            21. What problem did both Diana and Tim have when arranging their work experience?
            A. making initial contact with suitable farms
            B. organising transport to and from the farm
            C. finding a placement for the required length of time

            22. Tim was pleased to be able to help
            A. a lamb that had a broken leg.
            B. a sheep that was having difficulty giving birth.
            C. a newly born lamb that was having trouble feeding.

            23. Diana says the sheep on her farm
            A. were of various different varieties.
            B. were mainly reared for their meat.
            C. had better quality wool than sheep on the hills.

            24. What did the students learn about adding supplements to chicken feed?
            A. These should only be given if specially needed.
            B. It is worth paying extra for the most effective ones.
            C. The amount given at one time should be limited.

            25. What happened when Diana was working with dairy cows?
            A. She identified some cows incorrectly.
            B. She accidentally threw some milk away.
            C. She made a mistake when storing milk.

            26. What did both farmers mention about vets and farming?
            A. Vets are failing to cope with some aspects of animal health.
            B. There needs to be a fundamental change in the training of vets.
            C. Some jobs could be done by the farmer rather than by a vet.

            Questions 27–30
            What opinion do the students give about each of the following modules on their veterinary science course?

            Choose FOUR answers from the box and write the correct letter, A–F, next to questions 27–30.
            A. Tim found this easier than expected.
            B. Tim thought this was not very clearly organised.
            C. Diana may do some further study on this.
            D. They both found the reading required for this was difficult.
            E. Tim was shocked at something he learned on this module.
            F. They were both surprised how little is known about some aspects of this.

            Modules on Veterinary Science course
            27. Medical terminology: ______
            28. Diet and nutrition: ______
            29. Animal disease: ______
            30. Wildlife medication: ______""";
}

