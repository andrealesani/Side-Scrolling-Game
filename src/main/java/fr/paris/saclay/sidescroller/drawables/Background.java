package fr.paris.saclay.sidescroller.drawables;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;
import static fr.paris.saclay.sidescroller.utils.Direction.*;

/**
 * Drawable responsible for creating the background.
 */
public class Background extends Drawable {
    /**
     * Background images displayed in the scene.
     */
    private final Image backgroundImage;
    /**
     * Numbers of backgrounds rendered.
     */
    private int numOfBackgrounds = 1;
    /**
     * Distance travelled by the player.
     */
    private int deltaX = 0;

    /**
     * Creates a Background instance, saves the GamePanel reference and sets the theme (background related to the level selected
     * in the initial menu).
     *
     * @param gamePanel reference.
     * @param theme     theme types: grass, mushroom, castle (each one related to a different level).
     */
    public Background(GamePanel gamePanel, String theme) {
        super(gamePanel);
        direction = RIGHT;
        speed = 0;
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/backgrounds/" + theme + "/background.png"));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find background image", e);
        }
    }

    /**
     * Handles Background movement. The player cannot exceed xPosition = 0 to the left and the half of the screen
     * when walking on the right. Since the player has different speed while jumping, the camera is updated differently.
     */
    @Override
    public void update() {
        speed = gamePanel.getPlayerSpeed();
        int initialPosition = xPosition;
        if ((gamePanel.rightPressed || gamePanel.upPressed || gamePanel.leftPressed) && !gamePanel.isPlayerAttacking()) {
            double multiplier = 1;
            if (gamePanel.getPlayer().isJumping())
                multiplier = 1.5;
            if (gamePanel.rightPressed) {
                direction = RIGHT;
                if (gamePanel.getPlayerPositionX() >= CAMERA_MIN_RIGHT) {
                    xPosition -= speed * multiplier;
                    gamePanel.notifyCameraMoved();
                }
            } else if (gamePanel.leftPressed) {
                direction = LEFT;
                if (gamePanel.getPlayerPositionX() <= CAMERA_MIN_LEFT && xPosition != 0) {
                    xPosition += speed * multiplier;
                    gamePanel.notifyCameraMoved();
                }
            } else {
                switch (direction) {
                    case LEFT -> direction = UP_LEFT;
                    case RIGHT -> direction = UP_RIGHT;
                }
            }
            if (xPosition > 0)
                xPosition = 0;
            deltaX = xPosition - initialPosition;
        }
    }

    /**
     * Renders the component into the scene.
     *
     * @param graphics2D the rendering environment.
     */
    @Override
    public void draw(Graphics2D graphics2D) {

        int pixelsOffset = -SCREEN_WIDTH * SCALE * (numOfBackgrounds - 1);
        int i;

        graphics2D.drawImage(backgroundImage, xPosition, yPosition, SCREEN_WIDTH * SCALE, SCREEN_HEIGHT - HEIGHT_TILE_SIZE, null);
        for (i = 0; i < numOfBackgrounds - 1; i++) {
            graphics2D.drawImage(backgroundImage, xPosition + SCREEN_WIDTH * SCALE * (i + 1), yPosition, SCREEN_WIDTH * SCALE, SCREEN_HEIGHT - HEIGHT_TILE_SIZE, null);
        }

        if (xPosition < pixelsOffset) {
            numOfBackgrounds++;
        }
    }


    /**
     * Gets delta x.
     *
     * @return the delta x
     */
    public int getDeltaX() {
        return deltaX;
    }
}
