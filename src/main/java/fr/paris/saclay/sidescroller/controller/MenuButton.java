package fr.paris.saclay.sidescroller.controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MenuButton extends JButton {

    private Image image;
    public MenuButton(String filename, String label, GameMenu menu) {
        setPreferredSize(new Dimension(menu.getPreferredSize().width/3, menu.getPreferredSize().height/5));
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/" + filename + ".png"));
            image = image.getScaledInstance(menu.getPreferredSize().width/3, menu.getPreferredSize().height/5, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(GrayFilter.createDisabledImage(image)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setForeground(Color.white);
        setFont(new Font("Monocraft", Font.PLAIN, 24));
        setBorder(new EmptyBorder(0,0,0,0));
        setText(label);
        setContentAreaFilled(false);
        setHorizontalTextPosition(JButton.CENTER);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(new ImageIcon(image));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(new ImageIcon(GrayFilter.createDisabledImage(image)));
            }
        });
    }
}
