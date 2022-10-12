package fr.paris.saclay.sidescroller.drawables;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.utils.Direction;

import java.awt.*;

public abstract class Drawable {
    protected final GamePanel gamePanel;
    /**
     * Drawable's position on the X axis (relative to window)
     */
    public int xPosition;
    /**
     * Drawable's position on the Y axis (relative to window)
     */
    public int yPosition;
    /**
     * Drawable's movement speed
     */
    public int speed;
    /**
     * Drawable's facing direction
     */
    public Direction direction;

    public Drawable(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updatePositionToCamera(int deltaX) {
        xPosition += deltaX;
    }

    /**
     * This method is called by the GamePanel every time a new frame has to be generated.
     * Each Drawable object is responsible for calculating its position, state and aspect
     * (such as sprite animations) for every new frame.
     */
    public abstract void update();

    /**
     * Each Drawable object must implement a method to be rendered inside the JFrame.
     *
     * @param graphics2D the rendering environment.
     */
    public abstract void draw(Graphics2D graphics2D);

    public int getxPosition() {
        return xPosition;
    }
}
