package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import java.util.Arrays;

import static fr.paris.saclay.sidescroller.abstraction.Direction.LEFT;
import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Ghost extends Entity {
    private int frameCounter;

    public Ghost(GamePanel gamePanel, int xPosition) {
        super(gamePanel);

        this.xPosition = xPosition;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 1;
        lifePoints = 1;
        direction = LEFT;
        hitboxSize = WIDTH_TILE_SIZE / 2 + 10;

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
            if (spriteNumber == 1)
                spriteNumber = 2;
            else
                spriteNumber = 1;
            spriteCounter = 0;
        }

        if (frameCounter >= 60 && frameCounter <= 120) {
            spriteNumber = 3;
        }
    }
}
