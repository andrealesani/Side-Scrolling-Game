package fr.paris.saclay.sidescroller.abstraction;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int xPosition;
    public int yPosition;
    public int speed;
    public BufferedImage upLeftSprite, upLeftSprite2, upLeftSprite3, upLeftSprite4, upLeftSprite5, upLeftSprite6, upRightSprite, upRightSprite2, upRightSprite3, upRightSprite4, upRightSprite5, upRightSprite6, leftSprite,
            leftSprite2, rightSprite, rightSprite2;
    public Direction direction;
    public int spriteCounter = 0;
    public int spriteNumber= 1;

    public boolean isJumping = false;
}
