package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.*;
import fr.paris.saclay.sidescroller.abstraction.entities.Bat;
import fr.paris.saclay.sidescroller.abstraction.entities.Entity;
import fr.paris.saclay.sidescroller.abstraction.entities.Ghost;
import fr.paris.saclay.sidescroller.abstraction.entities.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class GamePanel extends JPanel implements Runnable {

    private boolean isRunning = true;

    public boolean upPressed, rightPressed, leftPressed;

    private final RPGSidescroller parentContainer;

    private Thread gameThread;
    private Player player;
    private List<Entity> entities = new ArrayList<>();
    ;
    private List<Drawable> drawables = new ArrayList<>();
    ;
    private Drawable background;
    private Drawable terrain;
    private final CollisionDetector collisionDetector;
    private boolean cameraHasMoved;
    private boolean gameOver = false;

    BufferedImage fullHeartImage, halfHeartImage, emptyHeartImage;
    private MusicPlayer mediaPlayer;

    public GamePanel(RPGSidescroller parent) {
        mediaPlayer = parent.getMusicPlayer();
        setDoubleBuffered(true);
        setFocusable(true);
        this.parentContainer = parent;
        try {
            fullHeartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/player/health/full_heart.png"));
            halfHeartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/player/health/half_heart.png"));
            emptyHeartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/player/health/empty_heart.png"));
        } catch (IOException e) {
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
        entities.add(new Ghost(this, 600));
        entities.add(new Bat(this, 400));
        drawables = new ArrayList<>();
        background = new Background(this);
        drawables.add(background);
        terrain = new Terrain(this);
        drawables.add(terrain);
        player = new Player(this);
        drawables.add(player);
        drawables.addAll(entities);
        gameOver = false;
        setFocusable(true);
        transferFocus();
        parentContainer.getGlassPane().setVisible(false);
        gameThread.start();
        parentContainer.getMusicPlayer().getMusicBar().play();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null) {
            if (isRunning) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;
                if (delta >= 1) {
                    update();
                    repaint();
                    parentContainer.getMusicPlayer().repaint();
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
        if (gameOver){
            parentContainer.getMusicPlayer().close();
            return;
        }


        for (var drawable : drawables)
            drawable.update();

        if (cameraHasMoved) {
            for (var entity : entities)
                entity.updatePositionToCamera();
            terrain.updatePositionToCamera();
        }

        if (isColliding()) {
            player.tookDamage(1);

            if (!player.isInvincible())
                player.setInvincible(30); // TODO not hardcode here the invincibility time
        }

        cameraHasMoved = false;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Drawable drawable : drawables)
            drawable.draw(graphics2D);

        if (player != null)
            drawLifePoints(graphics2D);

        if (gameOver) {
            drawGameOver(graphics2D);
        }

        graphics2D.dispose();
    }

    private void drawGameOver(Graphics2D graphics2D) {
        graphics2D.setColor(Color.decode(PRIMARY_COLOR));
        graphics2D.setFont(new Font("Monocraft", Font.BOLD, 72));
        // TODO dynamically center the text
        graphics2D.drawString("GAME OVER", SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2 - 100);
        graphics2D.setFont(new Font("Monocraft", Font.PLAIN, 24));
        graphics2D.drawString("Press ESC to open menu and start again", SCREEN_WIDTH / 2 - 300, SCREEN_HEIGHT / 2);
    }

    /**
     * Dynamically draws the life points to the GUI in the form of full, half or empty hearts.
     *
     * @param graphics2D the graphics environment on which to draw
     */
    private void drawLifePoints(Graphics2D graphics2D) {
        BufferedImage image;
        for (int i = 0; i < PLAYER_MAX_HP; i += 2) {
            if (i < player.getLifePoints()) {
                if (i + 1 < player.getLifePoints()) {
                    image = fullHeartImage;
                } else {
                    image = halfHeartImage;
                }
            } else
                image = emptyHeartImage;
            graphics2D.drawImage(image, 30 + i * 20, 20, 30, 30, null);
        }
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

    public Action showMenu() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(gameOver){
                    parentContainer.getGameMenu().quitToMenu(parentContainer);
                }
                else {
                    GameMenu menu = parentContainer.getGameMenu();
                    menu.setPauseMenu(true);
                    menu.setPausedGridConstraints();
                }
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

    /**
     * Checks if the player is colliding with any entities in the scene.
     *
     * @return true if the player is colliding with at least one other entity in the scene
     */
    public boolean isColliding() {
        for (Entity entity : entities)
            if (!entity.equals(player) && collisionDetector.checkCollision(player, entity)) {
                return true;
            }

        return false;
    }

    /**
     * Sets up a flag that forces the terrain and all the entities update their position relatively to the player.
     */
    public void notifyCameraMoved() {
        this.cameraHasMoved = true;
    }

    public void setGameOver() {
        gameOver = true;
    }
}
