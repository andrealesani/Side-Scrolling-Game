package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.swing.*;
import java.awt.*;

public class RPGSidescroller extends JFrame {
    public RPGSidescroller() throws HeadlessException {
        super("RPG Sidescroller");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        gamePanel.startGame();
        pack();
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
