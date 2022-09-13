package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.GamePanel;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gamePanel;
    InputHandler inputHandler;

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        this.gamePanel = gamePanel;
        this.inputHandler = inputHandler;
        setPlayerDefaultPosition();
        setPlayerImage();
    }

    public void setPlayerDefaultPosition(){
        xPosition = 100;
        yPosition = 450;
        speed = 4;
        direction = Direction.RIGHT;
    }

    public void setPlayerImage(){
        try {
            upSpritePrimary = ImageIO.read(getClass().getClassLoader().getResourceAsStream("alienGreen_right.png"));
            upSpriteSecondary = ImageIO.read(getClass().getClassLoader().getResourceAsStream("alienGreen_right2.png"));
            leftSpritePrimary = ImageIO.read(getClass().getClassLoader().getResourceAsStream("alienGreen_left.png"));
            leftSpriteSecondary = ImageIO.read(getClass().getClassLoader().getResourceAsStream("alienGreen_left2.png"));
            rightSpritePrimary = ImageIO.read(getClass().getClassLoader().getResourceAsStream("alienGreen_right.png"));
            rightSpriteSecondary = ImageIO.read(getClass().getClassLoader().getResourceAsStream("alienGreen_right2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(inputHandler.directionPressed !=null) {
            inputHandler.directionPressed.doAction(this);
            spriteCounter++;
            if(spriteCounter>5){
                if(spriteNumber==2){
                    spriteNumber = 1;
                }
                else spriteNumber ++;
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;

            switch (direction) {
//                case UP -> {
//                    if (spriteNumber == 1) {
//                        image = upSpritePrimary;
//                    } else if (spriteNumber == 2) {
//                        image = upSpriteSecondary;
//                    }
//                }
                case LEFT -> {
                    if (spriteNumber == 1) {
                        image = leftSpritePrimary;
                    } else if (spriteNumber == 2) {
                        image = leftSpriteSecondary;
                    }
                }
                case RIGHT -> {
                    if (spriteNumber == 1) {
                        image = rightSpritePrimary;
                    } else if (spriteNumber == 2) {
                        image = rightSpriteSecondary;
                    }
                }
            }
        graphics2D.drawImage(image, xPosition, yPosition, gamePanel.widthEntityTileSize, gamePanel.heightEntityTileSize, null);
    }
}
