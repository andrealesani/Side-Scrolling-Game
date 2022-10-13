package fr.paris.saclay.sidescroller.controllers.components;

import fr.paris.saclay.sidescroller.controllers.CollisionDetector;
import fr.paris.saclay.sidescroller.controllers.MainFrame;
import fr.paris.saclay.sidescroller.controllers.components.menu.GameMenu;
import fr.paris.saclay.sidescroller.controllers.components.musicPlayer.MusicPlayer;
import fr.paris.saclay.sidescroller.drawables.Background;
import fr.paris.saclay.sidescroller.drawables.Drawable;
import fr.paris.saclay.sidescroller.drawables.Terrain;
import fr.paris.saclay.sidescroller.drawables.entities.Entity;
import fr.paris.saclay.sidescroller.drawables.entities.Player;
import fr.paris.saclay.sidescroller.drawables.entities.enemies.Bat;
import fr.paris.saclay.sidescroller.drawables.entities.enemies.Ghost;

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

/**
 * Main screen displayed, it is the core controller of the game.
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     * Parent reference.
     */
    private final MainFrame parentContainer;
    /**
     * CollisionDetector reference.
     */
    private final CollisionDetector collisionDetector;
    /**
     * MusicPlayer reference.
     */
    private final MusicPlayer mediaPlayer;
    /**
     * Flags identifying if certain keyboard keys are pressed.
     */
    public boolean upPressed,
            rightPressed,
            leftPressed;
    /**
     * Life point images representing each possible state for each heart.
     */
    private BufferedImage fullHeartImage,
            halfHeartImage,
            emptyHeartImage;
    /**
     * Flag identifying if game is running and not paused.
     */
    private boolean isRunning = false;
    /**
     * Game thread.
     */
    private Thread gameThread;
    /**
     * The player.
     */
    private Player player;
    /**
     * List of entities on the screen.
     */
    private List<Entity> entities = new ArrayList<>();
    /**
     * List of Drawables on the screen.
     */
    private List<Drawable> drawables = new ArrayList<>();
    /**
     * Moving background.
     */
    private Drawable background;
    /**
     * Moving terrain.
     */
    private Drawable terrain;
    /**
     * Flag identifying if camera has to move (if player moves or reaches the half of the screen).
     */
    private boolean cameraHasMoved;
    /**
     * Identifies if game is over or not.
     */
    private boolean gameOver = false;
    /**
     * Counter of enemies spawned.
     */
    private int spawnCounter = 0;
    /**
     * Total player score.
     */
    private int score = 0;
    /**
     * Scores obtained by dead enemies.
     */
    private int scoreOffset = 0;
    /**
     * Debug flag disabling/enabling hitbox drawing.
     */
    private boolean isDebugHitbox = false;
    /**
     * Debug flag disabling/enabling enemies' spawn.
     */
    private boolean isDebugEnemyGeneration = false;

    /**
     * Creates a GamePanel instance.
     *
     * @param parent parent reference.
     */
    public GamePanel(MainFrame parent) {
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

    /**
     * Setups all keyboard keybindings.
     */
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
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, 0, false), "toggle_hitbox");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false), "toggle_entities");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, false), "toggle_invulnerability");
        getActionMap().put("walk_right", walkRight());
        getActionMap().put("walk_right_released", walkRightReleased());
        getActionMap().put("walk_left", walkLeft());
        getActionMap().put("walk_left_released", walkLeftReleased());
        getActionMap().put("jump", jump());
        getActionMap().put("menu", showMenu());
        getActionMap().put("attack", attack());
        getActionMap().put("block", block());
        getActionMap().put("block_released", blockRelease());
        getActionMap().put("toggle_hitbox", toggleHitbox());
        getActionMap().put("toggle_entities", toggleEntities());
        getActionMap().put("toggle_invulnerability", toggleInvulnerability());
    }

    /**
     * Handles walk right action.
     *
     * @return Action.
     */
    private Action walkRight() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    rightPressed = true;
            }
        };
    }

    /**
     * Handles walk right release action.
     *
     * @return Action.
     */
    private Action walkRightReleased() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    rightPressed = false;
            }
        };
    }

    /**
     * Handles walk left action.
     *
     * @return Action.
     */
    private Action walkLeft() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    leftPressed = true;
            }
        };
    }

    /**
     * Handles walk left release action.
     *
     * @return Action.
     */
    private Action walkLeftReleased() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    leftPressed = false;
            }
        };
    }

    /**
     * Handles jump action.
     *
     * @return Action.
     */
    private Action jump() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!upPressed && isRunning) {
                    upPressed = true;
                }
            }
        };
    }

    /**
     * Shows pause menu.
     *
     * @return Action.
     */
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

    /**
     * Handles attack action.
     *
     * @return Action.
     */
    private Action attack() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    player.attack();
            }
        };
    }

    /**
     * Handles block action.
     *
     * @return Action.
     */
    private Action block() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    player.block();
            }
        };
    }

    /**
     * Handles block release.
     *
     * @return Action.
     */
    private Action blockRelease() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                    player.blockRelease();
            }
        };
    }

    /**
     * Enables/Disables hitbox drawing (for debug purpose).
     *
     * @return Action.
     */
    private Action toggleHitbox() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setDebugHitbox();
            }
        };
    }

    /**
     * Enables/Disables enemies' spawning (for debug purpose).
     *
     * @return Action.
     */
    private Action toggleEntities() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setDebugEnemyGeneration();
            }
        };
    }

    /**
     * Enables/Disables invulnerability for the player (for debug purpose).
     *
     * @return Action.
     */
    private Action toggleInvulnerability() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!player.isInvincible())
                    player.setEntityInvincible(-1);
                else
                    player.setEntityInvincible(0);
            }
        };
    }

    /**
     * Sets debug hitbox.
     */
    public void setDebugHitbox() {
        isDebugHitbox = !isDebugHitbox;
    }

    /**
     * Sets debug enemy generation.
     */
    public void setDebugEnemyGeneration() {
        isDebugEnemyGeneration = !isDebugEnemyGeneration;
    }

    /**
     * Starts game and generates level.
     */
    public void startGame() {
        gameThread = new Thread(this);
        entities = new ArrayList<>();
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

    /**
     * Thread main method.
     */
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

    /**
     * Handles update for each entity/drawable.
     */
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

        score = Integer.max(score, -background.getXPosition() / 10 + scoreOffset);
        if (!isDebugEnemyGeneration)
            spawnEnemies();
        cameraHasMoved = false;
    }

    /**
     * Check collisions of drawn entities.
     *
     * @return list of entities colliding.
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
     * Handles the spawning of enemies (semi-random).
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

    /**
     * Gets player position x.
     *
     * @return the player position x
     */
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
     * Draws life points in the top left corner: each time the player takes damage, the life bar is updated.
     *
     * @param graphics2D the rendering environment.
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
     * Draws score in the top right corner: each time the player walks forward or kill an enemy the score is updated.
     *
     * @param graphics2D the rendering environment.
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
     * Draws Game Over text when player is dead.
     *
     * @param graphics2D the rendering environment.
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

    /**
     * Gets player speed.
     *
     * @return the player speed
     */
    public int getPlayerSpeed() {
        return player.speed;
    }

    /**
     * Sets running.
     *
     * @param running the running
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * Play.
     */
    public void play() {
        mediaPlayer.start();
    }

    /**
     * Stop.
     */
    public void stop() {
        gameThread.stop();
    }

    /**
     * Notify camera moved.
     */
    public void notifyCameraMoved() {
        this.cameraHasMoved = true;
    }

    /**
     * Sets game over.
     */
    public void setGameOver() {
        gameOver = true;
    }

    /**
     * Is player attacking boolean.
     *
     * @return the boolean
     */
    public boolean isPlayerAttacking() {
        return player.isAttacking();
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets drawable background.
     *
     * @return the drawable background
     */
    public Drawable getDrawableBackground() {
        return background;
    }

    /**
     * Is debug hitbox boolean.
     *
     * @return the boolean
     */
    public boolean isDebugHitbox() {
        return isDebugHitbox;
    }
}
