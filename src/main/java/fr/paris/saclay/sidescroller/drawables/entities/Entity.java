package fr.paris.saclay.sidescroller.drawables.entities;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.drawables.Drawable;
import fr.paris.saclay.sidescroller.utils.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.HEIGHT_TILE_SIZE;
import static fr.paris.saclay.sidescroller.utils.Constants.WIDTH_TILE_SIZE;
import static fr.paris.saclay.sidescroller.utils.Direction.*;

/**
 * Template for each moving entity (player or enemy).
 */
public abstract class Entity extends Drawable {
    /**
     * Links the Direction the entity is facing to the list of images that represent the entity in that direction.
     */
    protected final HashMap<Direction, List<BufferedImage>> animationMap = new HashMap<>();
    /**
     * The number of the life points that the entity currently has.
     */
    protected int lifePoints;
    /**
     * Identifies the number of frames that have to be rendered before changing a sprite (during an animation).
     */
    protected int spriteCounter = 0;
    /**
     * True when entity's life points reach 0.
     */
    protected boolean isDead = false;
    /**
     * Identifies the sprite that is currently being rendered
     */
    protected int spriteNumber = 0;
    /**
     * Is true when the entity is jumping, therefore blocking any other type of movement.
     */
    protected boolean isJumping = false;
    /**
     * Maximum life points.
     */
    protected int maximumLifePoints;
    /**
     * Final image that is shown during the rendering of the entity.
     */
    protected BufferedImage image;
    /**
     * We decided to make every hitbox square.
     * Therefore, this number indicates both the height and the width of the hitbox square (in pixels).
     */
    protected int hitboxSize;
    /**
     * Hitbox size of attack action.
     */
    protected Dimension attackHitboxSize = new Dimension(0, 0);
    /**
     * Hitbox size of block action.
     */
    protected Dimension blockHitboxSize = new Dimension(0, 0);
    /**
     * Represents the area of the entity that is used to compute collisions with enemies or projectiles.
     */
    protected Rectangle hitBox;
    /**
     * Represents the area of the entity that is used to compute collisions with enemies or projectiles.
     */
    protected Rectangle attackHitBox;
    /**
     * Represents the area of the entity that is used to compute blocks with enemies or projectiles.
     */
    protected Rectangle blockHitBox;
    /**
     * True if entity is currently invincible.
     */
    protected boolean isInvincible;
    /**
     * Number of frames during which the player will stay invincible.
     */
    protected int invincibilityTimer;
    /**
     * Maximum invincibility timer.
     */
    protected int maximumInvincibility;
    /**
     * True if entity is currently attacking.
     */
    protected boolean isAttacking = false;
    /**
     * True if entity is currently blocking.
     */
    protected boolean isBlocking = false;

    /**
     * Creates an Entity instance passing down GamePanel reference.
     *
     * @param gamePanel reference.
     */
    public Entity(GamePanel gamePanel) {
        super(gamePanel);
        updateHitboxPosition(); // gamePanel is drawn before thread starting
    }

    /**
     * Updates the position of the entity's hitbox according to the entity's position inside the screen.
     */
    protected void updateHitboxPosition() {
        int hitBoxHorizontalPosition = xPosition + WIDTH_TILE_SIZE / 2 - hitboxSize / 2;
        hitBox = new Rectangle(hitBoxHorizontalPosition, yPosition + HEIGHT_TILE_SIZE / 2 - hitboxSize / 2, hitboxSize, hitboxSize);
        int directionFactor = 0;
        int directionHitFactor = 1;
        if (direction == LEFT || direction == ATTACK_LEFT || direction == UP_LEFT || direction == BLOCK_LEFT) {
            directionFactor = 1;
            directionHitFactor = 0;
        }
        attackHitBox = new Rectangle(hitBoxHorizontalPosition + hitboxSize * directionHitFactor - attackHitboxSize.width * directionFactor, yPosition, attackHitboxSize.width, attackHitboxSize.height);
        blockHitBox = new Rectangle(hitBoxHorizontalPosition + hitboxSize * directionHitFactor - blockHitboxSize.width * directionFactor, yPosition, blockHitboxSize.width, blockHitboxSize.height);
    }

