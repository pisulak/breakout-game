package com.example.breakout_game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {
    private static int gridRows;
    private static int gridCols;
    private Color color;
    public Brick(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        width = canvasWidth / 9;
        height = canvasHeight / 20;
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getColor(){
        return color.getHue();
    }
    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.fillRect(GameCanvas.getBrickPosition((int) x, (int) y).getX(), GameCanvas.getBrickPosition((int) x, (int) y).getY(), width-0.5, height-0.5);
        graphicsContext.setFill(color);
    }
    public enum CrushType {
        NoCrush,
        HorizontalCrush,
        VerticalCrush
    }
    public CrushType crush(double upper, double lower, double left, double right, Point2D lastPos) {
        if(GameCanvas.getBrickPosition((int) x, (int) y).getY()<=upper && GameCanvas.getBrickPosition((int) x, (int) y).getY()+getHeight()>=lower && GameCanvas.getBrickPosition((int) x, (int) y).getX()<=left && GameCanvas.getBrickPosition((int) x, (int) y).getX()+getWidth()>=right) {
            if(GameCanvas.getBrickPosition((int) x, (int) y).getY() <= lastPos.getY() || GameCanvas.getBrickPosition((int) x, (int) y).getY()+getHeight() >= lastPos.getY())
                return CrushType.VerticalCrush;
            if(GameCanvas.getBrickPosition((int) x, (int) y).getX() <= lastPos.getX() || GameCanvas.getBrickPosition((int) x, (int) y).getX()+getWidth() >= lastPos.getX())
                return CrushType.HorizontalCrush;
        }
        return CrushType.NoCrush;
    }
}
