package fr.paris.saclay.sidescroller.drawables.entities.enemies;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.drawables.entities.Entity;

import java.awt.*;
import java.util.Arrays;

import static fr.paris.saclay.sidescroller.utils.Constants.*;
import static fr.paris.saclay.sidescroller.utils.Direction.LEFT;

/**
 * Bat enemy.
 */
public class Bat extends Entity {
    /**
     * Used to determine the vertical movement. It is initialized randomly so that if there
     * are more than one bat in the scene their pattern won't be synchronous.
     */
    private int flyFrameCounter = (int) (Math.random() * 60);

    /**
     * Creates a Bat instance passing the GamePanel reference and initial position of the entity.
     *
     * @param gamePanel reference.
     * @param xPosition initial position.
     */
    public Bat(GamePanel gamePanel, int xPosition) {
        super(gamePanel);
        this.xPosition = xPosition;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 2;
        lifePoints = 2;
        maximumLifePoints = 2;
        invincibilityTimer = 0;
        maximumInvincibility = 30;
        direction = LEFT;
        hitboxSize = WIDTH_TILE_SIZE * 3 / 4;
        attackHitboxSize = new Dimension(WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE);
        blockHitboxSize = new Dimension(WIDTH_TILE_SIZE / 2 + WIDTH_TILE_SIZE / 3, HEIGHT_TILE_SIZE);
        setSprites(Arrays.asList(
                "images/enemies/bat/wings_1.png",
                "images/enemies/bat/wings_2.png"
        ));
    }

    /**
     * Bats fy up and down while moving forward.
     */
    @Override
    protected void uniqueMovement() {
        // VERTICAL MOVEMENT
        if (flyFrameCounter < 60) {
            yPosition--;
            flyFrameCounter++;
        } else if (flyFrameCounter < 120 && yPosition < SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2) {
            yPosition++;
            flyFrameCounter++;
        } else
            flyFrameCounter = 0;
    }
}
