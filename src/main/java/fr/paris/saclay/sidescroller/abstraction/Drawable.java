package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.GamePanel;

import java.awt.*;

public abstract class Drawable {
    protected final GamePanel gamePanel;
    public int xPosition;
    public int yPosition;
    public int speed;
    public Direction direction;

    public Drawable(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // TODO don't hardcode jumping speed
    public void updatePositionToCamera() {
        switch (gamePanel.getPlayerDirection()) {
            case LEFT -> xPosition += gamePanel.getPlayerSpeed();
            case UP_LEFT -> xPosition += gamePanel.getPlayerSpeed() / 2;
            case RIGHT -> xPosition -= gamePanel.getPlayerSpeed();
            case UP_RIGHT -> xPosition -= gamePanel.getPlayerSpeed() / 2;
        }
    }

    /**
     * This method is called by the GamePanel every time a new frame has to be generated.
     * Each Drawable object is responsible for calculating its position, state and aspect
     * (sprite animations, change) for every new frame.
     */
    public abstract void update();

    /**
     * Each Drawable object must implement a method to be rendered inside the JFrame.
     *
     * @param graphics2D the object in charge of rendering shapes inside the JFrame.
     */
    public abstract void draw(Graphics2D graphics2D);
}
