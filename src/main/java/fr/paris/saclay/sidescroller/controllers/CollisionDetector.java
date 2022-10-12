package fr.paris.saclay.sidescroller.controllers;

import fr.paris.saclay.sidescroller.drawables.entities.Entity;
import fr.paris.saclay.sidescroller.drawables.entities.Player;

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
