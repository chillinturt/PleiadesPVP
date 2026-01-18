package me.Pleiades.pleiadesPVP.commands;

import me.Pleiades.pleiadesPVP.abilities.EnergyScore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateEnergyScore implements CommandExecutor {

    private final EnergyScore energyScore = new EnergyScore();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if (!(sender instanceof Player player)){
            sender.sendMessage("Only a player can run this command.");
            return true;
        }

        energyScore.CreateEnergyScore(player);
        sender.sendMessage("Energy score creation triggered...");

        return true;
    }
}
