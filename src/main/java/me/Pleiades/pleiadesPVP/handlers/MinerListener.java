package me.Pleiades.pleiadesPVP.handlers;

import me.Pleiades.pleiadesPVP.PleiadesPVP;
import me.Pleiades.pleiadesPVP.abilities.MinerAbility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MinerListener implements Listener {

    public MinerListener(PleiadesPVP pleiadesPVP){
        //Bukkit.getPluginManager().registerEvents(this, pleiadesPVP);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(!player.getScoreboardTags().contains("miner")){
            return;
        }

        // Check if the item is not a pickaxe
        if (itemInHand.getType() != Material.WOODEN_PICKAXE &&
                itemInHand.getType() != Material.STONE_PICKAXE &&
                itemInHand.getType() != Material.IRON_PICKAXE &&
                itemInHand.getType() != Material.GOLDEN_PICKAXE &&
                itemInHand.getType() != Material.DIAMOND_PICKAXE &&
                itemInHand.getType() != Material.NETHERITE_PICKAXE) {
            return; // Exit if the item is not a pickaxe
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return; // Only handle main-hand interactions
        }

        int currentEnergy = Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                .getScore(ChatColor.YELLOW + "Energy: ").getScore();

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                    .getScore(ChatColor.YELLOW + "Mode: ").getScore() == 0){

                if(currentEnergy < 100) {
                    return;
                }

                Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                        .getScore(ChatColor.YELLOW + "Energy: ").setScore(currentEnergy - 100);

                MinerAbility.AddEffects(player);
            }

        }

    }

}