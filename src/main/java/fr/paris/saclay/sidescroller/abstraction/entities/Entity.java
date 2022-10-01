package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.abstraction.Direction;
import fr.paris.saclay.sidescroller.abstraction.Drawable;
import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
    protected HashMap<Direction, List<BufferedImage>> animationMap = new HashMap<>();
    /**
     * Identifies the number of frames that have to be rendered before changing a sprite (during an animation).
     */
    protected int spriteCounter = 0;
    /**
     * Identifies the sprite that is currently being rendered
     */
    protected int spriteNumber = 1;
    /**
     * Is true when the entity is jumping, therefore blocking any other type of movement.
     */
    protected boolean isJumping = false;
    /**
     * Final image that is shown during the rendering of the entity.
     */
    protected BufferedImage image;
    /**
     * We decided to make every hitbox square.
     * Therefore, this number indicates both the height and the width of the hitbox square (in pixels).
     */
    protected int hitboxSize;
    /**
     * Represents the area of the entity that is used to compute collisions with enemies or projectiles.
     */
    protected Rectangle hitBox;

    public Entity(GamePanel gamePanel) {
        super(gamePanel);
        updateHitboxPosition(); // gamePanel is drawn before thread starting
    }

    /**
     * Updates the position of the entity's hitbox according to the entity's position inside the screen.
     */
    // TODO don't hardcode hitbox position
    protected void updateHitboxPosition() {
        hitBox = new Rectangle(xPosition + WIDTH_TILE_SIZE / 4, yPosition + HEIGHT_TILE_SIZE / 4, hitboxSize, hitboxSize);
    }

    /**
     * This method makes the entity chase the player on the X axis until its hitbox collides with the one of the player.
     */
    protected void chasePlayer() {
        if (gamePanel.getPlayerPositionX() > xPosition + hitboxSize / 2) {
            xPosition += speed;
            direction = RIGHT;
        } else if (gamePanel.getPlayerPositionX() < xPosition - hitboxSize / 2) {
            xPosition -= speed;
            direction = LEFT;
        }
    }

    protected void setSprites(List<String> paths) {
        List<BufferedImage> leftSprites = new ArrayList<>();
        try {
            for (String path : paths)
                leftSprites.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(path)));

            animationMap.put(LEFT, leftSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return how many life-points the entity currently has.
     */
    public int getLifePoints() {
        return lifePoints;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * Entities present a default version of the {@code updateSprites()} method. This method only supports two-sprite
     * animation for horizontal movement. Each sprite only lasts for 5 frames before changing to the next one.
     * Feel free to {@code Override} if you want to implement more fancy stuff.
     */
    protected void updateSprites() {
        spriteCounter++;

        if (spriteCounter > 9) {
            if (spriteNumber == 1)
                spriteNumber = 2;
            else
                spriteNumber = 1;
            spriteCounter = 0;
        }
    }

    protected void uniqueMovement() {
    }

    @Override
    public void update() {

        chasePlayer();

        uniqueMovement();

        updateSprites();

        updateHitboxPosition();
    }

    /**
     * Entities present a default version of the {@code draw()} method. This method only supports basic sprite
     * animations, namely when moving to the left and right directions (jump, up-left and up-right are not supported).
     * Feel free to {@code Override} if you want to implement more fancy stuff.
     *
     * @param graphics2D the rendering environment.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        AffineTransform transformX = AffineTransform.getScaleInstance(-1, 1);
        if (spriteNumber != 0) {
            switch (direction) {
                case LEFT, UP_LEFT -> {
                    image = animationMap.get(direction).get(spriteNumber - 1);
                }
                case RIGHT, UP_RIGHT -> {
                    transformX.translate(-animationMap.get(LEFT).get(spriteNumber - 1).getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(transformX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(animationMap.get(LEFT).get(spriteNumber - 1), null);
                }
            }
        }

        graphics2D.drawImage(image, xPosition, yPosition, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);

        graphics2D.setColor(new Color(0, 0, 255, 127));
        graphics2D.fill(hitBox);
    }
}
