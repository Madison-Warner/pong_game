package com.example.lab1.model;

import com.example.lab1.controller.MenuListener;
import javafx.scene.control.*;

public class GameMenu {
    private MenuBar menuBar;
    private MenuListener menuListener;

    /**
     * @param menuListener
     */
    public GameMenu(MenuListener menuListener) {
        this.menuListener = menuListener;
        initializeMenu();
    }

    private void initializeMenu() {
        menuBar = new MenuBar();
        Menu settingsMenu = new Menu("Settings");
        MenuItem saveItem = new MenuItem("Save Game");

        saveItem.setOnAction(e -> menuListener.saveGame());

        MenuItem loadItem = new MenuItem("Load Game");
        loadItem.setOnAction(e -> menuListener.loadGame());

        MenuItem playerNamesItem = new MenuItem("Set Player Names");
        playerNamesItem.setOnAction(e -> menuListener.setPlayerNames());

        MenuItem ballSpeedItem = new MenuItem("Set Ball Speed");
        ballSpeedItem.setOnAction(e -> menuListener.setBallSpeed());

        MenuItem racketDimensionsItem = new MenuItem("Adjust Racket Dimensions");
        racketDimensionsItem.setOnAction(e -> menuListener.setRacketDimensions());

        MenuItem winningScoreItem = new MenuItem("Set Winning Score");
        winningScoreItem.setOnAction(e -> menuListener.setWinningScore());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> menuListener.setExit());

        settingsMenu.getItems().addAll(
                saveItem, loadItem, playerNamesItem, ballSpeedItem,
                racketDimensionsItem, winningScoreItem, exitItem
        );
        menuBar.getMenus().add(settingsMenu);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
