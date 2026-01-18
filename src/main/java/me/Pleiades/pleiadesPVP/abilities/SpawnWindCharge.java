package me.Pleiades.pleiadesPVP.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpawnWindCharge {

    public static void SummonWindCharge(Player player) {
        // Get the direction the player is looking
        Vector lookVector = player.getLocation().getDirection();

        // Spawn the wind charge at the player's eye location
        Location spawnLocation = player.getEyeLocation().add(lookVector.multiply(1.5)); // Offset the spawn location slightly in front of the player
        Entity windCharge = player.getWorld().spawn(spawnLocation, WindCharge.class);
        Entity windCharge2 = player.getWorld().spawn(spawnLocation, WindCharge.class);
        Entity windCharge3 = player.getWorld().spawn(spawnLocation, WindCharge.class);

        // Set the velocity of the wind charge
        windCharge.setVelocity(lookVector);
        windCharge2.setVelocity(lookVector);
        windCharge3.setVelocity(lookVector);

        // Optional: Set any additional properties if necessary
        windCharge.addPassenger(player);

        // Schedule a task to remove the wind charge after 1 seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!windCharge.isDead()) {
                    windCharge.remove(); // Safely remove the wind charge
                }
                if (!windCharge2.isDead()) {
                    windCharge2.remove(); // Safely remove the wind charge
                }
                if (!windCharge3.isDead()) {
                    windCharge3.remove(); // Safely remove the wind charge
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("PleiadesPVP"), 20L); // 100L = 5 seconds (20 ticks = 1 second)
    }
}
