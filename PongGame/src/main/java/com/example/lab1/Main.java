package com.example.lab1;

import com.example.lab1.controller.*;
import com.example.lab1.model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    /**
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize game values
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        double initialRacketsPosY = (600 / 2) - (100 / 2);
        Racket racket1 = new Racket(10, initialRacketsPosY, 30, 100); // Initial racket dimensions
        Racket racket2 = new Racket(1000 - 10 - 30, initialRacketsPosY, 30, 100); // Initial racket dimensions
        Ball ball = new Ball(2, 500, 300);

        // Initialize game and other components
        PongController pongController = new PongController(player1, player2, racket1, racket2, ball);
        Game game = pongController.getGame();
        PongCanvas pongCanvas = new PongCanvas(game);
        pongCanvas.requestFocus();
        MenuListener menuListener = new MenuListener(game, pongCanvas);
        GameMenu gameMenu = new GameMenu(menuListener);

        KeyboardListener keyboardListener = new KeyboardListener(game, pongCanvas);
        setupKeyListeners(pongCanvas, keyboardListener);

        // Set up the root layout
        BorderPane root = new BorderPane();
        root.setTop(gameMenu.getMenuBar());
        root.setCenter(pongCanvas);

        // Set up the scene
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong Game");
        primaryStage.show();

        // Start BallManager
        AtomicBoolean running = new AtomicBoolean(true);
        BallManager ballManager = new BallManager(game, pongCanvas, running);
        Thread ballManagerThread = new Thread(ballManager);
        ballManagerThread.setDaemon(true); // Set as daemon thread to stop when the application closes
        ballManagerThread.start();

        // Use AnimationTimer to continuously update the game
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update();
                pongCanvas.drawCanvas();
            }
        }.start();

        // Serialize the game
        GameSerializer.getInstance().serializeGame(game, "saved_game.ser");

        // Deserialize the game
        Game loadedGame = GameSerializer.getInstance().deserializeGame("saved_game.ser");
        if (loadedGame != null) {
            // Update the game state with the loaded game
            pongController.setGame(loadedGame);
        }
    }

    /**
     * @param pongCanvas
     * @param keyboardListener
     */
    private void setupKeyListeners(PongCanvas pongCanvas, KeyboardListener keyboardListener) {
        pongCanvas.setOnKeyPressed(event -> keyboardListener.handleKeyPressed(event.getCode()));
        pongCanvas.setOnKeyReleased(event -> keyboardListener.handleKeyReleased(event.getCode()));
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}