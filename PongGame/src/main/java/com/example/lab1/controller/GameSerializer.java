package com.example.lab1.controller;
import com.example.lab1.model.Game;

import java.io.*;
public class GameSerializer {
    // Singleton instance
    private static GameSerializer instance = null;

    // Private constructor to prevent instantiation
    private GameSerializer() {
    }

    // Method to get the singleton instance
    public static synchronized GameSerializer getInstance() {
        if (instance == null) {
            instance = new GameSerializer();
        }
        return instance;
    }

    // Method to serialize the game object
    public void serializeGame(Game game, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(game);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    // Method to deserialize the game object
    public Game deserializeGame(String filename) {
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
