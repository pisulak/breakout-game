package com.example.breakout_game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.breakout_game.GraphicsItem.canvasHeight;
import static com.example.breakout_game.GraphicsItem.canvasWidth;

public class GameCanvas extends Canvas {
    private GraphicsContext graphicsContext;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<>();
    static private Point2D [][] grid = new Point2D[20][9];
    private boolean gameRunning = false;
    private Integer points=0;

    private AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate;
        @Override
        public void handle(long now) {
            double diff = (now - lastUpdate)/1_000_000_000.;
            lastUpdate = now;
            ball.updatePosition(diff);
            draw();
            shouldBallBounceFromPaddle();
            shouldBallBounceHorizontally();
            shouldBallBounceVertically();
            shouldBallBounceFromBrick();
            shouldGameBeOver();
            graphicsContext.setFill(Color.YELLOW);
            graphicsContext.setFont(Font.font("Serif", FontWeight.BOLD, 38));
            graphicsContext.fillText(currentPoints(), 250, 50);
        }

        @Override
        public void start() {
            super.start();
            lastUpdate = System.nanoTime();
        }
    };

    public GameCanvas() {
        super(640, 700);

        this.setOnMouseMoved(mouseEvent -> {
            paddle.setPosition(mouseEvent.getX());
            if(!gameRunning)
                ball.setPosition(new Point2D(mouseEvent.getX(), paddle.getY() - ball.getWidth() / 2));
//            else
//                ball.updatePosition();
            draw();
        });

        this.setOnMouseClicked(mouseEvent -> {
            gameRunning = true;
            animationTimer.start();
        });
    }

    public void initialize() {
        graphicsContext = this.getGraphicsContext2D();
        GraphicsItem.setCanvasSize(getWidth(), getHeight());
        paddle = new Paddle();
        ball = new Ball();
        loadLevel();
    }

    private void loadLevel() {
        double rows=canvasWidth/9, cols=canvasHeight/20;
        for(int i = 0;i<20;i++){
            for (int j = 0;j<9;j++){
                grid[i][j] = new Point2D(rows*j,cols*i);
            }
        }
        for(int i = 2;i<8;i++) {
            for(int j = 0;j<9;j++){
                Integer temp=i-2;
                bricks.add(new Brick(i,j,Color.hsb(360-60*temp,1,1)));
            }
        }
    }
    static public Point2D getBrickPosition(int i, int j){
        return grid[i][j];
    }

    public void draw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);

        for (Brick brick:bricks) {
            brick.draw(graphicsContext);
        }
    }

    private void shouldBallBounceHorizontally(){
        if(ball.getX()<=0 || ball.getX()+(640*.015)>=640) {
            ball.bounceHorizontally();
        }
    }
    private void shouldBallBounceVertically(){
        if(ball.getY()<=0) {
            ball.bounceVertically();
        }
    }
    private void shouldBallBounceFromPaddle(){
        if(ball.getY() + ball.getHeight() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getHeight() && ball.getX() >= paddle.getX() && ball.getX() + ball.getWidth() <= paddle.getX() + paddle.getWidth()) {
            double temp=0, distance;
            distance = paddle.getWidth() - (paddle.getX() + paddle.getWidth() - ball.getX());
            if(distance < paddle.getWidth()/2) {
                distance = paddle.getWidth() / 2 - distance;
                temp = -distance * .015;
            }
            if(distance > paddle.getWidth()/2) {
                distance = distance - paddle.getWidth() / 2;
                temp = distance * .015;
            }
            ball.bounceFromPaddle(temp);
        }
    }
    private void shouldBallBounceFromBrick() {
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            Brick.CrushType crushType = brick.crush(ball.getBallUpperEdge(), ball.getBallBottomEdge(), ball.getBallLeftEdge(), ball.getBallRightEdge(), ball.getBallLastPos());
            if (crushType == Brick.CrushType.VerticalCrush) {
                for(int i=0; i<6; i++) {
                    if (brick.getColor() == 360-60*i)
                        points += 3 + i;
                }
                iterator.remove();
                ball.bounceVertically();
            } else if (crushType == Brick.CrushType.HorizontalCrush) {
                for(int i=0; i<6; i++) {
                    if (brick.getColor() == 360-60*i)
                        points += 3 + i;
                }
                iterator.remove();
                ball.bounceHorizontally();
            }
        }
    }
    private String currentPoints(){
        return "Points: "+points;
    }
    private void shouldGameBeOver(){
        if(ball.getY()>700){
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, getWidth(), getHeight());
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(Font.font("Serif", FontWeight.BOLD, 72));
            graphicsContext.fillText("You Lost:(", 150, 150);
        }
        if(bricks.isEmpty()){
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, getWidth(), getHeight());
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.setFont(Font.font("Serif", FontWeight.BOLD, 72));
            graphicsContext.fillText("Victory!", 210, 150);
        }
    }
}