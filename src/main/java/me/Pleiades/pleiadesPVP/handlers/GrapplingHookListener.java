package me.Pleiades.pleiadesPVP.handlers;

import me.Pleiades.pleiadesPVP.abilities.GrapplingHook;
import me.Pleiades.pleiadesPVP.StartPackage.GrapplingHookItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class GrapplingHookListener implements Listener {

    @EventHandler
    public void onPlayerUseGrapplingHook(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        // Ensure the player is using the special "Grappling Hook"
        if (!heldItem.isSimilar(GrapplingHookItem.createGrapplingHook())) {
            return;
        }

        switch (event.getState()) {
            case FISHING: // When the player casts the fishing rod
                GrapplingHook.launchHook(event);
                break;

            case REEL_IN, IN_GROUND: // When the player retracts the line
                GrapplingHook.pullPlayer(event);
                break;

            default:
                break;
        }
    }
}
