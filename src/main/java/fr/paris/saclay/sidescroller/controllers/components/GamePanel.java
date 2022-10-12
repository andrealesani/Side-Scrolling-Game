package fr.paris.saclay.sidescroller.controllers.components;

import fr.paris.saclay.sidescroller.controllers.CollisionDetector;
import fr.paris.saclay.sidescroller.controllers.RPGSideScroller;
import fr.paris.saclay.sidescroller.controllers.components.menu.GameMenu;
import fr.paris.saclay.sidescroller.controllers.components.musicPlayer.MusicPlayer;
import fr.paris.saclay.sidescroller.drawables.Background;
import fr.paris.saclay.sidescroller.drawables.Drawable;
import fr.paris.saclay.sidescroller.drawables.Terrain;
import fr.paris.saclay.sidescroller.drawables.entities.Entity;
import fr.paris.saclay.sidescroller.drawables.entities.Player;
import fr.paris.saclay.sidescroller.drawables.entities.enemies.Bat;
import fr.paris.saclay.sidescroller.drawables.entities.enemies.Ghost;
import fr.paris.saclay.sidescroller.utils.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class GamePanel extends JPanel implements Runnable {

    private final RPGSideScroller parentContainer;
    private final CollisionDetector collisionDetector;
    private final MusicPlayer mediaPlayer;
    public boolean upPressed, rightPressed, leftPressed;
    BufferedImage fullHeartImage, halfHeartImage, emptyHeartImage;
    private boolean isRunning = true;
    private Thread gameThread;
    private Player player;
    private List<Entity> entities = new ArrayList<>();
    private List<Drawable> drawables = new ArrayList<>();
    private Drawable background;
    private Drawable terrain;
    private boolean cameraHasMoved;
    private boolean gameOver = false;
    private int spawnCounter = 0;

    private int score = 0;
    private int scoreOffset = 0;

    public GamePanel(RPGSideScroller parent) {
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

        collisionDetector = new CollisionDetector();
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
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "jump");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "menu");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "attack");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0, false), "attack");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "block");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_COLON, 0, false), "block");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, true), "block_released");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_COLON, 0, false), "block_released");
        getActionMap().put("walk_right", walkRight());
        getActionMap().put("walk_right_released", walkRightReleased());
        getActionMap().put("walk_left", walkLeft());
        getActionMap().put("walk_left_released", walkLeftReleased());
        getActionMap().put("jump", jump());
        getActionMap().put("menu", showMenu());
        getActionMap().put("attack", attack());
        getActionMap().put("block", block());
        getActionMap().put("block_released", blockRelease());
    }

    private Action walkRight() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                rightPressed = true;
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

    private Action walkLeft() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                leftPressed = true;
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
                if (isRunning) {
                    if (gameOver) {
                        parentContainer.getGameMenu().quitToMenu(parentContainer);
                    } else {
                        GameMenu menu = parentContainer.getGameMenu();
                        menu.setPauseMenu(true);
                        menu.setPausedGridConstraints();
                        menu.getModel().getResumeButton().setVisible(true);
                        menu.getModel().getQuitToMenuButton().setVisible(true);
                    }
                    parentContainer.getGlassPane().setVisible(true);
                    isRunning = false;
                    mediaPlayer.stop();
                }
            }
        };
    }

    private Action attack() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.attack();
            }
        };
    }

    private Action block() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.block();
            }
        };
    }

    private Action blockRelease() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.blockRelease();
            }
        };
    }

    public void startGame() {
        gameThread = new Thread(this);
        entities = new ArrayList<>();
//        entities.add(new Ghost(this, 600));
        entities.add(new Bat(this, 400));
        drawables = new ArrayList<>();
        background = new Background(this,
                parentContainer.getGameMenu().getModel().getBackgroundThemes().get(parentContainer.getGameMenu().getModel().getCurrentThemeSelection()));
        drawables.add(background);
        terrain = new Terrain(this,
                parentContainer.getGameMenu().getModel().getBackgroundThemes().get(parentContainer.getGameMenu().getModel().getCurrentThemeSelection()));
        drawables.add(terrain);
        player = new Player(this,
                parentContainer.getGameMenu().getModel().getPlayerThemes().get(parentContainer.getGameMenu().getModel().getCurrentPlayerSelection()));
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
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                if (isRunning) {
                    update();
                }
                repaint();
                parentContainer.getMusicPlayer().repaint();
                delta--;
            }
            if (timer > 1000000000) {
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameOver) {
            parentContainer.getMusicPlayer().close();
            return;
        }

        for (var drawable : drawables)
            drawable.update();

        if (cameraHasMoved) {
            for (var entity : entities)
                entity.updatePositionToCamera(((Background) background).getDeltaX());
            terrain.updatePositionToCamera(((Background) background).getDeltaX());
        }

        List<Entity> damagedEntities = checkCollision();
        if (damagedEntities.size() > 0) {
            for (Entity entity : damagedEntities) {
                entity.tookDamage();
                if (!entity.isInvincible())
                    entity.setEntityInvincible(entity.getMaximumInvincibility());
            }
        }

        for (Entity entity : entities) {
            if (entity.getLifePoints() == 0) {
                if (!entity.isDead()) {
                    entity.setDead(true);
                    score += 100;
                    scoreOffset += 100;
                }
            }
        }

        score = Integer.max(score, -background.getxPosition() / 10 + scoreOffset);
        spawnEnemies();
        cameraHasMoved = false;
    }

    /**
     * Checks if the player is colliding with any entities in the scene.
     *
     * @return true if the player is colliding with at least one other entity in the scene
     */
    public List<Entity> checkCollision() {
        List<Entity> damagedEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (!entity.equals(player) && collisionDetector.checkCollision(player, entity, false) && !player.isInvincible() && !entity.isDead()) {
                if (!damagedEntities.contains(player))
                    damagedEntities.add(player);
            }
            if (player.isAttacking() && collisionDetector.checkCollision(player, entity, true) && !entity.isInvincible()) {
                damagedEntities.add(entity);
            }
        }
        return damagedEntities;
    }

    /**
     * Randomly spawns a new {@code Entity} that is randomly chosen between {@code Ghost} and {@code Bat}.
     * The spawn position depends on the player position but has a random value added so that if the player stands
     * still enemies will not be spawned in the same position.
     */
    private void spawnEnemies() {
        double multiplier = 1.0;
        for (int i = 0; i < score / 1000; i++)
            multiplier *= 1.5;
        spawnCounter++;
        if (spawnCounter >= 60 * 5 / multiplier) {
            Entity entity;
            entity = Math.random() < 0.5 ? new Ghost(this, getPlayerPositionX() + (int) (Math.random() * 400) + 900) : new Bat(this, getPlayerPositionX() + (int) (Math.random() * 400) + 900);
            entities.add(entity);
            drawables.add(entity);
            spawnCounter = 0;
        }
    }

    public int getPlayerPositionX() {
        return player.xPosition;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Drawable drawable : drawables)
            drawable.draw(graphics2D);

        if (player != null)
            drawLifePoints(graphics2D);

        drawScore(graphics2D);

        if (gameOver) {
            drawGameOver(graphics2D);
        }

        graphics2D.dispose();
    }

    /**
     * Dynamically draws the life points to the GUI in the form of full, half or empty hearts.
     *
     * @param graphics2D the graphics environment on which to draw
     */
    private void drawLifePoints(Graphics2D graphics2D) {
        BufferedImage image;
        for (int i = 0; i < player.getMaximumLifePoints(); i += 2) {
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

    /**
     * Dynamically draws the player score in the top right corner of the screen.
     *
     * @param graphics2D the graphics environment on which to draw
     */
    private void drawScore(Graphics2D graphics2D) {
        Font font = new Font("Monocraft", Font.BOLD, 24);
        int scorePosition = SCREEN_WIDTH - graphics2D.getFontMetrics(font).stringWidth(Integer.toString(score)) / 2 - 70;
        graphics2D.setFont(font);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Integer.toString(score), scorePosition + 2, 40 + 2);
        graphics2D.drawString(Integer.toString(score), scorePosition + 2, 40 - 2);
        graphics2D.drawString(Integer.toString(score), scorePosition - 2, 40 + 2);
        graphics2D.drawString(Integer.toString(score), scorePosition - 2, 40 - 2);
        graphics2D.setColor(Color.decode(SECONDARY_COLOR));
        graphics2D.drawString(Integer.toString(score), scorePosition, 40);
    }

    /**
     * Draws the "Game Over" string on the UI.
     *
     * @param graphics2D the graphics environment on which to draw
     */
    private void drawGameOver(Graphics2D graphics2D) {
        graphics2D.setColor(Color.decode(PRIMARY_COLOR));
        Font titleFont = new Font("Monocraft", Font.BOLD, 72);
        graphics2D.setFont(titleFont);
        String gameOver = "GAME OVER";
        graphics2D.drawString(gameOver, SCREEN_WIDTH / 2 - graphics2D.getFontMetrics(titleFont).stringWidth(gameOver) / 2, SCREEN_HEIGHT / 2 - 100);
        titleFont = new Font("Monocraft", Font.PLAIN, 24);
        gameOver = "Press ESC to open menu and start again";
        graphics2D.setFont(titleFont);
        graphics2D.drawString(gameOver, SCREEN_WIDTH / 2 - graphics2D.getFontMetrics(titleFont).stringWidth(gameOver) / 2, SCREEN_HEIGHT / 2);
    }

    public int getPlayerSpeed() {
        return player.speed;
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
     * Sets up a flag that forces the terrain and all the entities update their position relatively to the player.
     */
    public void notifyCameraMoved() {
        this.cameraHasMoved = true;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isPlayerAttacking() {
        return player.isAttacking();
    }

    public Player getPlayer() {
        return player;
    }

    public Drawable getDrawableBackground() {
        return background;
    }
}
