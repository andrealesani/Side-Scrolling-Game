package fr.paris.saclay.sidescroller.controllers.components.menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class GameMenuUI {

    private String currentImage = "images/menu/book_1.png";

    private int currentAnimation = 1;

    public GameMenuUI() {
    }

    public void paint(Graphics graphics, GameMenu menu) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        if (!menu.getModel().isPauseMenu()) {
            menu.setOpaque(false);
            try {
                graphics2D.drawImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/menu/menu_background.png")),
                        0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, menu);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else menu.setBackground(Color.green);
        Image image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(currentImage));
            image = image.getScaledInstance(menu.getPreferredSize().width, menu.getPreferredSize().height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics.drawImage(image, (SCREEN_WIDTH - menu.getPreferredSize().width) / 2, (SCREEN_HEIGHT -
                menu.getPreferredSize().height) / 2, null);
        if (currentAnimation == 4 && !menu.isPauseMenu()) {
            drawOutlinedTitle(graphics, menu);
        }
    }

    private static void drawOutlinedTitle(Graphics graphics, GameMenu menu) {
        graphics.setColor(Color.decode(PRIMARY_COLOR));
        graphics.setFont(new Font("Monocraft", Font.PLAIN, 28));
        graphics.drawString("RPG Side Scroller", (SCREEN_WIDTH - menu.getPreferredSize().width / 2) / 2, (SCREEN_HEIGHT -
                menu.getPreferredSize().height) / 2 + 2);
        graphics.drawString("RPG Side Scroller", (SCREEN_WIDTH - menu.getPreferredSize().width / 2) / 2, (SCREEN_HEIGHT -
                menu.getPreferredSize().height) / 2 - 2);
        graphics.drawString("RPG Side Scroller", (SCREEN_WIDTH - menu.getPreferredSize().width / 2) / 2 + 2, (SCREEN_HEIGHT -
                menu.getPreferredSize().height) / 2);
        graphics.drawString("RPG Side Scroller", (SCREEN_WIDTH - menu.getPreferredSize().width / 2) / 2 - 2, (SCREEN_HEIGHT -
                menu.getPreferredSize().height) / 2);
        graphics.setColor(Color.decode(SECONDARY_COLOR));
        graphics.setFont(new Font("Monocraft", Font.PLAIN, 28));
        graphics.drawString("RPG Side Scroller", (SCREEN_WIDTH - menu.getPreferredSize().width / 2) / 2, (SCREEN_HEIGHT -
                menu.getPreferredSize().height) / 2);
    }

    public void updateImage() {
        currentAnimation++;
        this.currentImage = "images/menu/book_" + currentAnimation + ".png";
    }

    public int getCurrentAnimation() {
        return currentAnimation;
    }

    public void displayNormalMenu(GameMenuModel model) {
        model.getTimer().stop();
        model.getPlayButton().setVisible(true);
        model.getQuitButton().setVisible(true);
        model.getCredits().setVisible(true);
    }
}
