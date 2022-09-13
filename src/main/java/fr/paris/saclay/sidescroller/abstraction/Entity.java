package fr.paris.saclay.sidescroller.abstraction;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int xPosition;
    public int yPosition;
    public int speed;
    public BufferedImage upSpritePrimary, upSpriteSecondary, leftSpritePrimary,
            leftSpriteSecondary, rightSpritePrimary, rightSpriteSecondary;
    public Direction direction = null;
    public int spriteCounter = 0;
    public int spriteNumber= 1;
}
