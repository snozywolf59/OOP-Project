package com.dictionary.Controllers;

import com.dictionary.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private Button enterButton;

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            init();
        } catch (MalformedURLException ex) {
            //Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        enterButton.setOnAction(event-> Model.getInstance().getViewFactory().showWindow());
    }
    public void init() throws MalformedURLException {
        //https://drive.google.com/file/d/1wQRBVtDA0p1Zc08Xx5Nkc_6smKrbEU8n/view?usp=sharing
        //https://drive.google.com/file/d/1AgIqp5az3sjNRES2Ii6d6XABbNxi3JnJ/view?usp=sharing
        String videoUrl = "https://drive.google.com/uc?id=1wQRBVtDA0p1Zc08Xx5Nkc_6smKrbEU8n";
        File file = new File("src/main/resources/video2.mp4");

        // Tạo một Media từ URL
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
//        mediaPlayer.setOnPlaying(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        //mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> {
            // Quay lại thời điểm ban đầu
            mediaPlayer.seek(mediaPlayer.getStartTime());
            // Chơi lại video
            mediaPlayer.play();
        });
        mediaPlayer.play();
    }
}
