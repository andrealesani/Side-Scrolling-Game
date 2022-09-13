package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.GamePanel;
import fr.paris.saclay.sidescroller.utils.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            List<BufferedImage> upLeftSprites = Arrays.asList(
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_2.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_3.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_4.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_jump_5.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left.png")));
            animationMap.put(Direction.UP_LEFT,upLeftSprites);
            List<BufferedImage> leftSprites = Arrays.asList(
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left.png")),
                    ImageIO.read(getClass().getClassLoader().getResourceAsStream("pink_alien_left_2.png")));
            animationMap.put(Direction.LEFT,leftSprites);
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
                            yPosition -= speed*2;
                        }
                        else if(spriteNumber == 4 || spriteNumber == 5){
                            yPosition += speed*2;
                        }
                    }
                    case UP_RIGHT -> {
                        if (spriteNumber == 2 || spriteNumber ==3){
                            xPosition +=speed;
                        }
                        if(spriteNumber == 1 || spriteNumber == 2){
                            yPosition -= speed*2;
                        }
                        else if(spriteNumber == 4 || spriteNumber == 5){
                            yPosition += speed*2;
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
            else if(spriteCounter>5){
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
//        System.out.println(spriteNumber);
        AffineTransform transformX = AffineTransform.getScaleInstance(-1, 1);
        if(spriteNumber!=0) {
            switch (direction) {
                case LEFT, UP_LEFT -> {
                    if (direction == Direction.UP_LEFT && spriteNumber == 6) {
                        isJumping = false;
                    }
                    image = animationMap.get(direction).get(spriteNumber - 1);
                }
                case RIGHT, UP_RIGHT -> {
                    if (direction == Direction.UP_RIGHT && spriteNumber == 6) {
                        isJumping = false;
                    }
                    Direction utilDirection = direction == Direction.RIGHT ? Direction.LEFT : Direction.UP_LEFT;
                    transformX.translate(-animationMap.get(utilDirection).get(spriteNumber - 1).getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(transformX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(animationMap.get(utilDirection).get(spriteNumber - 1), null);
                }
            }
        }

        graphics2D.drawImage(image, xPosition, yPosition, gamePanel.widthTileSize, gamePanel.heightTileSize, null);
    }
}
