package fr.paris.saclay.sidescroller.abstraction.entities;

import fr.paris.saclay.sidescroller.abstraction.Direction;
import fr.paris.saclay.sidescroller.controller.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Arrays;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Player extends Entity {

    int maximumStamina = PLAYER_MAX_STAMINA;

    int staminaTimer = PLAYER_STAMINA_TIMER;

    boolean isRecovering = false;

    int currentStamina;

    public Player(GamePanel gamePanel) {
        super(gamePanel);
        currentStamina = PLAYER_MAX_STAMINA;
        hitboxSize = WIDTH_TILE_SIZE / 2;
        attackHitboxSize = new Dimension(WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE);
        lifePoints = PLAYER_MAX_HP;
        isInvincible = false;
        invincibilityTimer = 0;
        maximumInvincibility = PLAYER_INVINCIBILITY_TIME;
        maximumLifePoints = PLAYER_MAX_HP;
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
        setAttackSprites(Arrays.asList(
                "images/player/avatars/pink/alien_left_attack_1.png",
                "images/player/avatars/pink/alien_left_attack_2.png"
        ));
    }

    private void setPlayerDefaultPosition() {
        xPosition = 100;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 6;
        spriteNumber = 0;
        direction = Direction.RIGHT;
    }

    /**
     * Sets the player invincible for the specified amount of frames.
     *
     * @param invincibilityTimer the number of frames during which the player is invincible.
     */

    @Override
    public void update() {
        if ((gamePanel.rightPressed || gamePanel.upPressed || gamePanel.leftPressed) && !isAttacking) {
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
                if (spriteNumber == animationMap.get(Direction.LEFT).size()-1 && !isJumping) {
                    spriteNumber = 0;
                    spriteCounter = 0;
                } else if (spriteNumber == animationMap.get(Direction.UP_LEFT).size()-1 && isJumping) {
                    spriteNumber = 0;
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
        else if (isAttacking){
            spriteCounter++;
            if(spriteCounter>10){
                if (spriteNumber == animationMap.get(Direction.ATTACK_LEFT).size()-1){
                    spriteNumber = 0;
                    if (direction == Direction.ATTACK_LEFT) {
                        direction = Direction.LEFT;
                    } else direction = Direction.RIGHT;
                    isAttacking = false;
                }else {
                    spriteNumber++;
                }
                spriteCounter = 0;
            }
        }

        updateHitboxPosition();
        if (invincibilityTimer == 0)
            isInvincible = false;
        else
            invincibilityTimer--;
        if (this.lifePoints == 0) {
            gamePanel.setGameOver();
        }
        System.out.println(isRecovering);
        System.out.println(staminaTimer);
        if(!isRecovering && currentStamina<PLAYER_MAX_STAMINA){
            if (staminaTimer == 0){
                isRecovering = true;
            }
            else
                staminaTimer--;
        }
        if(isRecovering){
            if(isAttacking){
                isRecovering = false;
                staminaTimer = PLAYER_STAMINA_TIMER;
            }
            currentStamina++;
            if(currentStamina == PLAYER_MAX_STAMINA){
                isRecovering = false;
            }
        }


        if (this.lifePoints == 0) {
            gamePanel.setGameOver();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        AffineTransform transformX = AffineTransform.getScaleInstance(-1, 1);
            switch (direction) {
                case LEFT, UP_LEFT, ATTACK_LEFT -> {
                    if (direction == Direction.UP_LEFT && spriteNumber == 6) {
                        isJumping = false;
                    }
                    image = animationMap.get(direction).get(spriteNumber);
                }
                case RIGHT, UP_RIGHT, ATTACK_RIGHT -> {
                    if (direction == Direction.UP_RIGHT && spriteNumber == 6) {
                        isJumping = false;
                    }
                    Direction utilDirection = direction == Direction.RIGHT ? Direction.LEFT :
                            ((direction==Direction.UP_RIGHT) ? Direction.UP_LEFT : Direction.ATTACK_LEFT);
                    transformX.translate(-animationMap.get(utilDirection).get(spriteNumber).getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(transformX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(animationMap.get(utilDirection).get(spriteNumber), null);
                }
            }
            if (isAttacking){
                int directionFactor = 0;
                if(direction == Direction.ATTACK_LEFT){
                    directionFactor = 1;
                }
                graphics2D.drawImage(image, xPosition - (WIDTH_TILE_SIZE)*directionFactor, yPosition, 2*WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
            }
            else graphics2D.drawImage(image, xPosition, yPosition, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
            if (isInvincible())
                graphics2D.setColor(new Color(255, 0, 0, 127));
            else
                graphics2D.setColor(new Color(0, 0, 255, 127));
            graphics2D.fill(hitBox);
            graphics2D.setColor(new Color(0, 255, 0, 127));
            if (isAttacking)
                graphics2D.fill(attackHitBox);
            drawStaminaBar(graphics2D);
    }

    public void attack(){
        if (!gamePanel.upPressed && !isAttacking() && (currentStamina>0 || !isRecovering) ) {
            setAttacking(true);
            currentStamina -= 25;
            staminaTimer = PLAYER_STAMINA_TIMER;
            if(currentStamina<0){
                currentStamina=0;
            }
            spriteNumber = 0;
            direction = direction == Direction.LEFT ? Direction.ATTACK_LEFT : Direction.ATTACK_RIGHT;
        }
    }

    private void drawStaminaBar(Graphics2D graphics2D){
        int maximumStaminaBarWidth = WIDTH_TILE_SIZE*4;
        int lostStamina = maximumStamina - currentStamina;
        int currentX = 30;
        int currentY = 60;
        graphics2D.setColor(new Color(0,255,0,255));
        for (int i = 0; i < currentStamina; i++) {
            graphics2D.fillRect(currentX, currentY, maximumStaminaBarWidth/maximumStamina, 10);
            currentX = currentX + maximumStaminaBarWidth/maximumStamina;
        }
        if(lostStamina>0){
            graphics2D.setColor(Color.gray);
            for (int i = 0; i < lostStamina; i++) {
                graphics2D.fillRect(currentX, currentY, maximumStaminaBarWidth/maximumStamina, 10);
                currentX = currentX + maximumStaminaBarWidth/maximumStamina;
            }
        }
    }
}
