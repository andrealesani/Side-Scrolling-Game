package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.abstraction.Direction;
import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Bat extends Entity {

    public Bat(GamePanel gamePanel) {
        super(gamePanel);

        xPosition = 400;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 0;
        direction = Direction.LEFT;
        hitboxSize = WIDTH_TILE_SIZE / 2;

        setBatImage();
    }

    public void setBatImage() {
        try {
            java.util.List<BufferedImage> leftSprites = Arrays.asList(
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/enemies/bat/wings_1.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/enemies/bat/wings_2.png"))
            );
            animationMap.put(Direction.LEFT, leftSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
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
                    // TODO
                    /*Direction utilDirection = direction == Direction.RIGHT ? Direction.LEFT : Direction.UP_LEFT;
                    transformX.translate(-animationMap.get(utilDirection).get(spriteNumber - 1).getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(transformX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(animationMap.get(utilDirection).get(spriteNumber - 1), null);*/
                }
            }
        }

        graphics2D.drawImage(image, xPosition, yPosition, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
        graphics2D.fill(hitBox);
    }
}
