package fr.paris.saclay.sidescroller.drawables;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.utils.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

/**
 * Drawable responsible for creating the terrain.
 */
public class Terrain extends Drawable {
    /**
     * Tile image used for terrain generation.
     */
    private final Image terrain;
    /**
     * Number of tiles.
     */
    private int numOfTiles;

    /**
     * Creates a Terrain instance, saves the GamePanel reference and sets the theme (tile related to the level selected
     * in the initial menu).
     *
     * @param gamePanel reference.
     * @param theme     theme types: grass, mushroom, castle (each one related to a different level).
     */
    public Terrain(GamePanel gamePanel, String theme) {
        super(gamePanel);
        direction = Direction.RIGHT;
        speed = 0;
        numOfTiles = SCREEN_WIDTH / WIDTH_TILE_SIZE;
        try {
            terrain = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/backgrounds/" + theme + "/tile.png"));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find background image", e);
        }
    }

    @Override
    public void update() {
    }

    /**
     * Draws the component.
     *
     * @param graphics2D the rendering environment.
     */
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
