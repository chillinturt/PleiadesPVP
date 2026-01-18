package me.Pleiades.pleiadesPVP.World;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class WorldDeleteManager {
    private final JavaPlugin plugin;

    public WorldDeleteManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void deleteWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            plugin.getLogger().warning("[WorldDelete] World '" + worldName + "' not found. Skipping deletion.");
            return;
        }

        // Unload the world
        Bukkit.getServer().unloadWorld(world, false);
        plugin.getLogger().info("[WorldDelete] World '" + worldName + "' has been unloaded.");

        // Delete the world folder
        File worldFolder = world.getWorldFolder();
        if (deleteDirectory(worldFolder.toPath())) {
            plugin.getLogger().info("[WorldDelete] World '" + worldName + "' has been deleted.");
        } else {
            plugin.getLogger().warning("[WorldDelete] Failed to delete world '" + worldName + "'.");
        }
    }

    private boolean deleteDirectory(Path path) {
        try {
            Files.walk(path)
                    .sorted((a, b) -> b.compareTo(a)) // Delete children before parents
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            plugin.getLogger().log(Level.WARNING, "[WorldDelete] Failed to delete: " + p, e);
                        }
                    });
            return !Files.exists(path);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "[WorldDelete] Error deleting directory: " + path, e);
            return false;
        }
    }
    public void scheduleWorldDeletion(String worldName) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            deleteWorld(worldName);
        }));
    }
}
