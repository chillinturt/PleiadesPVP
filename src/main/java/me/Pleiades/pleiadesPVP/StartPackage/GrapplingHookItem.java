package me.Pleiades.pleiadesPVP.StartPackage;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GrapplingHookItem {

    public static ItemStack createGrapplingHook() {
        ItemStack grapplingHook = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = grapplingHook.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "Grappling Hook");
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.LURE, 1, true); // Just to make it look special
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            grapplingHook.setItemMeta(meta);
        }

        return grapplingHook;
    }

    // 🏆 NEW FUNCTION: Gives the player the Grappling Hook
    public static void giveGrapplingHook(Player player) {
        ItemStack grapplingHook = createGrapplingHook();
        player.getInventory().addItem(grapplingHook);
        player.sendMessage(ChatColor.GREEN + "You have received a " + ChatColor.AQUA + "Grappling Hook!");
    }
}
