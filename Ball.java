package com.example.breakout_game;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {
    private Point2D moveVector = new Point2D(1, -1).normalize();
    private double velocity = 600;
    public static Point2D lastPos;

    public Ball() {
        x = -100;
        y = -100;
        width = height = canvasHeight * .015;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillOval(x, y, width, height);
    }

    public void setPosition(Point2D point) {
        this.x = point.getX() - width/2;
        this.y = point.getY() - height/2;
    }

    public void updatePosition(double diff) {
        if(x<0)
            x=0;
        if(x>640)
            x=640;
        if(y<0)
            y=0;
        lastPos=new Point2D(x,y);
        x += moveVector.getX() * velocity * diff;
        y += moveVector.getY() * velocity * diff;
    }

    public void bounceHorizontally(){
        setPosition(lastPos);
        moveVector=new Point2D(-moveVector.getX(),moveVector.getY());
    }
    public void bounceVertically(){
        setPosition(lastPos);
        moveVector=new Point2D(moveVector.getX(),-moveVector.getY());
    }
    public void bounceFromPaddle(double x){
        setPosition(lastPos);
        moveVector=new Point2D(1*x,-moveVector.getY());
    }

    public double getBallUpperEdge(){
        return getY();
    }
    public double getBallBottomEdge(){
        return getY()+getHeight();
    }
    public double getBallLeftEdge(){
        return getX();
    }
    public double getBallRightEdge(){
        return getX()+getWidth();
    }
    public Point2D getBallLastPos() { return lastPos; }
}
