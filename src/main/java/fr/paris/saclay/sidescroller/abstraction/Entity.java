package fr.paris.saclay.sidescroller.abstraction;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {
    public int xPosition;
    public int yPosition;
    public int speed;

    public HashMap<Direction, List<BufferedImage>> animationMap = new HashMap<>();
    public Direction direction;
    public int spriteCounter = 0;
    public int spriteNumber= 1;

    public boolean isJumping = false;

    BufferedImage image;
}
