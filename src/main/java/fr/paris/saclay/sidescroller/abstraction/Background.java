package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.abstraction.Direction.*;
import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Background extends Drawable {

    Image backgroundImage;
    int numOfBackgrounds = 1;

    public Background(GamePanel gamePanel) {
        super(gamePanel);
        direction = RIGHT;
        speed = 0;
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/backgrounds/grasslands.png"));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find background image", e);
        }
    }

    @Override
    public void update() {
        speed = gamePanel.getPlayerSpeed();
        if ((gamePanel.rightPressed || gamePanel.upPressed || gamePanel.leftPressed) && !gamePanel.isPlayerAttacking()) {
            if (gamePanel.rightPressed) {
                direction = RIGHT;
                if (gamePanel.getPlayerPositionX() >= CAMERA_MIN_RIGHT) {
                    xPosition -= speed;
                    gamePanel.notifyCameraMoved();
                }
            } else if (gamePanel.leftPressed) {
                direction = LEFT;
                if (gamePanel.getPlayerPositionX() <= CAMERA_MIN_LEFT && xPosition != 0) {
                    xPosition += speed;
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
        }
    }

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
}
