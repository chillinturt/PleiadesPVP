package me.Pleiades.pleiadesPVP;

import me.Pleiades.pleiadesPVP.StartPackage.GiveStarterItems;
import me.Pleiades.pleiadesPVP.StartPackage.GrapplingHookItem;
import me.Pleiades.pleiadesPVP.World.WorldBorderManager;
import me.Pleiades.pleiadesPVP.World.WorldDeleteManager;
import me.Pleiades.pleiadesPVP.abilities.MinerAbility;
import me.Pleiades.pleiadesPVP.commands.CreateEnergyScore;
import me.Pleiades.pleiadesPVP.commands.Fly;
import me.Pleiades.pleiadesPVP.commands.Menu;
import me.Pleiades.pleiadesPVP.handlers.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PleiadesPVP extends JavaPlugin {

    private static PleiadesPVP instance;

    private WorldDeleteManager worldDeleteManager;

    @Override
    public void onEnable() {
        getLogger().info("Enabling PleiadesPVP...");

        instance = this;

        // Register the plugin channel for Velocity
        getServer().getMessenger().registerOutgoingPluginChannel(this, "velocity:player");

        // Plugin startup logic
        Bukkit.getLogger().info("Hello World");
        Bukkit.getLogger().info("Registering PlayerListener...");

        //handle world border
        WorldBorderManager borderManager = new WorldBorderManager(this);
        GameStateChanger gameStateChanger = new GameStateChanger(this, borderManager);

        //register commands
        Objects.requireNonNull(getCommand("fly")).setExecutor(new Fly());
        Objects.requireNonNull(getCommand("menu")).setExecutor(new Menu(this));
        Objects.requireNonNull(getCommand("createenergyscore")).setExecutor(new CreateEnergyScore());
        Objects.requireNonNull(getCommand("startgame")).setExecutor(gameStateChanger);


        //enable Listeners
        getServer().getPluginManager().registerEvents(new WizardListener(this), this);
        getServer().getPluginManager().registerEvents(new FireballListener(this), this);
        getServer().getPluginManager().registerEvents(new WindRiderListener(this), this);
        getServer().getPluginManager().registerEvents(new GrapplingHookListener(), this);
        getServer().getPluginManager().registerEvents(new EnergyListener(this), this);
        getServer().getPluginManager().registerEvents(gameStateChanger, this);
        getServer().getPluginManager().registerEvents(new GiveStarterItems(this, gameStateChanger), this);

        getServer().getPluginManager().registerEvents(new MinerListener(this), this);
        getServer().getPluginManager().registerEvents(new BeastMasterListener(this), this);

        getServer().getPluginManager().registerEvents(new MinerAbility(), this);


        saveDefaultConfig(); // Ensure config is loaded
        worldDeleteManager = new WorldDeleteManager(this);
        worldDeleteManager.scheduleWorldDeletion("gameWorld"); // Deletes after full shutdown

        //disable world saving
        Objects.requireNonNull(Bukkit.getWorld("gameWorld")).setAutoSave(false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Shutting down");

        // Kick all players before unloading the world
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(ChatColor.RED + "Server is shutting down.");
        }

        if(worldDeleteManager != null){
            //worldDeleteManager.deleteWorld("gameWorld");
        }else {Bukkit.getLogger().info("World could not be deleted");}


    }


    public static PleiadesPVP getInstance() {
        return instance;
    }
}
