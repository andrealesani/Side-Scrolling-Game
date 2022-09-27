package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    public boolean upPressed, rightPressed, leftPressed;


    private GraphicsEnvironment environment;
    private Thread gameThread;
    private final List<Drawable> drawables;
    private final Drawable player;
    private final Drawable background;
    private final Drawable terrain;

    public GamePanel() {
        //setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
                    getClass().getClassLoader().getResource("fonts/Monocraft.otf").toURI())));
        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
        }
        // TODO DELETE INPUTHANDLER
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "walk_right");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,false), "walk_right");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "walk_right_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,true), "walk_right_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,false), "walk_left");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,false), "walk_left");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,true), "walk_left_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,true), "walk_left_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,false), "jump");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,false), "jump");
        getActionMap().put("walk_right", walkRight());
        getActionMap().put("walk_right_released", walkRightReleased());
        getActionMap().put("walk_left", walkLeft());
        getActionMap().put("walk_left_released", walkLeftReleased());
        getActionMap().put("jump", jump());
        drawables = new ArrayList<>();
        background = new Background(this);
        drawables.add(background);
        player = new Player(this);
        drawables.add(player);
        terrain = new Terrain(this);
        drawables.add(terrain);

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

    private Action walkRight(){
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(!upPressed){
                    rightPressed = true;
                }
            }
        };
    }

    private Action walkRightReleased(){
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                    rightPressed = false;
            }
        };
    }

    private Action walkLeftReleased(){
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                leftPressed = false;
            }
        };
    }

    private Action walkLeft(){
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(!upPressed){
                    leftPressed = true;
                }
            }
        };
    }

    private Action jump(){
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(!upPressed){
                    upPressed = true;
                }
            }
        };
    }
}
