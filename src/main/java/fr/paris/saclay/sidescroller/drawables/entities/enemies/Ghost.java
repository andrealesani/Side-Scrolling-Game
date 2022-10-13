package fr.paris.saclay.sidescroller.drawables.entities.enemies;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.drawables.entities.Entity;

import java.awt.*;
import java.util.Arrays;

import static fr.paris.saclay.sidescroller.utils.Constants.*;
import static fr.paris.saclay.sidescroller.utils.Direction.LEFT;

/**
 * Ghost enemy.
 */
public class Ghost extends Entity {
    /**
     * Used to determine the horizontal movement
     */
    private int frameCounter;

    /**
     * Creates a Ghost instance passing the GamePanel reference and initial position of the entity.
     *
     * @param gamePanel reference.
     * @param xPosition initial position.
     */
    public Ghost(GamePanel gamePanel, int xPosition) {
        super(gamePanel);
        this.xPosition = xPosition;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 1;
        lifePoints = 4;
        invincibilityTimer = 0;
        maximumInvincibility = 30;
        direction = LEFT;
        hitboxSize = WIDTH_TILE_SIZE;
        maximumLifePoints = 4;
        attackHitboxSize = new Dimension(WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE);
        blockHitboxSize = new Dimension(WIDTH_TILE_SIZE / 2 + WIDTH_TILE_SIZE / 3, HEIGHT_TILE_SIZE);
        setSprites(Arrays.asList(
                "images/enemies/ghost/boo.png",
                "images/enemies/ghost/meh.png",
                "images/enemies/ghost/invisible.png")
        );
    }

    /**
     * Ghosts have a slight vertical movement so that it looks like they are "floating".
     */
    @Override
    protected void uniqueMovement() {
        // VERTICAL MOVEMENT
        if (frameCounter < 60) {
            if (frameCounter % 12 == 0)
                yPosition--;
            frameCounter++;
        } else if (frameCounter < 120 && yPosition < SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2) {
            if (frameCounter % 12 == 0)
                yPosition++;
            frameCounter++;
        } else
            frameCounter = 0;
    }

    /**
     * Activates "invisible" sprite every other 60 frames. <br>
     * NOTE: this is just a visual feature, hitbox and damage are still active.
     */
    @Override
    protected void updateSprites() {
        spriteCounter++;

        if (spriteCounter > 9) {
            if (spriteNumber >= animationMap.get(LEFT).size() - 2)
                spriteNumber = 0;
            else spriteNumber++;
            spriteCounter = 0;
        }
        if (frameCounter >= 60 && frameCounter <= 120) {
            if (frameCounter == 60)
                setEntityInvincible(60);
            spriteNumber = 2;
        }
    }
}
