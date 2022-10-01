package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.abstraction.Direction;
import fr.paris.saclay.sidescroller.controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Player extends Entity {
    private boolean invincible;
    private int invincibilityTimer;

    public Player(GamePanel gamePanel) {
        super(gamePanel);
        hitboxSize = WIDTH_TILE_SIZE / 2;
        lifePoints = PLAYER_MAX_HP;
        invincible = false;
        invincibilityTimer = 0;
        setPlayerDefaultPosition();
        setSprites(Arrays.asList(
                "images/player/avatars/pink/alien_left.png",
                "images/player/avatars/pink/alien_left_2.png"
        ));
        setJumpSprites(Arrays.asList(
                "images/player/avatars/pink/alien_left_jump_2.png",
                "images/player/avatars/pink/alien_left_jump_3.png",
                "images/player/avatars/pink/alien_left_jump_4.png",
                "images/player/avatars/pink/alien_left_jump_5.png",
                "images/player/avatars/pink/alien_left_jump_2.png",
                "images/player/avatars/pink/alien_left_jump.png"
        ));
    }

    private void setPlayerDefaultPosition() {
        xPosition = 100;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 6;
        direction = Direction.RIGHT;
    }

    private void setJumpSprites(List<String> paths) {
        List<BufferedImage> upLeftSprites = new ArrayList<>();
        try {
            for (String path : paths)
                upLeftSprites.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(path)));

            animationMap.put(Direction.UP_LEFT, upLeftSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player invincible for the specified amount of frames.
     *
     * @param invincibilityTimer the number of frames during which the player is invincible.
     */
    public void setInvincible(int invincibilityTimer) {
        this.invincible = true;
        this.invincibilityTimer = invincibilityTimer;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void tookDamage(int lifePoints) {
        if (!isInvincible())
            this.lifePoints -= lifePoints;

        if (this.lifePoints == 0) {
            gamePanel.setGameOver();
        }
    }

    @Override
    public void update() {
        if ((gamePanel.rightPressed || gamePanel.upPressed || gamePanel.leftPressed)) {
            if (gamePanel.rightPressed) {
                direction = Direction.RIGHT;
                if (xPosition <= SCREEN_WIDTH / 2 - WIDTH_TILE_SIZE / 2) {
                    xPosition += speed;
                }
            } else if (gamePanel.leftPressed) {
                direction = Direction.LEFT;
                if (xPosition >= 5) {
                    xPosition -= speed;
                }
            } else {
                isJumping = true;
                switch (direction) {
                    case LEFT, RIGHT -> {
                        direction = direction == Direction.LEFT ? Direction.UP_LEFT : Direction.UP_RIGHT;
                        spriteNumber = 0;
                    }
                    case UP_LEFT, UP_RIGHT -> {
                        if (spriteNumber == 1 || spriteNumber == 2) {
                            yPosition -= speed * 1.5;
                        } else if (spriteNumber == 4 || spriteNumber == 5) {
                            yPosition += speed * 1.5;
                        }
                    }
                }
            }
            if (xPosition < 0)
                xPosition = 0;

            spriteCounter++;
            if (spriteCounter > 5) {
                if (spriteNumber == 2 && !isJumping) {
                    spriteNumber = 1;
                    spriteCounter = 0;
                } else if (spriteNumber == 6 && isJumping) {
                    spriteNumber = 1;
                    isJumping = false;
                    gamePanel.upPressed = false;
                    spriteCounter = 10;
                    if (direction == Direction.UP_LEFT) {
                        direction = Direction.LEFT;
                    } else direction = Direction.RIGHT;
                } else {
                    spriteNumber++;
                    spriteCounter = 0;
                }
            }
        }

        if (invincibilityTimer == 0)
            invincible = false;
        else
            invincibilityTimer--;

        updateHitboxPosition();
    }

    public void draw(Graphics2D graphics2D) {
//        System.out.println(spriteNumber);
        AffineTransform transformX = AffineTransform.getScaleInstance(-1, 1);
        if (spriteNumber != 0) {
            switch (direction) {
                case LEFT, UP_LEFT -> {
                    if (direction == Direction.UP_LEFT && spriteNumber == 6) {
                        isJumping = false;
                    }
                    image = animationMap.get(direction).get(spriteNumber - 1);
                }
                case RIGHT, UP_RIGHT -> {
                    if (direction == Direction.UP_RIGHT && spriteNumber == 6) {
                        isJumping = false;
                    }
                    Direction utilDirection = direction == Direction.RIGHT ? Direction.LEFT : Direction.UP_LEFT;
                    transformX.translate(-animationMap.get(utilDirection).get(spriteNumber - 1).getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(transformX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(animationMap.get(utilDirection).get(spriteNumber - 1), null);
                }
            }
        }

        graphics2D.drawImage(image, xPosition, yPosition, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
        if (isInvincible())
            graphics2D.setColor(new Color(255, 0, 0, 127));
        else
            graphics2D.setColor(new Color(0, 0, 255, 127));
        graphics2D.fill(hitBox);
    }
}
