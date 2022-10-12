package fr.paris.saclay.sidescroller.controllers.components.menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.PRIMARY_COLOR;

public class ImagePreview extends JLabel {

    private boolean isBackground;

    public ImagePreview(int width, int height, String theme, boolean isBackground) {
        try {
            this.isBackground = isBackground;
            String filePath = "images/backgrounds/" + theme + "/background.png";
            if (!isBackground)
                filePath = "images/player/avatars/" + theme + "/alien_portrait.png";
            Image image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(filePath));
            image = image.getScaledInstance(isBackground ? 100 : 50, 50, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
            setFont(new Font("Monocraft", Font.PLAIN, 20));
            setForeground(Color.decode(PRIMARY_COLOR));
            setText(theme);
            setHorizontalTextPosition(JLabel.CENTER);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalTextPosition(JLabel.BOTTOM);
            setPreferredSize(new Dimension(width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTheme(String theme) {
        try {
            String filePath = "images/backgrounds/" + theme + "/background.png";
            if (!isBackground)
                filePath = "images/player/avatars/" + theme + "/alien_portrait.png";
            Image image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(filePath));
            image = image.getScaledInstance(isBackground ? 100 : 50, 50, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
            setFont(new Font("Monocraft", Font.PLAIN, 20));
            setForeground(Color.decode(PRIMARY_COLOR));
            setText(theme);
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
