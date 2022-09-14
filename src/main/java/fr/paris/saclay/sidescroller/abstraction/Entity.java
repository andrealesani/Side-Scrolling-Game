package fr.paris.saclay.sidescroller.abstraction;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public abstract class Entity extends Drawable {
    public HashMap<Direction, List<BufferedImage>> animationMap = new HashMap<>();
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public boolean isJumping = false;
    BufferedImage image;
}
