package com.example.lab1.model;

import java.io.Serializable;

public class Racket implements Serializable {
    private double posX;
    private double posY;
    private int width;
    private int height;
    private boolean movingUp;
    private boolean movingDown;
    private double racketSpeed;

    /**
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public Racket(double posX, double posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.movingUp = false;
        this.movingDown = false;
        this.racketSpeed = 10; // Default racket speed
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void moveUp() {
        double newY = this.posY - racketSpeed; // Adjust moveSpeed as needed
        if (newY >= 0) { // Check if racket is not going above the top of the screen
            setPosY(newY);
        }
    }

    private void moveDown() {
        double newY = this.posY + racketSpeed; // Adjust moveSpeed as needed
        if (newY + height <= 600) { // Check if racket is not going below the bottom of the screen
            setPosY(newY);
        }
    }

    public void startMovingUp() {
        movingUp = true;
    }

    public void startMovingDown() {
        movingDown = true;
    }

    public void stopMovingUp() {
        movingUp = false;
    }

    public void stopMovingDown() {
        movingDown = false;
    }

    public void updatePosition() {
        if (movingUp) {
            moveUp();
        }
        if (movingDown) {
            moveDown();
        }
    }
}
