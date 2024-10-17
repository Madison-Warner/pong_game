package com.example.lab1.controller;
import com.example.lab1.model.*;

public class PongController {
    private Game game;

    public PongController(Player player1, Player player2, Racket racket1, Racket racket2, Ball ball) {
        this.game = new Game(player1, player2, racket1, racket2, ball);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}