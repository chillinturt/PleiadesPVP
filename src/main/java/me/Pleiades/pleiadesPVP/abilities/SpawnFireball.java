package me.Pleiades.pleiadesPVP.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpawnFireball {

    public static void SummonFireball(Player player) {
        // Get the direction the player is looking
        Vector lookVector = player.getLocation().getDirection();

        // Spawn the fireball at the player's eye location
        Location spawnLocation = player.getEyeLocation().add(lookVector.multiply(1.5)); // Offset the spawn location slightly in front of the player
        Fireball fireball = player.getWorld().spawn(spawnLocation, Fireball.class);

        // Set the velocity of the fireball
        fireball.setVelocity(lookVector);

        // Optional: Set additional properties (e.g., yield, isIncendiary)
        fireball.setIsIncendiary(false); // Prevents fire spread
        fireball.setYield(2); // Explosion strength

        // Schedule a task to remove the fireball after 5 seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!fireball.isDead()) {
                    fireball.remove(); // Safely remove the fireball
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("PleiadesPVP"), 60L); // 100L = 5 seconds (20 ticks = 1 second)
    }
}
