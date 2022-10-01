package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.entities.Entity;
import fr.paris.saclay.sidescroller.abstraction.entities.Player;

public class CollisionDetector {
    GamePanel gamePanel;

    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkCollision(Player player, Entity entity) {
        if (player.getHitBox().intersects(entity.getHitBox())) {
            return true;
        }
        return false;
    }
}
