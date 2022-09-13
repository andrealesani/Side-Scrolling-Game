package fr.paris.saclay.sidescroller.utils;

import fr.paris.saclay.sidescroller.abstraction.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler  implements KeyListener {
    //TODO GETTER
    public boolean upPressed, rightPressed, leftPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case (KeyEvent.VK_RIGHT), (KeyEvent.VK_D) -> {
                if(!upPressed){
                    rightPressed = true;
                }
            }
            case (KeyEvent.VK_LEFT), (KeyEvent.VK_A) ->{
                if(!upPressed){
                    leftPressed = true;
                }
            }
            case (KeyEvent.VK_UP), (KeyEvent.VK_W) -> {
                if(!upPressed){
                    upPressed = true;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case (KeyEvent.VK_RIGHT), (KeyEvent.VK_D) -> rightPressed = false;
            case (KeyEvent.VK_LEFT), (KeyEvent.VK_A) -> leftPressed = false;
        }
    }
}
