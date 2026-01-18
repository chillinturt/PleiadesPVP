package me.Pleiades.pleiadesPVP.handlers;

import me.Pleiades.pleiadesPVP.PleiadesPVP;
import me.Pleiades.pleiadesPVP.StartPackage.GrapplingHookItem;
import me.Pleiades.pleiadesPVP.World.WorldBorderManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class GameStateChanger implements Listener, CommandExecutor {
    public String gameState = "PreGame";

    private final PleiadesPVP plugin;
    private final WorldBorderManager borderManager;

    public GameStateChanger(PleiadesPVP plugin, WorldBorderManager borderManager) {
        this.plugin = plugin;
        this.borderManager = borderManager;
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);
        if(Objects.equals(gameState, "play")){
            player.setGameMode(GameMode.SPECTATOR);
        }

        player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1.0f, 2.0f);
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();

        Inventory inv = player.getInventory();
        inv.clear();

        player.setGameMode(GameMode.SPECTATOR);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1.0f, 2.0f);
    }

    public void startGame() {
        setGameState("play");
        Bukkit.getLogger().info("Game was start and game state is now: " + getGameState());

        World gameWorld = Bukkit.getWorld("gameWorld");
        if (gameWorld == null) {
            Bukkit.getLogger().warning("[PleiadesPVP] Game world is not loaded!");
            return;
        }

        // Get the spawn location of the world
        Location spawnLocation = gameWorld.getSpawnLocation();

        // Loop through all players and set them to survival mode
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(spawnLocation);
            player.sendMessage(ChatColor.GREEN + "The game has started! Fight for victory!");


            //give items on game start
            if(player.getScoreboardTags().contains("grappler")){
                GrapplingHookItem.giveGrapplingHook(player);
            }

        }

        // Set the border
        borderManager.setBorder(gameWorld, spawnLocation, 800); // 100-block diameter
    }

    public void setGameState(String newState) {
        Bukkit.getLogger().info("Game state changed to: " + newState);
        this.gameState = newState;
    }

    public String getGameState() {
        return this.gameState;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("startgame")) {
            startGame();
            return true;
        }
        return false;
    }

}
