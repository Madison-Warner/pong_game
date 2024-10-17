package com.example.lab1.controller;

import com.example.lab1.model.Ball;
import com.example.lab1.model.Game;
import com.example.lab1.model.Player;
import com.example.lab1.model.PongCanvas;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Rectangle;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class BallManager implements Runnable {
    private final Game game;
    private final PongCanvas canvas;
    private final AtomicBoolean running;

    /**
     * @param game
     * @param canvas
     * @param running
     */
    public BallManager(Game game, PongCanvas canvas, AtomicBoolean running) {
        this.game = game;
        this.canvas = canvas;
        this.running = running;
    }

    public void pause() {
        game.getBall().pause(); // Pause the ball movement
    }

    public void resume() {
        game.getBall().resume(); // Resume the ball movement
    }

    @Override
    public void run() {
        Ball ball = game.getBall();
        while (running.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            if (!game.isPaused()) {
                ball.move();
            }
            checkCollisions();
            checkGameEnd();
            canvas.drawCanvas();
        }
    }

    private void checkCollisions() {
        Rectangle racket1Bounds = new Rectangle(game.getRacket1().getPosX(), game.getRacket1().getPosY(),
                game.getRacket1().getWidth(), game.getRacket1().getHeight());
        Rectangle racket2Bounds = new Rectangle(game.getRacket2().getPosX(), game.getRacket2().getPosY(),
                game.getRacket2().getWidth(), game.getRacket2().getHeight());
        if (racket1Bounds.intersects(game.getBall().getPosX(), game.getBall().getPosY(), game.getBall().getRadius(), game.getBall().getRadius())
                || racket2Bounds.intersects(game.getBall().getPosX(), game.getBall().getPosY(), game.getBall().getRadius(), game.getBall().getRadius())) {
            // Bounce the ball in opposite X direction
            game.getBall().setSpeedX(-game.getBall().getSpeedX());
        }
        if (game.getBall().getPosX() < game.getBall().getRadius() || game.getBall().getPosX() > canvas.getWidth() - game.getBall().getRadius()) {
            // Player scores
            if (game.getBall().getPosX() < game.getBall().getRadius()) {
                game.getPlayer2().incrementScore();
                System.out.println("Player 2 Scored!");
            } else {
                game.getPlayer1().incrementScore();
                System.out.println("Player 1 Scored!");
            }
            // Reset ball position
            resetBallPosition();
        }
        if (game.getBall().getPosY() < game.getBall().getRadius() || game.getBall().getPosY() > canvas.getHeight() - game.getBall().getRadius()) {
            // Bounce the ball in opposite Y direction
            game.getBall().setSpeedY(-game.getBall().getSpeedY());
        }
    }

    private boolean gameWon = false; // Add this flag

    private void checkGameEnd() {
        if (!gameWon && (game.getPlayer1().getScore() >= game.getWinningScore() || game.getPlayer2().getScore() >= game.getWinningScore())) {
            gameWon = true; // Set the flag to true when the game is won

            // Declare winner
            Player winner = game.getPlayer1().getScore() > game.getPlayer2().getScore() ? game.getPlayer1() : game.getPlayer2();
            String winningMessage = "Game Over! " + winner.getName() + " wins!";
            System.out.println(winningMessage);

            // Pause the game
            pause();

            // Display winner alert with a rematch button
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText("Winner: " + winner.getName());
                alert.setContentText("Do you want to rematch?");
                ButtonType rematchButton = new ButtonType("Rematch");
                ButtonType exitButton = new ButtonType("Exit");
                alert.getButtonTypes().setAll(rematchButton, exitButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == rematchButton) {
                    restartGame();
                    resume(); // Resume the game after rematch is chosen
                } else {
                    // Exit the game
                    System.exit(0);
                }
            });
        }
    }

    private void resetBallPosition() {
        Ball ball = game.getBall();
        ball.setPosX(canvas.getWidth() / 2);
        ball.setPosY(canvas.getHeight() / 2);
    }

    private void restartGame() {
        resetBallPosition();
        game.getPlayer1().resetScore();
        game.getPlayer2().resetScore();
    }
}

