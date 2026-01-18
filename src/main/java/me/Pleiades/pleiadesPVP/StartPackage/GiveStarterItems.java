package me.Pleiades.pleiadesPVP.StartPackage;

import me.Pleiades.pleiadesPVP.PleiadesPVP;
import me.Pleiades.pleiadesPVP.World.WorldBorderManager;
import me.Pleiades.pleiadesPVP.commands.Menu;
import me.Pleiades.pleiadesPVP.handlers.GameStateChanger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class GiveStarterItems implements Listener {
    private final PleiadesPVP plugin;
    private final GameStateChanger gameStateChanger;

    private final ItemStack menuSelectItem = new ItemStack(Material.CHEST);
    private final ItemStack returnToHubItem = new ItemStack(Material.CLOCK);

    public GiveStarterItems(PleiadesPVP plugin, GameStateChanger gameStateChanger){
        this.plugin = plugin;
        this.gameStateChanger = gameStateChanger;

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Inventory inv = player.getInventory();
        inv.clear();

        inv.setItem(0, menuSelectItem);
        inv.setItem(8, returnToHubItem);
    }


    // This class will handle what happens when the starter items are pressed, performing commands to return to hub,
    // select a kit, etc
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();


        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(Objects.equals(gameStateChanger.getGameState(), "play")){
                return;
            }
            if(player.getInventory().getItemInMainHand().isSimilar(menuSelectItem)){
                Menu.openMenu(player);
            }

            if(player.getInventory().getItemInMainHand().isSimilar(returnToHubItem)){
                player.performCommand("/server lobby");
            }

            event.setCancelled(true);
        }
    }
}