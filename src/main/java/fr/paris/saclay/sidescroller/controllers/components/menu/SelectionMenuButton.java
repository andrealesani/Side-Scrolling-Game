package fr.paris.saclay.sidescroller.controllers.components.menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class SelectionMenuButton extends JButton {

    private Image image;

    public SelectionMenuButton(String filename) {
        setPreferredSize(new Dimension(30, 30));
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/menu/" + filename + ".png"));
            image = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIcon(new ImageIcon(image));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentAreaFilled(false);
        setHorizontalTextPosition(JButton.CENTER);
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
    }
}
