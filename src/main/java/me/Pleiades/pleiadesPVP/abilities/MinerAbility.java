package me.Pleiades.pleiadesPVP.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;
import java.util.Set;

public class MinerAbility implements Listener {

    // Set of ore blocks that should drop double resources
    private static final Set<Material> ORES = EnumSet.of(
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE
    );

    public static void AddEffects(Player player) {

        // Haste 10 (Mining Speed) for 30 seconds
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 10 * 20, 15)); // 9 because level 10 is index 9

    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event) {
        Bukkit.getLogger().info("Block Broken");
        Player player = event.getPlayer();

        if(!player.getScoreboardTags().contains("miner")){
            return;
        }
        Bukkit.getLogger().info("Player is a miner");

        Material blockType = event.getBlock().getType();

        // Check if the broken block is an ore
        if (ORES.contains(blockType)) {
            // Cancel the default drop to prevent duplication
            event.setDropItems(false);

            // Determine the correct drop
            ItemStack drop;
            switch (blockType) {
                case COAL_ORE, DEEPSLATE_COAL_ORE -> drop = new ItemStack(Material.COAL, 2);
                case IRON_ORE, DEEPSLATE_IRON_ORE -> drop = new ItemStack(Material.RAW_IRON, 2);
                case GOLD_ORE, DEEPSLATE_GOLD_ORE -> drop = new ItemStack(Material.RAW_GOLD, 2);
                case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> drop = new ItemStack(Material.DIAMOND, 2);
                case LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> drop = new ItemStack(Material.LAPIS_LAZULI, 8); // Lapis usually drops more
                case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> drop = new ItemStack(Material.REDSTONE, 8); // Redstone drops more
                case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> drop = new ItemStack(Material.EMERALD, 2);
                default -> drop = null;
            }

            // Drop the extra resources at the block's location
            if (drop != null) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
            }
        }
    }
}
