package fwoostybots.com.xosurvivalcore.Commands.impl;

import fwoostybots.com.xosurvivalcore.Commands.Command;
import fwoostybots.com.xosurvivalcore.Main;
import fwoostybots.com.xosurvivalcore.Utilities.Teleport;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WildCommand extends Command {

    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();


    public WildCommand() {
        super("wild", "Teleports you to the wild", List.of());
    }

    @Override
    public void runPlayer(@NotNull Player player, String[] args) {
        int cooldownTime = Main.getInstance().getConfig().getInt("cool-down-time");
        // Check if player is in HashMap
        if(cooldown.containsKey(player.getUniqueId())) {
            long secondsLeft = ((cooldown.get(player.getUniqueId())/1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            String cool_down_message = Main.getInstance().getConfig().getString("cool-down-message").replace("{seconds-left}", String.valueOf(secondsLeft));

            // Check if player is in cooling-off period
            if(secondsLeft > 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', cool_down_message));
                return;
            }
            // Remove from hashmap, teleport player
            else if (secondsLeft <= 0) {
                cooldown.remove(player.getUniqueId());
                teleport(args, player);
                return;
            }
        }
        else {
            // First time command usage, teleport player
            teleport(args, player);
            return;
        }
    }

    private void teleport(String[] args, Player player) {
        // Add cool-down to player
        cooldown.put(player.getUniqueId(), System.currentTimeMillis());

        // Check if the correct arguments are supplied
        if(args.length == 0) {
            if(player.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")
                    || player.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")) {

                String incorrectWorldMessage = Main.getInstance().getConfig().getString("incorrect-world-message");

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', incorrectWorldMessage));
                return;
            }
            else {
                // Determine a safe location
                Location randomLocation = Teleport.findSafeLocation(player);

                // Teleport the player
                player.teleport(randomLocation);

                // Notify the player
                String random_location = Main.getInstance().getConfig().getString("random-location");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', random_location));
                return;
            }
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage, /wild"));
            return;
        }
    }
}
