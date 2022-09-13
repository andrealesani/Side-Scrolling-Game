package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.Player;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalWidthEntityTileSize = 14;
    final int originalHeightEntityTileSize = 19;

    final int originalSquareTileSize = 16;

    final int scale = 3;
    //TODO SCALING BASED ON DEVICE
    public int widthTileSize = originalSquareTileSize *scale;
    public int heightTileSize = originalSquareTileSize *scale;
    public int widthEntityTileSize = originalWidthEntityTileSize *scale;
    public int heightEntityTileSize = originalHeightEntityTileSize *scale;
    final int maxScreenCols = 16;
    final int maxScreenRows = 12;
    final int screenWidth = widthTileSize *maxScreenCols;
    final int screenHeight = heightTileSize *maxScreenRows;
    Thread gameThread;
    InputHandler inputHandler = new InputHandler();
    Player player = new Player(this, inputHandler);
    public GamePanel(){
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(inputHandler);
    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread !=null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    public void update(){
        player.update();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        player.draw(graphics2D);
        graphics2D.dispose();
    }
}
