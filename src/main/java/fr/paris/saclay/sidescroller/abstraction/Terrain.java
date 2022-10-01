package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Terrain extends Drawable {
    Image terrain;
    int numOfTiles;

    public Terrain(GamePanel gamePanel) {
        super(gamePanel);
        direction = Direction.RIGHT;
        speed = 0;
        numOfTiles = SCREEN_WIDTH / WIDTH_TILE_SIZE;
        try {
            terrain = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/backgrounds/grass.png"));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find background image", e);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
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
