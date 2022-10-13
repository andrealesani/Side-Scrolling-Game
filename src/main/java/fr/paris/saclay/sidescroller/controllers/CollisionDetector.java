package fr.paris.saclay.sidescroller.controllers;

import fr.paris.saclay.sidescroller.drawables.entities.Entity;
import fr.paris.saclay.sidescroller.drawables.entities.Player;

/**
 * Handles entities' collisions.
 */
public class CollisionDetector {
    /**
     * Checks collisions between the player and the enemies. If player is attacking, it checks if the attack's hitbox
     * intersects the enemy's hitbox; valid also for the block's hitbox.
     *
     * @param player         Player entity.
     * @param entity         enemy entity.
     * @param isAttackHitbox identifies if it is checking attack hitbox.
     * @return true if entity is colliding, false otherwise.
     */
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
