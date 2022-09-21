package fr.paris.saclay.sidescroller.abstraction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public abstract class Entity extends Drawable {
    public HashMap<Direction, List<BufferedImage>> animationMap = new HashMap<>();
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public boolean isJumping = false;
    public BufferedImage image;
    public int hitboxSize;
    public Rectangle hitBox;

    public void updateHitbox() {
        hitBox = new Rectangle(xPosition + WIDTH_TILE_SIZE / 4, yPosition + HEIGHT_TILE_SIZE / 4, hitboxSize, hitboxSize);
    }

}
