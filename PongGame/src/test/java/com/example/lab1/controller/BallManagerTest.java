package com.example.lab1.controller;

import com.example.lab1.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class BallManagerTest {

    private Game game;
    private PongCanvas canvas;
    private BallManager ballManager;

    @BeforeEach
    public void setUp() {
        // Initialize game objects
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Racket racket1 = new Racket(0, 0, 10, 100); // Example dimensions
        Racket racket2 = new Racket(0, 0, 10, 100); // Example dimensions
        Ball ball = new Ball(2, 100, 50); // Example speed and position

        game = new Game(player1, player2, racket1, racket2, ball);
        canvas = new PongCanvas(game);
        ballManager = new BallManager(game, canvas, new AtomicBoolean(true));
    }

    /**
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testCollisionWithRackets() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Simulate ball's position and speed for collision testing
        Ball ball = game.getBall();
        ball.setPosX(100);
        ball.setPosY(50);
        ball.setSpeedX(-2);
        ball.setSpeedY(1);

        // Set rackets positions
        game.getRacket1().setPosX(50);
        game.getRacket1().setPosY(0);
        game.getRacket1().setWidth(10);
        game.getRacket1().setHeight(100);

        game.getRacket2().setPosX(200);
        game.getRacket2().setPosY(0);
        game.getRacket2().setWidth(10);
        game.getRacket2().setHeight(100);

        // Use reflection to access and invoke the private method
        Method method = BallManager.class.getDeclaredMethod("checkCollisions");
        method.setAccessible(true);
        method.invoke(ballManager);

        // Check if the ball's X speed is reversed after collision with the rackets
        assertEquals(2, ball.getSpeedX());
    }

    /**
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testCollisionWithBorders() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Simulate ball's position and speed for collision testing
        Ball ball = game.getBall();
        ball.setPosX(5);
        ball.setPosY(50);
        ball.setSpeedX(-2);
        ball.setSpeedY(1);

        // Use reflection to access and invoke the private method
        Method method = BallManager.class.getDeclaredMethod("checkCollisions");
        method.setAccessible(true);
        method.invoke(ballManager);

        // Check if the ball's X speed is reversed after collision with left border
        assertEquals(2, ball.getSpeedX());

        // Modify ball's position and speed for testing other collisions
        ball.setPosX(395);
        ball.setSpeedX(2);

        method.invoke(ballManager);

        // Check if the ball's X speed is reversed after collision with right border
        assertEquals(-2, ball.getSpeedX());

        ball.setPosX(50);
        ball.setPosY(5);
        ball.setSpeedX(1);
        ball.setSpeedY(-2);

        method.invoke(ballManager);

        // Check if the ball's Y speed is reversed after collision with top border
        assertEquals(2, ball.getSpeedY());

        // Modify ball's position and speed for testing collision with bottom border
        ball.setPosY(595);
        ball.setSpeedY(2);

        method.invoke(ballManager);

        // Check if the ball's Y speed is reversed after collision with bottom border
        assertEquals(-2, ball.getSpeedY());
    }
}