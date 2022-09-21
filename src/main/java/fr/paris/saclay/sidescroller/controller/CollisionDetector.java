package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.Direction;
import fr.paris.saclay.sidescroller.abstraction.Entity;
import fr.paris.saclay.sidescroller.abstraction.Player;

public class CollisionDetector {
    GamePanel gamePanel;

    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkCollision(Player player, Entity entity) {
        //System.out.println("player:" + player.xPosition + " enemy: " + entity.xPosition);
        if (player.hitBox.intersects(entity.hitBox)) {
            return true;
        }
        return false;
    }
}
