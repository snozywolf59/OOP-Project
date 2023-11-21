package com.dictionary.Models.Home;

import opennlp.tools.doccat.*;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ChatBot {
    private DoccatModel model;

    private static Map<String, String> questionAnswer = new HashMap<>();

    private static ChatBot instance;

    private ChatBot() {
        model = trainModel();
        questionAnswer.put("conversation-continue", "Hmm");
        questionAnswer.put("conversation-complete", "Rất vui khi được nói chuyện với cậu. Hi");
        questionAnswer.put("info-ChatBot", "Tớ là M, là một trợ lý ảo của ứng dụng EDUET - một ứng dụng học tiếng anh cực kì bổ ích. Tớ được thiết lập để giúp cậu có trải nghiệm tốt hơn.");
        questionAnswer.put("how-to-learn-E", "Nếu cậu muốn học tiếng Anh, có một số cách thú vị và hiệu quả! Cậu có thể bắt đầu bằng việc xem phim hoặc nghe nhạc tiếng Anh để làm quen với ngữ cảnh và nghe âm thanh. Ngoài ra, đọc sách, báo, hoặc blog cũng là cách tốt để cải thiện từ vựng. Hãy thử tìm người học cùng để có thêm động lực và có ai đó để thực hành nha.");
        questionAnswer.put("feel-foolish", "Đồ đần!");
    }

    public static ChatBot getInstance()
    {
        if (instance == null)
        {
            synchronized(ChatBot.class)
            {
                if (instance == null)
                {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new ChatBot();
                }
            }
        }
        return instance;
    }

    /*
     * Define answers for each given category.
     */

    private DoccatModel trainModel() {
        try {
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(
                    new PlainTextByLineStream(new MarkableFileInputStreamFactory(new File("src/main/resources/Bin/faq-categorizer.txt")), StandardCharsets.UTF_8)
            );

            DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});

            TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
            params.put(TrainingParameters.CUTOFF_PARAM, 0);

            return DocumentCategorizerME.train("en", sampleStream, params, factory);
        } catch (IOException e) {
            System.out.println("Train model error.");
        }
        return null;
    }

    private String [] breakSentences(String sentences) {
        try {
            InputStream modelIn = new FileInputStream("src/main/resources/Bin/en-sent.bin");
            SentenceModel sentenceModel = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            return sentenceDetector.sentDetect(sentences);
        } catch (IOException e) {
            System.out.println("Break sentence error.");
        }
        return null;
    }

    private String [] tokenizeSentence(String sentence) {
        try {
            InputStream modelIn = new FileInputStream("src/main/resources/Bin/en-token.bin");
            TokenizerModel tokenizerModel = new TokenizerModel(modelIn);
            TokenizerME tokenizer = new TokenizerME(tokenizerModel);
            return tokenizer.tokenize(sentence);
        } catch (IOException e) {
            System.out.println("Tokenize sentence error");
        }
        return null;
    }

    private String [] detectPOSTag(String [] tokens) {
        try {
            InputStream modelIn = new FileInputStream("src/main/resources/Bin/en-pos-maxent.bin");
            POSModel posModel = new POSModel(modelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel);
            return posTagger.tag(tokens);
        } catch (IOException e) {
            System.out.println("Detect POS tag error");
        }
        return null;
    }

    private String [] lemmatization(String [] tokens, String [] POSTag) {
        try {
            InputStream modelIn = new FileInputStream("src/main/resources/Bin/en-lemmatizer.bin");
            LemmatizerModel lemmatizerModel = new LemmatizerModel(modelIn);
            LemmatizerME lemmatizer = new LemmatizerME(lemmatizerModel);
            return lemmatizer.lemmatize(tokens, POSTag);
        } catch (IOException e) {
            System.out.println("Lemmatization error");
        }
        return null;
    }

    public String bestCategory(String sentence) {
        String [] tokens = tokenizeSentence(sentence);
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        double[] probabilitiesOfOutcomes = categorizer.categorize(lemmatization(tokens, detectPOSTag(tokens)));
        return categorizer.getBestCategory(probabilitiesOfOutcomes);
    }

    public String answer(String bestCategory, String username) {
        StringBuilder answer = new StringBuilder();
        if (questionAnswer.containsKey(bestCategory)) {
            answer.append(questionAnswer.get(bestCategory));
        } else {
            switch (bestCategory) {
                case "greeting":
                    int ranNum = new Random().nextInt(2);
                    switch (ranNum) {
                        case 0:
                            answer.append("Xin chào ").append(username).append(", tớ có thể giúp gì cho cậu nào");
                            break;
                        case 1:
                            answer.append("Chào ").append(username).append(", cậu cần tớ việc gì nhỉ?");
                            break;
                    }
                    break;
                case "number-of-favorite-words":
                    int i = 1;
                    answer.append("Một con số tuyệt vời đó! Cậu đã đánh dấu ").append(i).append(" từ rồi.");
                    break;
            }
        }
        return answer.toString();
    }
}
