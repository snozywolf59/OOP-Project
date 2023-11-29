package com.dictionary.Controllers.Content.game.Snake;

import com.dictionary.Models.Model;
import com.dictionary.Models.game.snake.FoodWord;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Snake extends Application {
    private static Snake snake;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int ROWS = 30;
    private static final int COLUMNS = ROWS;
    private static final int WIDTH_SIZE = WIDTH / ROWS;
    private static final int HEIGHT_SIZE = HEIGHT / ROWS;
    private static final ArrayList<String> WORD_LIST = new ArrayList<>();
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static final int RESTART = 4;
    private final java.util.List<Point> snakeBody = new ArrayList<>();
    private final Image HeadImageDown = new Image(getClass().getResourceAsStream("/snake/img/down.png"));
    private final Image HeadImageUp = new Image(getClass().getResourceAsStream("/snake/img/up.png"));
    private final Image HeadImageRight = new Image(getClass().getResourceAsStream("/snake/img/right.png"));
    private final Image HeadImageLeft = new Image(getClass().getResourceAsStream("/snake/img/left.png"));
    private final Image BodyImage = new Image(getClass().getResourceAsStream("/snake/img/Body.png"));
    private final Image BottomImageDown = new Image(getClass().getResourceAsStream("/snake/img/downBottom.png"));
    private final Image BottomImageUp = new Image(getClass().getResourceAsStream("/snake/img/upBottom.png"));
    private final Image BottomImageRight = new Image(getClass().getResourceAsStream("/snake/img/rightBottom.png"));
    private final Image BottomImageLeft = new Image(getClass().getResourceAsStream("/snake/img/LeftBottom.png"));
    private final Image backGround = new Image(getClass().getResourceAsStream("/snake/img/backGround.png"));
    private Clip clips;
    private GraphicsContext gc;
    private Point snakeHead = new Point();
    private List<FoodWord> foodImage = new ArrayList<>();
    private String word = "freetime";
    private boolean gameOver = false;
    private int currentDirection;
    private int score = 0;
    private String cause;

    public Stage stage;
    public static Timeline timeline;
    public static synchronized Snake getInstance() {
        if (snake == null) {
            snake = new Snake();
        }
        return snake;
    }






    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);//giao dien co the ve do hoa len do
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();
        File soundFile = new File("src/main/resources/snake/sound/sound1.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clips = AudioSystem.getClip();
            clips.open(audioInputStream);
            clips.start();
            clips.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        //Xử lí sự kiện
        scene.setOnKeyPressed(event -> {
            WORD_LIST.add("have");
            WORD_LIST.add("time");
            WORD_LIST.add("yummy");
            WORD_LIST.add("yeasty");
            WORD_LIST.add("yogurt");
            WORD_LIST.add("duck");
            WORD_LIST.add("banana");
            WORD_LIST.add("table");
            WORD_LIST.add("pen");
            WORD_LIST.add("draw");
            WORD_LIST.add("next");
            WORD_LIST.add("gold");
            WORD_LIST.add("year");
            WORD_LIST.add("month");
            WORD_LIST.add("king");
            WORD_LIST.add("moon");
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (currentDirection != LEFT) {
                    currentDirection = RIGHT;
                }
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (currentDirection != RIGHT) {
                    currentDirection = LEFT;
                }
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (currentDirection != DOWN) {
                    currentDirection = UP;
                }
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (currentDirection != UP) {
                    currentDirection = DOWN;
                }
            } else if(code == KeyCode.SPACE) {
                currentDirection = RESTART;
            } else if(code == KeyCode.ESCAPE) {
                currentDirection = 5;
            }
        });
        restart();
        timeline = new Timeline(new KeyFrame(Duration.millis(140), e -> run(gc)));//Lặp chạy chương trình vô hạn lần
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        if (foodImage.isEmpty()) {
            WORD_LIST.remove(0);
            word = WORD_LIST.get(0);
            foodImage = new ArrayList<>();
            for (int i = 0; i < word.length(); i++) {
                String s = "/snake/img/" + word.charAt(i) + ".png";
                System.out.print(s);
                FoodWord foodWord = new FoodWord();
                foodWord.setImage(s);
                foodWord.setWord(word.charAt(i));
                foodImage.add(foodWord);
            }
            generateFood();
        }
        if (gameOver) {
            if (currentDirection == 4) {
                restart();
            }
//            stage.setScene(Model.getInstance().getViewFactory().getSCENE());
            gc.setFill(Color.BLACK);
            gc.setFont(new javafx.scene.text.Font("Digital-7", 40));
            gc.fillText("Game Over" , (double) WIDTH / 3 + 100, (double) HEIGHT / 2);
            gc.fillText("Press SPACE to restart" , WIDTH / 4 + 100, (double) HEIGHT / 2 + 60);
            gc.fillText("Press ESC to Exit" , (double) WIDTH / 4 + 130, (double) HEIGHT / 2 + 120);
            if(currentDirection == 5) {
                timeline.stop();
                clips.stop();
                stage.setScene(Model.getInstance().getViewFactory().getScene());
            }
            return;
        }
        gc.clearRect(snakeHead.getX() * WIDTH_SIZE, snakeHead.getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        for (int i = 1; i < snakeBody.size(); i++) {
            gc.clearRect(snakeBody.get(i).getX() * WIDTH_SIZE, snakeBody.get(i).getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gc.clearRect(i * WIDTH_SIZE, j * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
            }
        }
        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore();

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }



        switch (currentDirection) {
            case RIGHT -> moveRight();
            case LEFT -> moveLeft();
            case UP -> moveUp();
            case DOWN -> moveDown();
        }

        gameOver();
        eatFood();
    }

    //Tô màu background
    private void drawBackground(GraphicsContext gc) {
        gc.drawImage(backGround, 0 , 0, WIDTH, HEIGHT);
    }

    private void generateFood() {
        int count = 0;
        start:
        while (true) {
            int foodX = (int) (Math.random() * ROWS);
            int foodY = (int) (Math.random() * COLUMNS);

            for (Point snake : snakeBody) {
                for (FoodWord food : foodImage) {
                    if ((snake.getX() == foodX && snake.getY() == foodY) || (snake.getX() == food.getX() && snake.getY() == food.getY())) {
                        continue start;
                    }
                }
            }
            if (count != foodImage.size()) {
                FoodWord food = foodImage.get(count);
                food.setX(foodX);
                food.setY(foodY);
                foodImage.set(count, food);
                count++;
            } else break;
        }
    }

    private void drawFood(GraphicsContext gc) {
        for (FoodWord food : foodImage) {
            gc.drawImage(food.getImage(), food.getX() * WIDTH_SIZE, food.getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
    }

    private void drawSnake(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.web("#f59642"));
        if (currentDirection == UP) {
            gc.drawImage(HeadImageUp, snakeHead.getX() * WIDTH_SIZE, snakeHead.getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        else if (currentDirection == RIGHT) {
            gc.drawImage(HeadImageRight, snakeHead.getX() * WIDTH_SIZE, snakeHead.getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        else if (currentDirection == LEFT) {
            gc.drawImage(HeadImageLeft, snakeHead.getX() * WIDTH_SIZE, snakeHead.getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        else if (currentDirection == DOWN) {
            gc.drawImage(HeadImageDown, snakeHead.getX() * WIDTH_SIZE, snakeHead.getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }


        for (int i = 1; i < snakeBody.size() - 1; i++) {
            gc.drawImage(BodyImage, snakeBody.get(i).getX() * WIDTH_SIZE, snakeBody.get(i).getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        int tmp = snakeBody.size() - 1;
        int tmp1 = snakeBody.size() - 2;
        if (snakeBody.get(tmp1).getY() > snakeBody.get(tmp).getY()) {
            gc.drawImage(BottomImageUp, snakeBody.get(tmp).getX() * WIDTH_SIZE, snakeBody.get(tmp).getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        else if (snakeBody.get(tmp1).getY() < snakeBody.get(tmp).getY()) {
            gc.drawImage(BottomImageDown, snakeBody.get(tmp).getX() * WIDTH_SIZE, snakeBody.get(tmp).getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        else if (snakeBody.get(tmp1).getX() < snakeBody.get(tmp).getX()) {
            gc.drawImage(BottomImageRight, snakeBody.get(tmp).getX() * WIDTH_SIZE, snakeBody.get(tmp).getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
        else if (snakeBody.get(tmp1).getX() > snakeBody.get(tmp).getX()) {
            gc.drawImage(BottomImageLeft, snakeBody.get(tmp).getX() * WIDTH_SIZE, snakeBody.get(tmp).getY() * HEIGHT_SIZE, WIDTH_SIZE, HEIGHT_SIZE);
        }
    }

    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * WIDTH_SIZE >= WIDTH || snakeHead.y * HEIGHT_SIZE >= HEIGHT) {
            gameOver = true;
        }

        //destroy itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                cause = "Bạn đã tự cắn mình";
                break;
            }
        }
    }

    private void eatFood() {

        for (int i = 0; i < foodImage.size(); i++) {
            if (snakeHead.getX() == foodImage.get(i).getX() && snakeHead.getY() == foodImage.get(i).getY() && foodImage.get(i).getWord() == foodImage.get(0).getWord() && i != 0) {
                snakeBody.add(new Point(-1, -1));
                FoodWord food = foodImage.get(0);
                foodImage.set(i, food);
                foodImage.remove(0);
                score += 5;
                playSound("src/main/resources/snake/sound/eatFood.wav");
                return;
            }
            if (snakeHead.getX() == foodImage.get(i).getX() && snakeHead.getY() == foodImage.get(i).getY() && foodImage.get(i).getWord() == foodImage.get(0).getWord() && i == 0) {
                snakeBody.add(new Point(-1, -1));
                foodImage.remove(i);
                score += 5;
                playSound("src/main/resources/snake/sound/eatFood.wav");
                return;
            } else if (snakeHead.getX() == foodImage.get(i).getX() && snakeHead.getY() == foodImage.get(i).getY()) {
                gameOver = true;
                cause = "Bạn đã ăn sai thứ tự";
            }
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public void playSound(String soundFilePath) {
        File soundFile = new File(soundFilePath);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        currentDirection = 0;
        gameOver = false;
        foodImage.clear();
        for (int i = 0; i < word.length(); i++) {
            String s = "/snake/img/" + word.charAt(i) + ".png";
            FoodWord foodWord = new FoodWord();
            foodWord.setImage(s);
            foodWord.setWord(word.charAt(i));
            foodImage.add(foodWord);
        }
        snakeBody.clear();
        //Tạo con rắn ban đầu
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();
    }
}
