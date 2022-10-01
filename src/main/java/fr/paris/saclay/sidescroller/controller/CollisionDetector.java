package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.entities.Entity;
import fr.paris.saclay.sidescroller.abstraction.entities.Player;

public class CollisionDetector {

    public boolean checkCollision(Player player, Entity entity) {
        return player.getHitBox().intersects(entity.getHitBox());
    }
}