    /**
     * Decreases life points if the entity reaches damage.
     */
    public void tookDamage() {
        if (!isInvincible)
            this.lifePoints -= 1;
    }

    /**
     * Adds provided walk sprites to animation map.
     *
     * @param paths sprites.
     */
    protected void setSprites(List<String> paths) {
        addSpritesToAnimationMap(paths, new ArrayList<>(), LEFT);
    }

    /**
     * Adds sprites to the animation map: each sprite contains its file path and the related Direction (LEFT, UP_LEFT,
     * ATTACK_LEFT, BLOCK_LEFT)
     *
     * @param paths     new sprites to be added.
     * @param sprites   animation map.
     * @param direction direction of the sprites.
     */
    private void addSpritesToAnimationMap(List<String> paths, List<BufferedImage> sprites, Direction direction) {
        try {
            for (String path : paths)
                sprites.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(path)));

            animationMap.put(direction, sprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds provided jump sprites to animation map.
     *
     * @param paths sprites.
     */
    protected void setJumpSprites(List<String> paths) {
        addSpritesToAnimationMap(paths, new ArrayList<>(), Direction.UP_LEFT);
    }

    /**
     * Adds provided attack sprites to animation map.
     *
     * @param paths sprites.
     */
    protected void setAttackSprites(List<String> paths) {
        addSpritesToAnimationMap(paths, new ArrayList<>(), Direction.ATTACK_LEFT);
    }

    /**
     * Adds provided block sprites to animation map.
     *
     * @param paths sprites.
     */
    protected void setBlockSprites(List<String> paths) {
        addSpritesToAnimationMap(paths, new ArrayList<>(), Direction.BLOCK_LEFT);
    }

    /**
     * Gets life points.
     *
     * @return the life points
     */
    public int getLifePoints() {
        return lifePoints;
    }

    /**
     * Gets hit box.
     *
     * @return the hit box
     */
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * Gets block hit box.
     *
     * @return the block hit box
     */
    public Rectangle getBlockHitBox() {
        return blockHitBox;
    }

    /**
     * Sets entity invincible.
     *
     * @param invincibilityTimer the invincibility timer
     */
    public void setEntityInvincible(int invincibilityTimer) {
        this.isInvincible = true;
        this.invincibilityTimer = invincibilityTimer;
    }

    /**
     * Gets maximum invincibility.
     *
     * @return the maximum invincibility
     */
    public int getMaximumInvincibility() {
        return maximumInvincibility;
    }

    /**
     * Is attacking boolean.
     *
     * @return the boolean
     */
    public boolean isAttacking() {
        return isAttacking;
    }

    /**
     * Sets attacking.
     *
     * @param isAttacking the is attacking
     */
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    /**
     * Gets attack hit box.
     *
     * @return the attack hit box
     */
    public Rectangle getAttackHitBox() {
        return attackHitBox;
    }

    /**
     * Gets maximum life points.
     *
     * @return the maximum life points
     */
    public int getMaximumLifePoints() {
        return maximumLifePoints;
    }

    /**
     * Is dead boolean.
     *
     * @return the boolean
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets dead.
     *
     * @param dead the dead
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Defines actions for each frame:
     * <ul>
     * <li>
     *     chasePlayer: tracks players movement and defines the shortest path to it.
     * </li>
     * <li>
     *     uniqueMovement: implements unique movement if entity requires it.
     * </li>
     * <li>
     *     updateHitboxPosition: tracks entity movement and updates hitbox.
     * </li>
     * </ul>
     */
    @Override
    public void update() {
        if (!isDead) {
            chasePlayer();

            uniqueMovement();

            updateSprites();

            updateHitboxPosition();

            if (invincibilityTimer == 0)
                isInvincible = false;
            else
                invincibilityTimer--;
        }
    }

    /**
     * Entities present a default version of the {@code draw()} method. This method only supports basic sprite
     * animations, namely when moving to the left and right directions (jump, up-left and up-right are not supported).
     * Feel free to {@code Override} if you want to implement more fancy stuff.
     *
     * @param graphics2D the rendering environment.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        if (!isDead) {
            AffineTransform transformX = AffineTransform.getScaleInstance(-1, 1);
            switch (direction) {
                case LEFT, UP_LEFT -> image = animationMap.get(direction).get(spriteNumber);
                case RIGHT, UP_RIGHT -> {
                    transformX.translate(-animationMap.get(LEFT).get(spriteNumber).getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(transformX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(animationMap.get(LEFT).get(spriteNumber), null);
                }
            }

            graphics2D.drawImage(image, xPosition, yPosition, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE, null);
            if (gamePanel.isDebugHitbox()) {
                graphics2D.setColor(new Color(0, 0, 255, 127));
                if (isInvincible())
                    graphics2D.setColor(new Color(255, 0, 0, 127));
                else
                    graphics2D.setColor(new Color(0, 0, 255, 127));
                graphics2D.fill(hitBox);
                graphics2D.setColor(new Color(0, 255, 0, 127));
                if (isAttacking)
                    graphics2D.fill(attackHitBox);
                if (isBlocking)
                    graphics2D.fill(blockHitBox);
            }
            drawHpBar(graphics2D);
        }
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    protected void drawHpBar(Graphics2D graphics2D) {
        int maximumWidthBar = WIDTH_TILE_SIZE + WIDTH_TILE_SIZE / 4;
        int lostLifePoints = maximumLifePoints - lifePoints;
        int currentBarPosition = xPosition - WIDTH_TILE_SIZE / 8;
        graphics2D.setColor(Color.green);
        for (int i = 0; i < lifePoints; i++) {
            graphics2D.fillRect(currentBarPosition, yPosition - WIDTH_TILE_SIZE / 4, maximumWidthBar / maximumLifePoints, 5);
            currentBarPosition = currentBarPosition + maximumWidthBar / maximumLifePoints;
        }
        if (lostLifePoints > 0) {
            graphics2D.setColor(Color.red);
            for (int i = 0; i < lostLifePoints; i++) {
                graphics2D.fillRect(currentBarPosition, yPosition - WIDTH_TILE_SIZE / 4, maximumWidthBar / maximumLifePoints, 5);
                currentBarPosition = currentBarPosition + maximumWidthBar / maximumLifePoints;
            }
        }
    }

    /**
     * This method makes the entity chase the player on the X axis until its hitbox collides with the one of the player.
     */
    protected void chasePlayer() {
        int blockOffset = gamePanel.getPlayer().isBlocking() ? blockHitBox.width / 2 : 0;
        if (gamePanel.getPlayerPositionX() > xPosition + hitboxSize / 2 + blockOffset) {
            xPosition += speed;
            direction = RIGHT;
        } else if (gamePanel.getPlayerPositionX() < xPosition - hitboxSize / 2 - blockOffset) {
            xPosition -= speed;
            direction = LEFT;
        }
    }

    /**
     * Defines unique movement for each entity, currently implemented in enemies.
     */
    protected void uniqueMovement() {
    }

    /**
     * Entities present a default version of the {@code updateSprites()} method.
     * Each sprite only lasts for 5 frames before changing to the next one.
     * Feel free to {@code Override} if you want to implement more fancy stuff.
     */
    protected void updateSprites() {
        spriteCounter++;

        if (spriteCounter > 9) {
            if (spriteNumber == animationMap.get(LEFT).size() - 1)
                spriteNumber = 0;
            else
                spriteNumber++;
            spriteCounter = 0;
        }
    }

    /**
     * Is blocking boolean.
     *
     * @return the boolean
     */
    public boolean isBlocking() {
        return isBlocking;
    }

    /**
     * Is jumping boolean.
     *
     * @return the boolean
     */
    public boolean isJumping() {
        return isJumping;
    }
}
