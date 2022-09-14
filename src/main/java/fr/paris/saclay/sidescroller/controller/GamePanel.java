package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    public final int ORIGINAL_SQUARE_TILE = 16;
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROWS = 12;
    public final int SCALE = 4;
    //TODO SCALING BASED ON DEVICE
    public final int WIDTH_TILE_SIZE = ORIGINAL_SQUARE_TILE * SCALE;
    public final int HEIGHT_TILE_SIZE = ORIGINAL_SQUARE_TILE * SCALE;

    public final int SCREEN_WIDTH = WIDTH_TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = HEIGHT_TILE_SIZE * MAX_SCREEN_ROWS;
    private Thread gameThread;
    private InputHandler inputHandler;
    private final List<Drawable> drawables;
    private final Drawable player;
    private final Drawable background;
    private final Drawable terrain;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);

        inputHandler = new InputHandler();
        addKeyListener(inputHandler);

        drawables = new ArrayList<>();
        background = new Background(this, inputHandler);
        drawables.add(background);
        player = new Player(this, inputHandler);
        drawables.add(player);
        terrain = new Terrain(this, inputHandler);
        drawables.add(terrain);
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
//        System.out.println(player.direction);
        for (Drawable drawable : drawables)
            drawable.update();
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
}
