package fr.paris.saclay.sidescroller.utils;

import fr.paris.saclay.sidescroller.abstraction.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler  implements KeyListener {
    //TODO GETTER
    public Direction directionPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case (KeyEvent.VK_RIGHT):
            case (KeyEvent.VK_D): {
                directionPressed = Direction.RIGHT;
                break;
            }
            case (KeyEvent.VK_LEFT):
            case (KeyEvent.VK_A): {
                directionPressed = Direction.LEFT;
                break;
            }
//            case (KeyEvent.VK_UP):
//            case (KeyEvent.VK_W): {
//                directionPressed = Direction.UP;
//                break;
//            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        directionPressed = null;
    }
}
