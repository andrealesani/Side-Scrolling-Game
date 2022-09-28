package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.utils.InputHandler;
import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.abstraction.entities.Bat;
import fr.paris.saclay.sidescroller.abstraction.entities.Entity;
import fr.paris.saclay.sidescroller.abstraction.entities.Player;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    private boolean isRunning = true;

    public boolean upPressed, rightPressed, leftPressed;

    private final RPGSidescroller parentContainer;

    private Thread gameThread;
    private Player player;
    private List<Entity> entities = new ArrayList<>();;
    private List<Drawable> drawables = new ArrayList<>();;
    private Drawable background ;
    private Drawable terrain;
    private final CollisionDetector collisionDetector;
    private Direction cameraHasMoved;

    private Clip mediaPlayer;

    public GamePanel(RPGSidescroller parent) {
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        this.parentContainer = parent;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getClassLoader().getResource("fonts/Monocraft.otf").toURI())));
        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
        }

        collisionDetector = new CollisionDetector(this);

        setKeyBindings();

        setVisible(true);
    }

    private void setKeyBindings() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "walk_right");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "walk_right");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "walk_right_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "walk_right_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "walk_left");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "walk_left");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "walk_left_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "walk_left_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "jump");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "jump");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "menu");
        getActionMap().put("walk_right", walkRight());
        getActionMap().put("walk_right_released", walkRightReleased());
        getActionMap().put("walk_left", walkLeft());
        getActionMap().put("walk_left_released", walkLeftReleased());
        getActionMap().put("jump", jump());
        getActionMap().put("menu", showMenu());
    }

    public void startGame() {
        gameThread = new Thread(this);
        entities = new ArrayList<>();
        entities.add(new Bat(this));
        drawables = new ArrayList<>();
        background = new Background(this);
        drawables.add(background);
        terrain = new Terrain(this);
        drawables.add(terrain);
        player = new Player(this);
        drawables.add(player);
        drawables.addAll(entities);
        setFocusable(true);
        transferFocus();
        parentContainer.getGlassPane().setVisible(false);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        try {
            mediaPlayer = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream("soundtrack/megalovania.wav"));
            mediaPlayer.open(inputStream);
            mediaPlayer.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        while (gameThread != null) {
            if (isRunning) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;
                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }
                if (timer > 1000000000) {
                    timer = 0;
                }
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update() {

        for (var drawable : drawables)
            drawable.update();

        if (cameraHasMoved != null) {
            for (var entity : entities)
                entity.updatePositionToCamera();
            terrain.updatePositionToCamera();
        }

        /*if (isColliding())
            System.out.println("BONK!!");*/

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

    public int getPlayerSpeed() {
        return player.speed;
    }

    private Action walkRight() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!upPressed) {
                    rightPressed = true;
                }
            }
        };
    }

    private Action walkRightReleased() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                rightPressed = false;
            }
        };
    }

    private Action walkLeftReleased() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                leftPressed = false;
            }
        };
    }

    private Action walkLeft() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!upPressed) {
                    leftPressed = true;
                }
            }
        };
    }

    private Action jump() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!upPressed) {
                    upPressed = true;
                }
            }
        };
    }

    private Action showMenu() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                GameMenu menu = parentContainer.getGameMenu();
                menu.setPauseMenu(true);
                menu.setPausedGridConstraints();
                parentContainer.getGlassPane().setVisible(true);
                isRunning = false;
                mediaPlayer.stop();
            }
        };
    }


    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void play() {
        mediaPlayer.start();
    }

    public void stop() {
        gameThread.stop();
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
