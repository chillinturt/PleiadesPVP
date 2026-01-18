package me.Pleiades.pleiadesPVP.World;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldBorderManager {
    private final JavaPlugin plugin;  // ✅ Remove static

    public WorldBorderManager(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");  // ❗ Ensure plugin is not null
        }
        this.plugin = plugin;
    }

    public void setBorder(World world, Location center, double size) {
        if (world == null) {
            Bukkit.getLogger().warning("[PleiadesPVP] World is null! Cannot set border.");
            return;
        }
        WorldBorder border = world.getWorldBorder();
        border.setCenter(center);
        border.setSize(size);
        border.setWarningDistance(5);
        border.setDamageAmount(2.0);

        Bukkit.getLogger().info("World border set to 800x800");

        // Delay shrinking by 15 minutes (9,000 ticks)
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            border.setSize(64, 900);
            plugin.getLogger().info("World border shrinking to 64x64 over 15 minutes.");

            // Play sound to all players
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
            }

        }, 600);
    }
}
