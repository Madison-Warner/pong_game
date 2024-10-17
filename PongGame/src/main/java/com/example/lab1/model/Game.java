package com.example.lab1.model;

import java.io.*;

public class Game implements Serializable {
    private Player player1;
    private Player player2;
    private int winningScore;
    private Ball ball;
    private Racket racket1;
    private Racket racket2;
    private PongCanvas pongCanvas;
    private boolean paused;

    /**
     * @param player1
     * @param player2
     * @param racket1
     * @param racket2
     * @param ball
     */
    public Game(Player player1, Player player2, Racket racket1, Racket racket2, Ball ball) {
        // Set initial values for the game attributes
        this.player1 = player1;
        this.player2 = player2;
        this.winningScore = 10; // Default winning score
        this.ball = ball;
        this.racket1 = racket1; // Default racket width
        this.racket2 = racket2; // Default racket height
        this.paused = false;
    }

    public void update() {
        if (!paused) {
            // Update game state here (e.g., check for collisions, update ball position, etc.)
            racket1.updatePosition();
            racket2.updatePosition();
            ball.move();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    /**
     * @param player1
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     * @param player2
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getWinningScore() {
        return winningScore;
    }

    /**
     * @param winningScore
     */
    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }

    public Ball getBall() {
        return ball;
    }

    /**
     * @param ball
     */
    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Racket getRacket1() {
        return racket1;
    }

    /**
     * @param racket1
     */
    public void setRacket1(Racket racket1) {
        this.racket1 = racket1;
    }

    public Racket getRacket2() { return racket2; }

    /**
     * @param racket2
     */
    public void setRacket2(Racket racket2) {
        this.racket2 = racket2;
    }

    public PongCanvas getPongCanvas() {
        return pongCanvas;
    }

    /**
     * @param pongCanvas
     */
    public void setPongCanvas(PongCanvas pongCanvas) {
        this.pongCanvas = pongCanvas;
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
        System.out.println("Game is paused");
    }

    public void resume() {
        paused = false;
        System.out.println("Game is resumed");
    }

    /**
     * Saves the game state to a file.
     * @param filename The filename to save the game state to.
     */
    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads the game state from a file.
     * @param filename The filename to load the game state from.
     * @return The loaded Game object, or null if loading fails.
     */
    public static Game loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Game game = (Game) in.readObject();
            System.out.println("Game loaded successfully.");
            return game;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
