package com.example.lab1.controller;

import com.example.lab1.model.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.io.*;

import java.util.Optional;

public class MenuListener {
    private Game game;
    private PongCanvas pongCanvas;

    /**
     * @param game
     * @param pongCanvas
     */
    public MenuListener(Game game, PongCanvas pongCanvas) {
        this.game = game;
        this.pongCanvas = pongCanvas;
    }

    public void setExit() {
        System.out.println("EXIT");
        Platform.exit();
    }

    public void setPlayerNames() {
        TextInputDialog dialog1 = new TextInputDialog(game.getPlayer1().getName());
        dialog1.setHeaderText("Enter name for Player 1:");
        dialog1.setTitle("Set Player Names");
        dialog1.setContentText("Name:");

        TextInputDialog dialog2 = new TextInputDialog(game.getPlayer2().getName());
        dialog2.setHeaderText("Enter name for Player 2:");
        dialog2.setTitle("Set Player Names");
        dialog2.setContentText("Name:");

        dialog1.showAndWait().ifPresent(name -> {
            game.getPlayer1().setName(name);
            System.out.println("Player 1 name has changed to " + name);
            pongCanvas.drawCanvas(); // Redraw canvas after setting player names
        });

        dialog2.showAndWait().ifPresent(name -> {
            game.getPlayer2().setName(name);
            System.out.println("Player 2 name has changed to " + name);
            pongCanvas.drawCanvas(); // Redraw canvas after setting player names
        });
    }

    public void setWinningScore() {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(game.getWinningScore()));
        dialog.setTitle("Set Winning Score");
        dialog.setHeaderText("Enter the winning score:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(score -> {
            try {
                int newScore = Integer.parseInt(score);
                if (newScore <= 0) {
                    throw new IllegalArgumentException("Winning score must be a positive integer.");
                }
                game.setWinningScore(newScore);
                System.out.println("Winning target has changed to " + newScore);
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                showErrorAlert(e.getMessage());
            }
        });
    }

    public void setBallSpeed() {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(game.getBall().getSpeedX()));
        dialog.setTitle("Set Ball Speed");
        dialog.setHeaderText("Enter the ball speed:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(speed -> {
            try {
                int newSpeedX = Integer.parseInt(speed);
                int newSpeedY = newSpeedX; // Set same speed for Y direction for simplicity

                if (newSpeedX <= 0 || newSpeedY <= 0 || newSpeedX >= 5 || newSpeedY >= 5) {
                    throw new IllegalArgumentException("Ball speed must be a positive integer less than 15.");
                }

                game.getBall().setSpeedX(newSpeedX);
                game.getBall().setSpeedY(newSpeedY);
                System.out.println("Ball speed has changed to " + newSpeedX + " (X) and " + newSpeedY + " (Y)");
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                showErrorAlert(e.getMessage());
            }
        });
    }

    public void setRacketDimensions() {
        TextInputDialog widthDialog = new TextInputDialog(String.valueOf(game.getRacket1().getWidth()));
        widthDialog.setTitle("Adjust Racket Dimensions");
        widthDialog.setHeaderText("Enter the new width for the racket:");
        TextInputDialog heightDialog = new TextInputDialog(String.valueOf(game.getRacket1().getHeight()));
        heightDialog.setTitle("Adjust Racket Dimensions");
        heightDialog.setHeaderText("Enter the new height for the racket:");
        widthDialog.showAndWait().ifPresent(width -> {
            heightDialog.showAndWait().ifPresent(height -> {
                try {
                    int newWidth = Integer.parseInt(width);
                    int newHeight = Integer.parseInt(height);
                    if (newWidth <= 0 || newHeight <= 0) {
                        throw new IllegalArgumentException("Dimensions must be positive integers.");
                    }
                    game.getRacket1().setWidth(newWidth);
                    game.getRacket1().setHeight(newHeight);
                    game.getRacket2().setWidth(newWidth);
                    game.getRacket2().setHeight(newHeight);
                    System.out.println("Racket dimensions have changed to " + newWidth + " wide " + newHeight + " high.");
                    pongCanvas.drawCanvas(); // Redraw canvas after setting player names
                } catch (NumberFormatException e) {
                    showErrorAlert("Invalid input. Please enter valid integers.");
                } catch (IllegalArgumentException e) {
                    showErrorAlert(e.getMessage());
                }
            });
        });
    }

    public void saveGame() {
        GameSerializer.getInstance().serializeGame(game, "saved_game.ser");
        // Show confirmation popup
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Saved");
        alert.setHeaderText(null);
        alert.setContentText("Game saved successfully.");
        alert.showAndWait();
    }

    public void loadGame() {
        Game loadedGame = GameSerializer.getInstance().deserializeGame("saved_game.ser");
        if (loadedGame != null) {
            game.setPlayer1(loadedGame.getPlayer1());
            game.setPlayer2(loadedGame.getPlayer2());
            game.setWinningScore(loadedGame.getWinningScore());
            game.setBall(loadedGame.getBall());
            game.setRacket1(loadedGame.getRacket1());
            game.setRacket2(loadedGame.getRacket2());
            System.out.println("Game loaded successfully.");
            pongCanvas.drawCanvas();
        }
    }

    /**
     * @param message
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
