package com.example.lab1.model;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int score;

    /**
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() { this.score += 1; }

    public void resetScore() { this.score = 0; }
}