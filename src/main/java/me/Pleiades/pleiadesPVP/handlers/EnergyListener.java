package me.Pleiades.pleiadesPVP.handlers;

import me.Pleiades.pleiadesPVP.PleiadesPVP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class EnergyListener implements Listener {
    private final PleiadesPVP pleiadesPVP;
    private final Set<UUID> sneakingPlayers = new HashSet<>();

    public EnergyListener(PleiadesPVP pleiadesPVP) {
        this.pleiadesPVP = pleiadesPVP;
        startScoreIncrementTask();
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();

        if (event.isSneaking()) {
            sneakingPlayers.add(playerID);
        } else {
            sneakingPlayers.remove(playerID);
        }
    }

    private void startScoreIncrementTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID playerId : new HashSet<>(sneakingPlayers)) { // Iterate over a copy to avoid concurrency issues
                    Player player = Bukkit.getPlayer(playerId);

                    if (player == null || !player.isOnline() || !player.isSneaking()) {
                        sneakingPlayers.remove(playerId); // Ensure the player is removed if they're not sneaking
                        continue;
                    }

                    // Increment the score on the "Energy" scoreboard objective
                    if (player.getScoreboard().getObjective("Energy") == null) {
                        continue;
                    }

                    int maxScore = 100;

                    int currentScore = Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                            .getScore(ChatColor.YELLOW + "Energy: ").getScore();

                    if (currentScore >= maxScore) {
                        continue;
                    }

                    Objects.requireNonNull(player.getScoreboard().getObjective("Energy"))
                            .getScore(ChatColor.YELLOW + "Energy: ").setScore(currentScore + 1);
                }
            }
        }.runTaskTimer(pleiadesPVP, 1L, 1L); // runs every 1 tick, (1 second is 20 ticks)
    }
}
