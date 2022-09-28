package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.utils.InputHandler;
import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.abstraction.entities.Bat;
import fr.paris.saclay.sidescroller.abstraction.entities.Entity;
import fr.paris.saclay.sidescroller.abstraction.entities.Player;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private final InputHandler inputHandler;
    private final Player player;
    private final List<Entity> entities;
    private final List<Drawable> drawables;
    private final Drawable background;
    private final Drawable terrain;
    private final CollisionDetector collisionDetector;
    private Direction cameraHasMoved;

    public GamePanel() {
        //setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);

        inputHandler = new InputHandler();
        addKeyListener(inputHandler);

        collisionDetector = new CollisionDetector(this);

        background = new Background(this, inputHandler);
        terrain = new Terrain(this, inputHandler);

        player = new Player(this, inputHandler);

        entities = new ArrayList<>();
        drawables = new ArrayList<>();

        entities.add(new Bat(this));

        drawables.add(background);
        drawables.add(terrain);
        drawables.add(player);
        drawables.addAll(entities);

        setVisible(true);
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                drawCount++;
                delta--;
            }
            if (timer > 1000000000) {
//                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        for (var drawable : drawables)
            drawable.update();

        if (cameraHasMoved != null) {
            for (var entity : entities)
                entity.updatePositionToCamera();
        }

        if (isColliding())
            System.out.println("BONK!!");

        cameraHasMoved = null;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Drawable drawable : drawables)
            drawable.draw(graphics2D);

        graphics2D.dispose();
    }

    public int getPlayerPositionX() {
        return player.xPosition;
    }

    public int getPlayerPositionY() {
        return player.yPosition;
    }

    public int getPlayerSpeed() {
        return player.speed;
    }

    public Direction getPlayerDirection() {
        return player.direction;
    }

    public boolean isColliding() {
        for (Entity entity : entities)
            if (!entity.equals(player) && collisionDetector.checkCollision(player, entity)) {
                return true;
            }

        return false;
    }

    public void notifyCameraMoved(Direction direction) {
        this.cameraHasMoved = direction;
    }
}
