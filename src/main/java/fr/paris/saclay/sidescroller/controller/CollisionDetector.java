package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.entities.Entity;
import fr.paris.saclay.sidescroller.abstraction.entities.Player;

public class CollisionDetector {

    public boolean checkCollision(Player player, Entity entity, boolean isAttackHitbox) {
        if (isAttackHitbox) {
            return player.getAttackHitBox().intersects(entity.getHitBox());
        } else {
            if (player.getHitBox().intersects(entity.getHitBox())) {
                return true;
            } else if (player.isBlocking() && player.getBlockHitBox().intersects(entity.getHitBox())) {
                player.setCurrentStamina(player.getCurrentStamina() - 1);
                return false;
            } else return false;
        }
    }
}
