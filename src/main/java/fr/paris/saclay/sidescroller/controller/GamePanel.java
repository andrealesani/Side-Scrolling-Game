package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.Player;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{

    final int originalSquareTileSize = 16;

    final int scale = 4;
    //TODO SCALING BASED ON DEVICE
    public int widthTileSize = originalSquareTileSize *scale;
    public int heightTileSize = originalSquareTileSize *scale;
    final int maxScreenCols = 16;
    final int maxScreenRows = 12;
    public final int screenWidth = widthTileSize *maxScreenCols;
    public final int screenHeight = heightTileSize *maxScreenRows;
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
        long timer = 0;
        int drawCount = 0;
        while (gameThread !=null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta>=1){
                update();
                repaint();
                drawCount++;
                delta--;
            }
            if(timer>1000000000){
//                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){
//        System.out.println(player.direction);
        player.update();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        try {
            int i=0;
            while(i<screenWidth){
                for (int j = 0; j < 2; j++) {
                    graphics2D.drawImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("grass.png")), i, screenHeight - heightTileSize-1, widthTileSize, heightTileSize, null);
                }
                i +=widthTileSize;
            }
            graphics2D.drawImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("grasslands.png")), 0, 0, screenWidth*scale, screenHeight - heightTileSize, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.draw(graphics2D);
        graphics2D.dispose();
    }
}
