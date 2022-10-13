package fr.paris.saclay.sidescroller.utils;

/**
 * Utils class used to store common methods and variables.
 */
public class Constants {
    /**
     * Single square size.
     */
    public static final int ORIGINAL_SQUARE_TILE = 16;
    /**
     * Maximum screen's columns.
     */
    public static final int MAX_SCREEN_COL = 16;
    /**
     * Maximum screen's rows.
     */
    public static final int MAX_SCREEN_ROWS = 12;
    /**
     * Scale factor of the game.
     */
    public static final int SCALE = 4;
    /**
     * Width of scaled tile.
     */
    public static final int WIDTH_TILE_SIZE = ORIGINAL_SQUARE_TILE * SCALE;
    /**
     * Screen width.
     */
    public static final int SCREEN_WIDTH = WIDTH_TILE_SIZE * MAX_SCREEN_COL;
    /**
     * Camera's offset bound for right direction: when the player reaches the half of the screen it stops and the camera is updated.
     */
    public static final int CAMERA_MIN_RIGHT = SCREEN_WIDTH / 2 - WIDTH_TILE_SIZE / 2;
    /**
     * Height of scaled tile.
     */
    public static final int HEIGHT_TILE_SIZE = ORIGINAL_SQUARE_TILE * SCALE;
    /**
     * Screen height.
     */
    public static final int SCREEN_HEIGHT = HEIGHT_TILE_SIZE * MAX_SCREEN_ROWS;
    /**
     * Player maximum health points.
     */
    public static final int PLAYER_MAX_HP = 6;
    /**
     * Player maximum stamina.
     */
    public static final int PLAYER_MAX_STAMINA = 100;
    /**
     * Stamina recovery timer.
     */
    public static final int PLAYER_STAMINA_TIMER = 90;
    /**
     * Player recovery timer, activated on hit.
     */
    public static final int PLAYER_INVINCIBILITY_TIME = 90;
    /**
     * Primary color of the game's theme.
     */
    public static final String PRIMARY_COLOR = "#3c1f30";
    /**
     * Secondary color of the game's theme.
     */
    public static final String SECONDARY_COLOR = "#fcc760";
    /**
     * Camera's offset bound for left direction: when the player walks left the camera is updated before reaching the end
     * of the screen.
     */
    public static final int CAMERA_MIN_LEFT = 300;

    /**
     * Retrieves opposite direction of the provided one (e.g. LEFT parameter -> RIGHT returned value).<br>
     * Used when retrieving the sprites for each entity since right sprites are obtained by translating left sprites.
     *
     * @param direction provided Direction.
     * @return opposite direction.
     */
    public static Direction getOppositeDirection(Direction direction) {
        Direction finalDirection = null;
        switch (direction) {
            case RIGHT -> finalDirection = Direction.LEFT;
            case UP_RIGHT -> finalDirection = Direction.UP_LEFT;
            case ATTACK_RIGHT -> finalDirection = Direction.ATTACK_LEFT;
            case BLOCK_RIGHT -> finalDirection = Direction.BLOCK_LEFT;
        }
        return finalDirection;
    }
}
