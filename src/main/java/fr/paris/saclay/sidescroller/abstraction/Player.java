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
        yPosition = gamePanel.screenHeight -gamePanel.heightTileSize*2;
        speed = 4;
        direction = Direction.RIGHT;
    }

    public void setPlayerImage(){
        try {
            upLeftSprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump.png"));
            upLeftSprite2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_2.png"));
            upLeftSprite3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_3.png"));
            upLeftSprite4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_4.png"));
            upLeftSprite5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_5.png"));
            upLeftSprite6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left.png"));
            upRightSprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right_jump.png"));
            upRightSprite2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right_jump_2.png"));
            upRightSprite3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right_jump_3.png"));
            upRightSprite4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right_jump_4.png"));
            upRightSprite5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right_jump_5.png"));
            upRightSprite6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right.png"));
            leftSprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left.png"));
            leftSprite2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_2.png"));
            rightSprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right.png"));
            rightSprite2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if((inputHandler.rightPressed || inputHandler.upPressed || inputHandler.leftPressed)){
            if(inputHandler.rightPressed) {
                direction = Direction.RIGHT;
                xPosition += speed;
            } else if (inputHandler.leftPressed) {
                direction = Direction.LEFT;
                xPosition -= speed;
            } else {
                isJumping = true;
                switch (direction){
                    case LEFT-> {
                        direction = Direction.UP_LEFT;
                        spriteNumber = 0;
                    }
                    case RIGHT-> {
                        direction = Direction.UP_RIGHT;
                        spriteNumber = 0;
                    }
                    case UP_LEFT -> {
                        if (spriteNumber == 2 || spriteNumber ==3){
                            xPosition -=speed;
                        }
                        if(spriteNumber == 1 || spriteNumber == 2){
                            yPosition -= speed;
                        }
                        else if(spriteNumber == 4 || spriteNumber == 5){
                            yPosition += speed;
                        }
                    }
                    case UP_RIGHT -> {
                        if (spriteNumber == 2 || spriteNumber ==3){
                            xPosition +=speed;
                        }
                        if(spriteNumber == 1 || spriteNumber == 2){
                            yPosition -= speed;
                        }
                        else if(spriteNumber == 4 || spriteNumber == 5){
                            yPosition += speed;
                        }
                    }
                }
            }
            spriteCounter++;
            if(spriteCounter>5 && !isJumping){
                    if(spriteNumber==2) spriteNumber = 1;
                    else spriteNumber ++;
                    spriteCounter = 0;
            }
            else if(spriteCounter>5 && isJumping){
                    if(spriteNumber==6) {
                        spriteNumber=1;
                        isJumping = false;
                        inputHandler.upPressed = false;
                    }
                    else spriteNumber++;
                    spriteCounter = 0;
                }
            }
        }
    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        System.out.println(spriteNumber);
        switch (direction){
            case UP_LEFT ->{
                if (spriteNumber == 1) {
                    image = upLeftSprite;
                } else if (spriteNumber == 2) {
                    image = upLeftSprite2;
                } else if (spriteNumber == 3) {
                    image = upLeftSprite3;
                } else if (spriteNumber == 4) {
                    image = upLeftSprite4;
                } else if (spriteNumber == 5) {
                    image = upLeftSprite5;
                } else if(spriteNumber == 6) {
                    image = upLeftSprite6;
                    isJumping = false;
                }
            }
            case UP_RIGHT ->{
                if (spriteNumber == 1) {
                    image = upRightSprite;
                } else if (spriteNumber == 2) {
                    image = upRightSprite2;
                } else if (spriteNumber == 3) {
                    image = upRightSprite3;
                } else if (spriteNumber == 4) {
                    image = upRightSprite4;
                } else if (spriteNumber == 5) {
                    image = upRightSprite5;
                } else  {
                    image = upRightSprite6;
                }
            }
            case RIGHT -> {
                if (spriteNumber == 1) {
                    image = rightSprite;
                } else if (spriteNumber == 2) {
                    image = rightSprite2;
                }
            }
            case LEFT -> {
                if (spriteNumber == 1) {
                    image = leftSprite;
                } else if (spriteNumber == 2) {
                    image = leftSprite2;
                }
            }
        }

        graphics2D.drawImage(image, xPosition, yPosition, gamePanel.widthTileSize, gamePanel.heightTileSize, null);
    }
}
