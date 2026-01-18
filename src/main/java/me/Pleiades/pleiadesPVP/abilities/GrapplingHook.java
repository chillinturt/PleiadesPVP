package me.Pleiades.pleiadesPVP.abilities;

import me.Pleiades.pleiadesPVP.StartPackage.GrapplingHookItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class GrapplingHook {

    private static final HashMap<UUID, FishHook> activeHooks = new HashMap<>();
    private static final HashMap<UUID, Location> hookLocations = new HashMap<>();

    public static void launchHook(PlayerFishEvent event) {
        Player player = event.getPlayer();

        // Ensure the player is using the special "Grappling Hook"
        if (!isHoldingGrapplingHook(player)) {
            return;
        }

        FishHook hook = event.getHook();
        activeHooks.put(player.getUniqueId(), hook);

        // Keep checking the hook's position until it lands on a block
        new BukkitRunnable() {
            @Override
            public void run() {
                if (hook.isDead() || !hook.isValid()) {
                    cancel(); // Stop checking if the hook is gone
                    return;
                }

                // Get the hook's current location
                Location hookLocation = hook.getLocation();

                // Check if the hook is attached to a block in any direction
                if (isAttachedToBlock(hookLocation)) {
                    hookLocations.put(player.getUniqueId(), hookLocation);

                    // 🛑 STOP THE HOOK FROM MOVING
                    hook.setGravity(false);
                    hook.setVelocity(new Vector(0, 0, 0));
                    hook.teleport(hookLocation); // Ensure the hook stays exactly where it landed

                    // 🔄 Keep resetting velocity to prevent physics updates
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!hook.isValid()) {
                                cancel();
                                return;
                            }
                            hook.setGravity(false);
                            hook.setVelocity(new Vector(0, 0, 0)); // Continuously stop movement
                            hook.teleport(hookLocation); // Keep it locked in place
                        }
                    }.runTaskTimer(Bukkit.getPluginManager().getPlugin("PleiadesPVP"), 0L, 2L);

                    player.sendMessage("§aGrappling hook attached!"); // Debug message
                    cancel(); // Stop checking after it lands
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("PleiadesPVP"), 0L, 2L);
    }

    public static void pullPlayer(PlayerFishEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Ensure the player is using the special "Grappling Hook"
        if (!isHoldingGrapplingHook(player)) {
            return;
        }

        // Ensure the player has an active hook location
        if (!hookLocations.containsKey(playerId)) {
            return;
        }

        Location hookLocation = hookLocations.get(playerId);
        hookLocations.remove(playerId); // Remove hook tracking

        // Remove the visual hook entity
        if (activeHooks.containsKey(playerId)) {
            activeHooks.get(playerId).remove();
            activeHooks.remove(playerId);
        }

        // Calculate the pull direction
        Vector pullVector = hookLocation.toVector().subtract(player.getLocation().toVector()).normalize().multiply(2);

        // Apply velocity to the player
        player.setVelocity(pullVector);
        player.sendMessage("§aPulled towards hook!"); // Debug message
    }

    private static boolean isAttachedToBlock(Location loc) {
        return isSolidBlock(loc.clone().add(0, -0.3, 0)) || // Below (floor)
                isSolidBlock(loc.clone().add(0, 0.3, 0))  || // Above (ceiling)
                isSolidBlock(loc.clone().add(0.3, 0, 0))  || // East (side)
                isSolidBlock(loc.clone().add(-0.3, 0, 0)) || // West (side)
                isSolidBlock(loc.clone().add(0, 0, 0.3))  || // South (side)
                isSolidBlock(loc.clone().add(0, 0, -0.3));   // North (side)
    }

    private static boolean isSolidBlock(Location loc) {
        return loc.getBlock().getType().isSolid();
    }

    private static boolean isHoldingGrapplingHook(Player player) {
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        return heldItem.hasItemMeta() &&
                heldItem.getItemMeta().hasDisplayName() &&
                heldItem.getItemMeta().getDisplayName().equals("§bGrappling Hook");
    }
}
