package com.dictionary.Models.Home;

import com.darkprograms.speech.translator.GoogleTranslate;
import com.dictionary.App;
import com.dictionary.Models.API.Translator;
import com.dictionary.Models.FireStore.FireStoreApp;
import com.dictionary.Models.Login.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;

public final class ChatBot {
    private DoccatModel model;

    private static Map<String, String> questionAnswer = new HashMap<>();

    private static ChatBot instance;

    private ChatBot() {
        model = trainModel();
        questionAnswer = getAnswerFromFireStore();
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

    public String answer(String bestCategory) {
        System.out.println(bestCategory);

        StringBuilder answer = new StringBuilder();
        if (questionAnswer.containsKey(bestCategory)) {
            answer.append(questionAnswer.get(bestCategory));
        } else {
            switch (bestCategory) {
                case "greeting":
                    int ranNum = new Random().nextInt(2);
                    switch (ranNum) {
                        case 0:
                            answer.append("Xin chào ").append(User.getInstance().getName()).append(", tớ có thể giúp gì cho cậu nào");
                            break;
                        case 1:
                            answer.append("Chào ").append(User.getInstance().getName()).append(", cậu cần tớ việc gì nhỉ?");
                            break;
                    }
                    break;
                case "number-of-favorite-words":
                    answer.append("Một con số tuyệt vời đó! Cậu đã đánh dấu ").append(User.getInstance().getNumberOfFavoriteWords()).append(" từ rồi.");
                    break;
            }
        }
        return answer.toString();
    }

    public void addQAtoFirestore(String category) {
        Scanner sc = new Scanner(System.in);
        System.out.println("question: ");
        String question = sc.nextLine();
        System.out.println("answer: ");
        String answer = sc.nextLine();
        DocumentReference docRef = FireStoreApp.getInstance().getQuestion_Answer().document(category);
        Map<String, Object> data = new HashMap<>();
        data.put("Category", category);
        try {
            data.put("Question", GoogleTranslate.translate("en", question));
        } catch (IOException e) {
            System.out.println("Error translate");
        }
        data.put("Answer", answer);

        ApiFuture<WriteResult> result = docRef.set(data);
        try {
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }

    public void addQAtoTXT(String category) {
        String filePath = "src/main/resources/Bin/faq-categorizer.txt";

        // Nội dung mới bạn muốn thêm vào file
        String contentToAppend = "\n";

        DocumentReference docRef = FireStoreApp.getInstance().getQuestion_Answer().document(category);
        try {
            DocumentSnapshot docSnap = docRef.get().get();
            if (docSnap.exists()) {
                contentToAppend += Objects.requireNonNull(docSnap.get("Category"))
                        + "\t" + Objects.requireNonNull(docSnap.get("Question"));
            }

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error in document snapshot");
        }

        try {
            // Mở file với tham số true để cho phép ghi thêm vào cuối file
            FileWriter fileWriter = new FileWriter(filePath, true);

            // Sử dụng BufferedWriter để ghi nội dung
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Thêm nội dung vào file
            bufferedWriter.write(contentToAppend);

            // Đóng BufferedWriter
            bufferedWriter.close();

            System.out.println("Nội dung đã được thêm vào file thành công.");

        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi thêm nội dung vào file: " + e.getMessage());
        }
    }

    public Map<String, String> getAnswerFromFireStore() {
        Map<String, String> answer = new HashMap<>();
        ApiFuture<QuerySnapshot> query = FireStoreApp.getInstance().getQuestion_Answer().get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error in query snapshot");
        }
        assert querySnapshot != null;
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            answer.put(document.getId(), Objects.requireNonNull(document.get("Answer")).toString());
        }
        return answer;
    }

    public static void main(String[] args) {
        for (int i = 24; i < 30; i++) {
            ChatBot.getInstance().addQAtoFirestore("000" + i);
            ChatBot.getInstance().addQAtoTXT("000" + i);
        }
    }
}
