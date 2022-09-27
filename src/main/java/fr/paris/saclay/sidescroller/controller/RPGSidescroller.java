package fr.paris.saclay.sidescroller.controller;

import javax.swing.*;
import java.awt.*;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class RPGSidescroller extends JFrame {

    public RPGSidescroller() {
        super("RPG Sidescroller");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);
        GamePanel gamePanel = new GamePanel();
        GameMenu gameMenu = new GameMenu(gamePanel, this, cardLayout);
        add(gamePanel, "Game Panel");
        add(gameMenu, "Game Menu");
        cardLayout.show(this.getContentPane(), "Game Menu");

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT+25)); // +25 is needed because JFrame takes into account the title bar


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
