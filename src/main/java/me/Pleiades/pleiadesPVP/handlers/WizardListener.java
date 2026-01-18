package me.Pleiades.pleiadesPVP.handlers;

import me.Pleiades.pleiadesPVP.PleiadesPVP;
import me.Pleiades.pleiadesPVP.abilities.SpawnFireball;
import me.Pleiades.pleiadesPVP.abilities.SpawnWindCharge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class WizardListener implements Listener {

    private final ItemStack item = new ItemStack(Material.BLAZE_ROD);

    public WizardListener(PleiadesPVP pleiadesPVP){
        //Bukkit.getPluginManager().registerEvents(this, pleiadesPVP);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Inventory inv = player.getInventory();

        inv.setItem(1, item);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(!player.getScoreboardTags().contains("wizard")){
            return;
        }

        if (!player.getInventory().getItemInMainHand().isSimilar(item)){
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return; // Only handle main-hand interactions
        }

        int currentEnergy = Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                .getScore(ChatColor.YELLOW + "Energy: ").getScore();

        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){

            // Increment the score on the "Energy" scoreboard objective
            if (player.getScoreboard().getObjective("Energy") == null) {
                return;
            }

            int currentScore = Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                    .getScore(ChatColor.YELLOW + "Mode: ").getScore();

            if(currentScore < 1){
                Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                        .getScore(ChatColor.YELLOW + "Mode: ").setScore(currentScore + 1);
            }else {
                Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                        .getScore(ChatColor.YELLOW + "Mode: ").setScore(0);
            }



        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                        .getScore(ChatColor.YELLOW + "Mode: ").getScore() == 0){

                    if(currentEnergy < 10) {
                        return;
                    }

                    Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                            .getScore(ChatColor.YELLOW + "Energy: ").setScore(currentEnergy - 10);

                    SpawnFireball.SummonFireball(player);
                    System.out.println("Fireball spawned");
                }

                if(Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                        .getScore(ChatColor.YELLOW + "Mode: ").getScore() == 1){
                    if(currentEnergy < 5){
                        return;
                    }

                    Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                            .getScore(ChatColor.YELLOW + "Energy: ").setScore(currentEnergy - 5);

                    SpawnWindCharge.SummonWindCharge(player);
                    System.out.println("Wind Charge spawned");
                }
            }
    }

}