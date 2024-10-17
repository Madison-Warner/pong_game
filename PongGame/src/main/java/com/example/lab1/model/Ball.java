package com.example.lab1.model;

import java.io.Serializable;

public class Ball implements Serializable {
    private double radius;
    private int speedX;
    private int speedY;
    private double posX;
    private double posY;
    private boolean paused;


    /**
     * @param speed
     * @param posX
     * @param posY
     */
    public Ball( int speed, double posX, double posY) {
        this.radius = 10;
        this.speedX = speed;
        this.speedY = speed;
        this.posX = posX;
        this.posY = posY;
        this.paused = false;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getSpeedX(){
        return speedX;
    }
    public int getSpeedY(){ return speedY; }

    /**
     * @param speed
     */
    public void setSpeedX(int speed){
        this.speedX = speed;
    }

    /**
     * @param speed
     */
    public void setSpeedY(int speed){ this.speedY = speed; }

    public void move() {
        if (!paused) {
            double newX = this.posX + this.speedX;
            double newY = this.posY + this.speedY;
            setPosX(newX);
            setPosY(newY);
        }
    }

    public double getPosX() { return posX; }

    public double getPosY(){ return posY; }

    /**
     * @param posX
     */
     public void setPosX(double posX){ this.posX = posX; }

    /**
     * @param posY
     */
     public void setPosY(double posY){ this.posY = posY; }
    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
        System.out.println("Ball movement is paused");
    }

    public void resume() {
        paused = false;
        System.out.println("Ball movement is resumed");
    }
}