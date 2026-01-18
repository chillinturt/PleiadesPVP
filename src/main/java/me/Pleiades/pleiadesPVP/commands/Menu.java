package me.Pleiades.pleiadesPVP.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.Pleiades.pleiadesPVP.PleiadesPVP;
import me.Pleiades.pleiadesPVP.abilities.EnergyScore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Listener, CommandExecutor {
    private static final String INV_NAME = "Server Selector";
    private final ItemStack wizardWand = new ItemStack(Material.BLAZE_ROD);
    private final PleiadesPVP plugin;

    public Menu(PleiadesPVP plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        // Register the Velocity messaging channel
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "velocity:player");
    }

    @EventHandler
    public void onServerMenuClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(INV_NAME)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if (slot == 0) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("wizard");

            Inventory inv = player.getInventory();
            inv.clear();
            inv.setItem(1, wizardWand);
        }

        if (slot == 1) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("fireball");

            player.sendMessage("Fireball power selected!");
        }

        if (slot == 2) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("wind rider");

            player.sendMessage("Wind Rider power selected!");
        }

        if (slot == 3) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("grappler");

            player.sendMessage("Grappler power selected!");
        }

        if (slot == 4) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("snow miser");

            player.sendMessage("Snow Miser power selected!");
        }

        if (slot == 5) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("heat miser");

            player.sendMessage("Heat Miser power selected!");
        }

        if (slot == 6) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("beast master");

            player.sendMessage("Beast Master power selected!");
        }

        if (slot == 7) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("berserker");

            player.sendMessage("Berserker power selected!");
        }

        if (slot == 8) {
            EnergyScore.CreateEnergyScore(player);

            for (String tag : player.getScoreboardTags()) {
                player.removeScoreboardTag(tag);
            }

            player.addScoreboardTag("miner");

            player.sendMessage("Miner power selected!");
        }

        event.setCancelled(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;
        openMenu(player);
        return true;
    }

    public static void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9 * 3, INV_NAME);

        inv.setItem(0, getItem(new ItemStack(Material.GRASS_BLOCK), "&9Wizard", "&aClick to Select", "&aOP KIT!"));
        inv.setItem(1, getItem(new ItemStack(Material.FIRE_CHARGE), "&9Fireball Launcher", "&aClick to Select", "&aExplode things"));
        inv.setItem(2, getItem(new ItemStack(Material.WIND_CHARGE), "&9Wind Rider", "&aClick to Select", "&aBursts of speed!"));
        inv.setItem(3, getItem(new ItemStack(Material.LEAD), "&9Grappler", "&aClick to Select", "&aGrappling hook!"));
        inv.setItem(4, getItem(new ItemStack(Material.SNOW_BLOCK), "&9Snow Miser", "&aClick to Select", "&aHe's MR. snow!"));
        inv.setItem(5, getItem(new ItemStack(Material.MAGMA_BLOCK), "&9Heat Miser", "&aClick to Select", "&aHe's MR. sun!"));
        inv.setItem(6, getItem(new ItemStack(Material.WOLF_SPAWN_EGG), "&9Beast Master", "&aClick to Select", "&aSummon creatures!"));
        inv.setItem(7, getItem(new ItemStack(Material.IRON_AXE), "&9Berserker", "&aClick to Select", "&aAAAAAAA!"));

        inv.setItem(8, getItem(new ItemStack(Material.IRON_PICKAXE), "&9Miner", "&aClick to Select", "&aHeigh-Ho!"));


        inv.setItem(15, getItem(new ItemStack(Material.COMMAND_BLOCK), "&9TEST", "&aClick to Join", "&aMy testing world"));

        player.openInventory(inv);
    }

    private static ItemStack getItem(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);
        return item;
    }

    private void connectToServer(Player player, String serverName) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArrayOutputStream);

            // Write the server name to the plugin message
            out.writeUTF("Connect");
            out.writeUTF(serverName);

            // Send the plugin message
            player.sendPluginMessage(plugin, "velocity:player", byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "An error occurred while connecting to the server.");
            e.printStackTrace();
        }
    }
}
