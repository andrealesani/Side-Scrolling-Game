package fr.paris.saclay.sidescroller.controllers.components.menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

/**
 * View class of GameMenu class.
 */
public class GameMenuUI {

    /**
     * Menu container image, it changes for each animation sprite.
     */
    private String currentImage = "images/menu/book_1.png";
    /**
     * Current animation index.
     */
    private int currentAnimation = 1;

    /**
     * Draws GameMenu: menu container book and title.
     *
     * @param graphics the rending environment.
     * @param menu     controller reference.
     */
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

    /**
     * Draws title with an outlined stroke.
     *
     * @param graphics the rending environment.
     * @param menu     controller reference.
     */
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

    /**
     * Updates the menu background during animation.
     */
    public void updateImage() {
        currentAnimation++;
        this.currentImage = "images/menu/book_" + currentAnimation + ".png";
    }

    /**
     * Gets current animation.
     *
     * @return the current animation
     */
    public int getCurrentAnimation() {
        return currentAnimation;
    }

    /**
     * Display initial menu after animation concludes.
     *
     * @param model reference.
     */
    public void displayNormalMenu(GameMenuModel model) {
        model.getTimer().stop();
        model.getPlayButton().setVisible(true);
        model.getQuitButton().setVisible(true);
        model.getCredits().setVisible(true);
    }
}
