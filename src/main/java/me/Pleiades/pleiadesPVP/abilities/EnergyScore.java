package me.Pleiades.pleiadesPVP.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class EnergyScore {


    public static void CreateEnergyScore(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        if(manager == null) {
            player.sendMessage(ChatColor.RED + "Scoreboard manager is not available!");
            return;
        }



        Scoreboard scoreboard = manager.getNewScoreboard();

        if (scoreboard.getObjective("Energy") != null) {
            scoreboard.getObjective("Energy").unregister(); // Remove existing objective
        }

        Objective energy = scoreboard.registerNewObjective("Energy", "dummy", "Example Board");
        energy.setDisplaySlot(DisplaySlot.SIDEBAR);
        energy.setDisplayName("Energy Board");

        System.out.println("Energy score created");

        Score score1 = energy.getScore(ChatColor.YELLOW + "Energy: ");
        score1.setScore(0);

        Score score2 = energy.getScore(ChatColor.YELLOW + "Mode: ");
        score2.setScore(0);

        player.setScoreboard(scoreboard);
    }


}