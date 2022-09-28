package fr.paris.saclay.sidescroller.controller;

import javax.swing.*;
import java.awt.*;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class RPGSidescroller extends JFrame {

    private GamePanel gamePanel;

    private GameMenu gameMenu;

    public RPGSidescroller() {
        super("RPG Sidescroller");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel(this);
        gameMenu = new GameMenu(this);
        add(gamePanel);
        setGlassPane(gameMenu);
        getGlassPane().setVisible(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT+25)); // +25 is needed because JFrame takes into account the title bar
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }
}
