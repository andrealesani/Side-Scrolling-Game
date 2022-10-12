package fr.paris.saclay.sidescroller.drawables;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.utils.Direction;

import java.awt.*;

/**
 * Basic template for each object drawn on the screen excluding player UI.
 */
public abstract class Drawable {
    /**
     * GamePanel reference.
     */
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

    /**
     * Creates a Drawable instance and saves GamePanel reference.
     *
     * @param gamePanel reference.
     */
    public Drawable(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Updates camera position by setting it equal to the distance travelled by the player.
     *
     * @param deltaX distance travelled.
     */
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

    public int getXPosition() {
        return xPosition;
    }
}
