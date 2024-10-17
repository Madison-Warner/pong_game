package com.example.lab1.model;

import com.example.lab1.controller.MenuListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PongCanvas extends Canvas {
    private final int DEFAULT_WIDTH = 1000;
    private final int DEFAULT_HEIGHT = 600;
    private final double BALL_RADIUS = 10;

    private Game game;

    /**
     * @param game
     */
    public PongCanvas(Game game) {
        super();
        this.game = game;
        this.setWidth(DEFAULT_WIDTH);
        this.setHeight(DEFAULT_HEIGHT);
        this.setFocusTraversable(true);
    }

    public void drawCanvas() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        double player1NamePosX = 20;
        double player2NamePosX = this.getWidth() - 20 - (gc.getFont().getSize() * game.getPlayer2().getName().length());
        double playerNamePosY = 20;
        double playerScorePosY = 35;

        // Draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw rackets
        gc.setFill(Color.YELLOW);
        gc.fillRect(game.getRacket1().getPosX(), game.getRacket1().getPosY(), game.getRacket1().getWidth(), game.getRacket1().getHeight()); // Player 1
        gc.fillRect(game.getRacket2().getPosX(), game.getRacket2().getPosY(), game.getRacket2().getWidth(), game.getRacket2().getHeight()); // Player 2

        // Draw player names
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText(game.getPlayer1().getName(), player1NamePosX, playerNamePosY); // Player 1 name
        gc.fillText(game.getPlayer2().getName(), player2NamePosX, playerNamePosY); // Player 2 name

        // Draw player scores
        gc.fillText(String.valueOf(game.getPlayer1().getScore()), player1NamePosX, playerScorePosY); // Player 1 score
        gc.fillText(String.valueOf(game.getPlayer2().getScore()), player2NamePosX, playerScorePosY); // Player 2 score

        // Draw ball in the center
        gc.setFill(Color.WHITE);
        gc.fillOval(game.getBall().getPosX(), game.getBall().getPosY(), 2 * game.getBall().getRadius(), 2 * game.getBall().getRadius());
    }
}