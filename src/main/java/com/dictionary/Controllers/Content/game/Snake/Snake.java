package com.dictionary.Controllers.Content.game.Snake;

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
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final ArrayList<String> WORD_LIST = new ArrayList<>();
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
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
    private GraphicsContext gc;
    private Point snakeHead = new Point();
    private List<FoodWord> foodImage = new ArrayList<>();
    private String word = "freetime";
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;
    private String Cause;

    public static synchronized Snake getInstance() {
        if (snake == null) {
            snake = new Snake();
        }
        return snake;
    }

    public static void playSound(String soundFilePath) {
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

    @Override
    public void start(Stage primaryStage) {
        if(HeadImageDown == null) {
            System.out.println("Anhr null");
        }
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);//giao dien co the ve do hoa len do
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();
        playSound("C:\\Users\\LENOVO\\OneDrive\\Desktop\\OOP-Project\\src\\main\\resources\\snake\\sound\\sound1.wav");
        //Xử lí sự kiện
        scene.setOnKeyPressed(event -> {
            WORD_LIST.add("have");
            WORD_LIST.add("time");
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
            }
        });
        for (int i = 0; i < word.length(); i++) {
            String s = "/snake/img/" + word.charAt(i) + ".png";
            FoodWord foodWord = new FoodWord();
            foodWord.setImage(s);
            foodWord.setWord(word.charAt(i));
            foodImage.add(foodWord);
        }
        //Tạo con rắn ban đầu
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));//Lặp chạy chương trình vô hạn lần
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
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.setFont(new javafx.scene.text.Font("Digital-7", 50));
            gc.fillText("Game Over" + '\n' + Cause, WIDTH / 3.5, (double) HEIGHT / 2);
            return;
        }
        gc.clearRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        for (int i = 1; i < snakeBody.size(); i++) {
            gc.clearRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gc.clearRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
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
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(javafx.scene.paint.Color.web("#5032a8"));
                } else {
                    gc.setFill(javafx.scene.paint.Color.web("#5032a8"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
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
            gc.drawImage(food.getImage(), food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }

    }

    private void drawSnake(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.web("#f59642"));
        if (currentDirection == UP) {
            gc.drawImage(HeadImageUp, snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        else if (currentDirection == RIGHT) {
            gc.drawImage(HeadImageRight, snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        else if (currentDirection == LEFT) {
            gc.drawImage(HeadImageLeft, snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        else if (currentDirection == DOWN) {
            gc.drawImage(HeadImageDown, snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }


        for (int i = 1; i < snakeBody.size() - 1; i++) {
            gc.drawImage(BodyImage, snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        int tmp = snakeBody.size() - 1;
        int tmp1 = snakeBody.size() - 2;
        if (snakeBody.get(tmp1).getY() > snakeBody.get(tmp).getY()) {
            gc.drawImage(BottomImageUp, snakeBody.get(tmp).getX() * SQUARE_SIZE, snakeBody.get(tmp).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        else if (snakeBody.get(tmp1).getY() < snakeBody.get(tmp).getY()) {
            gc.drawImage(BottomImageDown, snakeBody.get(tmp).getX() * SQUARE_SIZE, snakeBody.get(tmp).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        else if (snakeBody.get(tmp1).getX() < snakeBody.get(tmp).getX()) {
            gc.drawImage(BottomImageRight, snakeBody.get(tmp).getX() * SQUARE_SIZE, snakeBody.get(tmp).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
        else if (snakeBody.get(tmp1).getX() > snakeBody.get(tmp).getX()) {
            gc.drawImage(BottomImageLeft, snakeBody.get(tmp).getX() * SQUARE_SIZE, snakeBody.get(tmp).getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
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
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        //destroy itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                Cause = "Bạn đã tự cắn mình";
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
                playSound("src/snake/sound/eatFood.wav");
                return;
            }
            if (snakeHead.getX() == foodImage.get(i).getX() && snakeHead.getY() == foodImage.get(i).getY() && foodImage.get(i).getWord() == foodImage.get(0).getWord() && i == 0) {
                snakeBody.add(new Point(-1, -1));
                foodImage.remove(i);
                score += 5;
                playSound("src/snake/sound/eatFood.wav");
                return;
            } else if (snakeHead.getX() == foodImage.get(i).getX() && snakeHead.getY() == foodImage.get(i).getY()) {
                gameOver = true;
                Cause = "Bạn đã ăn sai thứ tự";
            }
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }
}