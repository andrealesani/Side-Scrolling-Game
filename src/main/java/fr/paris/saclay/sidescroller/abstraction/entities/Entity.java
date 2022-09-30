package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.abstraction.Direction;
import fr.paris.saclay.sidescroller.abstraction.Drawable;
import fr.paris.saclay.sidescroller.controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import static fr.paris.saclay.sidescroller.abstraction.Direction.*;
import static fr.paris.saclay.sidescroller.utils.Constants.*;

public abstract class Entity extends Drawable {
    /**
     * The number of the life points that the entity currently has.
     */
    protected int lifePoints;
    /**
     * Links the Direction the entity is facing to the list of images that represent the entity in that direction.
     */
    public HashMap<Direction, List<BufferedImage>> animationMap = new HashMap<>();
    /**
     * Identifies the number of frames that have to be rendered before changing a sprite (during an animation).
     */
    public int spriteCounter = 0;
    /**
     * Identifies the sprite that is currently being rendered
     */
    public int spriteNumber = 1;
    /**
     * Is true when the entity is jumping, therefore blocking any other type of movement.
     */
    public boolean isJumping = false;
    /**
     * Final image that is shown during the rendering of the entity.
     */
    public BufferedImage image;
    /**
     * We decided to make every hitbox square.
     * Therefore, this number indicates both the height and the width of the hitbox square (in pixels).
     */
    public int hitboxSize;
    /**
     * Represents the area of the entity that is used to compute collisions with enemies or projectiles.
     */
    public Rectangle hitBox;

    public Entity(GamePanel gamePanel) {
        super(gamePanel);
        updateHitboxPosition(); // gamePanel is drawn before thread starting
    }

    /**
     * Updates the position of the entity's hitbox according to the entity's position inside the screen.
     */
    // TODO don't hardcode hitbox position
    public void updateHitboxPosition() {
        hitBox = new Rectangle(xPosition + WIDTH_TILE_SIZE / 4, yPosition + HEIGHT_TILE_SIZE / 4, hitboxSize, hitboxSize);
    }

    /**
     * This method makes the entity chase the player on the X axis until its hitbox collides with the one of the player.
     */
    public void chasePlayer() {
        if (gamePanel.getPlayerPositionX() > xPosition + hitboxSize - 1) {
            xPosition += speed;
            direction = RIGHT;
        } else if (gamePanel.getPlayerPositionX() < xPosition - hitboxSize - 1) {
            xPosition -= speed;
            direction = LEFT;
        }
    }

    public int getLifePoints() {
        return lifePoints;
    }
}
