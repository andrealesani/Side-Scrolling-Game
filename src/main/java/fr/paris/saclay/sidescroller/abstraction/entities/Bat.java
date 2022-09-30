package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

        setBatImage();
    }

    public void setBatImage() {
        try {
            java.util.List<BufferedImage> leftSprites = Arrays.asList(
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/enemies/bat/wings_1.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/enemies/bat/wings_2.png"))
            );
            animationMap.put(LEFT, leftSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        // PATHFINDING ALGORITHM
        chasePlayer();

        // VERTICAL MOVEMENT
        if (flyFrameCounter < 60) {
            yPosition--;
            flyFrameCounter++;
        }
        else if (flyFrameCounter < 120 && yPosition < SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2) {
            yPosition++;
            flyFrameCounter++;
        } else
            flyFrameCounter = 0;

        // SPRITE ANIMATION
        spriteCounter++;

        if (spriteCounter > 9) {
            if (spriteNumber == 1)
                spriteNumber = 2;
            else
                spriteNumber = 1;
            spriteCounter = 0;
        }

        updateHitboxPosition();
    }

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
