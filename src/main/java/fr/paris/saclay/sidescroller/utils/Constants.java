package fr.paris.saclay.sidescroller.utils;

public class Constants {
    public static final int ORIGINAL_SQUARE_TILE = 16;
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROWS = 12;
    public static final int SCALE = 4;
    //TODO SCALING BASED ON DEVICE
    public static final int WIDTH_TILE_SIZE = ORIGINAL_SQUARE_TILE * SCALE;
    public static final int HEIGHT_TILE_SIZE = ORIGINAL_SQUARE_TILE * SCALE;

    public static final int SCREEN_WIDTH = WIDTH_TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = HEIGHT_TILE_SIZE * MAX_SCREEN_ROWS;
    public static final int PLAYER_MAX_HP = 6;
}
