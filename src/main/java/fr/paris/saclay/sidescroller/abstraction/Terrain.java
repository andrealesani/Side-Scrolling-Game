package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.GamePanel;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Terrain extends Drawable {
    Image terrain;
    int numOfTiles;
    GamePanel gamePanel;

    public Terrain(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        direction = Direction.RIGHT;
        speed = 0;
        numOfTiles = SCREEN_WIDTH / WIDTH_TILE_SIZE;
        try {
            terrain = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/grass.png"));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find background image", e);
        }
    }

    @Override
    public void update() {
        speed = gamePanel.getPlayerSpeed();
        if ((gamePanel.rightPressed || gamePanel.upPressed || gamePanel.leftPressed)) {
            if (gamePanel.rightPressed) {
                direction = Direction.RIGHT;
                if (gamePanel.getPlayerPositionX() >= SCREEN_WIDTH / 2 - WIDTH_TILE_SIZE / 2) {
                    xPosition -= speed;
                }
            } else if (gamePanel.leftPressed) {
                direction = Direction.LEFT;
                if (gamePanel.getPlayerPositionX() <= 5) {
                    xPosition += speed;
                }
            } else {
                switch (direction) {
                    case LEFT -> {
                        direction = Direction.UP_LEFT;
                    }
                    case RIGHT -> {
                        direction = Direction.UP_RIGHT;
                    }
                    case UP_LEFT -> {
                        xPosition += speed / 2;
                    }
                    case UP_RIGHT -> {
                        xPosition -= speed / 2;
                    }
                }
            }
            if (xPosition > 0)
                xPosition = 0;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        //System.out.println(numOfTiles);
        int pixelsOffset = -WIDTH_TILE_SIZE * (numOfTiles - SCREEN_WIDTH / WIDTH_TILE_SIZE - 2);
        int i;

        for (i = 0; i < numOfTiles; i++) {
            graphics2D.drawImage(terrain, xPosition + i * WIDTH_TILE_SIZE, SCREEN_HEIGHT - HEIGHT_TILE_SIZE, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
        }

        if (xPosition < pixelsOffset) {
            numOfTiles++;
        }
    }
}
