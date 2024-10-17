package com.example.lab1.controller;

import javafx.scene.input.KeyCode;
import com.example.lab1.model.*;

public class KeyboardListener {
    private Game game;
    private PongCanvas pongCanvas;
    private boolean isPaused = false;

    /**
     * @param game
     * @param pongCanvas
     */
    public KeyboardListener(Game game, PongCanvas pongCanvas) {
        this.game = game;
        this.pongCanvas = pongCanvas;
    }

    /**
     * @param keyCode
     */
    public void handleKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.ESCAPE) {
            // Toggle pause state
            isPaused = !isPaused;
            System.out.println("Game is " + (isPaused ? "paused" : "resumed"));
            if (isPaused) {
                // Pause the game
                game.pause();
            } else {
                // Resume the game
                game.resume();
            }
        } else {
            if (!isPaused) {
                // Handle other key presses only when the game is not paused
                switch (keyCode) {
                    case UP:
                        game.getRacket2().startMovingUp();
                        break;
                    case DOWN:
                        game.getRacket2().startMovingDown();
                        break;
                    case W:
                        game.getRacket1().startMovingUp();
                        break;
                    case S:
                        game.getRacket1().startMovingDown();
                        break;
                    default:
                        // Do nothing for other keys
                        break;
                }
                game.getRacket1().updatePosition();
                game.getRacket2().updatePosition();
                pongCanvas.drawCanvas();
            }
        }
    }

    public void handleKeyReleased(KeyCode keyCode) {
        if (!isPaused) {
            // Handle key releases only when the game is not paused
            switch (keyCode) {
                case UP:
                    game.getRacket2().stopMovingUp();
                    break;
                case DOWN:
                    game.getRacket2().stopMovingDown();
                    break;
                case W:
                    game.getRacket1().stopMovingUp();
                    break;
                case S:
                    game.getRacket1().stopMovingDown();
                    break;
                default:
                    // Do nothing for other keys
                    break;
            }
            game.getRacket1().updatePosition();
            game.getRacket2().updatePosition();
            pongCanvas.drawCanvas();
        }
    }
}
