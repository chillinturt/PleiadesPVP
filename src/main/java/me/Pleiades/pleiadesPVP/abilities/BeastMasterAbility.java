package me.Pleiades.pleiadesPVP.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class BeastMasterAbility {

    public static final HashMap<UUID, Horse> playerHorses = new HashMap<>();
    public static final HashMap<UUID, HashMap<UUID, Wolf>> playerDogs = new HashMap<>();

    public static void spawnHorse(Player player) {

        UUID playerId = player.getUniqueId();

        // Check if the player already has a horse
        if (playerHorses.containsKey(playerId)) {
            Horse oldHorse = playerHorses.get(playerId);
            if (oldHorse != null && !oldHorse.isDead()) {
                oldHorse.remove(); // Kill the old horse
            }
            playerHorses.remove(playerId);
            player.sendMessage("§cYour old horse has been replaced.");
        }

        // Check if the player already has a horse
        if (playerHorses.containsKey(playerId)) {
            player.sendMessage("§cYou can only have one horse!");
            return;
        }

        // Spawn the horse
        Location spawnLocation = player.getLocation();
        Horse horse = player.getWorld().spawn(spawnLocation, Horse.class);

        // Set fixed horse attributes
        double fixedHealth = 30.0;
        double fixedJumpStrength = 0.7;
        double fixedSpeed = 0.25; // Default horse speed varies, so we set it explicitly

        // **Ensure the horse is fully tamed**
        horse.setTamed(true);
        horse.setOwner(player);
        horse.setCustomName(player.getName() + "'s Horse");
        horse.setDomestication(horse.getMaxDomestication()); // **Force full taming**
        horse.setBreed(true); // Ensure it can breed like a tamed horse

        // Set attributes
        Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(fixedHealth);
        horse.setHealth(fixedHealth);
        horse.setJumpStrength(fixedJumpStrength);
        Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(fixedSpeed);

        // Give the horse a saddle
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        // Store in the HashMap
        playerHorses.put(playerId, horse);
        player.sendMessage("§aYou have summoned a fully tamed horse!");

        // Optional: Auto-mount the player on the horse
        //horse.addPassenger(player);
    }


    public static void spawnDog(Player player) {
        UUID playerId = player.getUniqueId();

        // Ensure player has a HashMap for tracking dogs
        playerDogs.putIfAbsent(playerId, new HashMap<>());
        HashMap<UUID, Wolf> dogs = playerDogs.get(playerId);

        // Check if the player already has 4 dogs
        if (dogs.size() >= 4) {
            player.sendMessage("§cYou can only have up to 4 dogs!");
            return;
        }

        // Spawn and tame the dog
        Location spawnLocation = player.getLocation();
        Wolf wolf = player.getWorld().spawn(spawnLocation, Wolf.class);
        wolf.setTamed(true);
        wolf.setOwner(player);
        wolf.setCustomName(player.getName() + "'s Dog");

        // Store in the HashMap
        dogs.put(wolf.getUniqueId(), wolf);
        player.sendMessage("§aYou have summoned a tamed dog!");
    }

    public static void handleEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Check if the entity is a horse
        if (entity instanceof Horse) {
            UUID horseUUID = entity.getUniqueId();

            // Find and remove the horse from the map
            playerHorses.entrySet().removeIf(entry -> entry.getValue().getUniqueId().equals(horseUUID));

            System.out.println("Horse removed from tracking.");
        }

        // Check if the entity is a wolf
        if (entity instanceof Wolf) {
            for (UUID playerId : playerDogs.keySet()) {
                if (playerDogs.get(playerId).containsKey(entity.getUniqueId())) {
                    playerDogs.get(playerId).remove(entity.getUniqueId());

                    // If no dogs remain, remove the player entry
                    if (playerDogs.get(playerId).isEmpty()) {
                        playerDogs.remove(playerId);
                    }
                    return;
                }
            }
        }
    }

}
