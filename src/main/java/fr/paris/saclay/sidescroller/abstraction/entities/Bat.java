package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import java.util.Arrays;

import static fr.paris.saclay.sidescroller.abstraction.Direction.LEFT;
import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Bat extends Entity {
    /**
     * Used to determine the vertical movement. It is initialized randomly so that if there
     * are more than one bat in the scene their pattern won't be synchronous.
     */
    private int flyFrameCounter = (int) (Math.random() * 60);

    public Bat(GamePanel gamePanel, int xPosition) {
        super(gamePanel);

        this.xPosition = xPosition;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 2;
        lifePoints = 1;
        direction = LEFT;
        hitboxSize = WIDTH_TILE_SIZE / 2;

        setSprites(Arrays.asList(
                "images/enemies/bat/wings_1.png",
                "images/enemies/bat/wings_2.png"
        ));
    }

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
