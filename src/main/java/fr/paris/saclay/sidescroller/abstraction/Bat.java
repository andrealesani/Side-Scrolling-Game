package fr.paris.saclay.sidescroller.abstraction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class Bat extends Entity{
    public Bat() {
        xPosition = 200;
        yPosition = SCREEN_HEIGHT - HEIGHT_TILE_SIZE * 2;
        speed = 0;
        direction = Direction.LEFT;
        hitboxSize = WIDTH_TILE_SIZE / 2;
    }

    @Override
    public void update() {
        updateHitbox();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/pink_alien_left_jump_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        graphics2D.drawImage(image, xPosition, yPosition, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
        graphics2D.fill(hitBox);
    }
}
