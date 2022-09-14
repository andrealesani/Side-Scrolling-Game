package fr.paris.saclay.sidescroller.abstraction;

import java.awt.*;

public abstract class Drawable {

    public int xPosition;
    public int yPosition;
    public int speed;
    public Direction direction;

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);
}
