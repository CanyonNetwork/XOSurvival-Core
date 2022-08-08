package fwoostybots.com.xosurvivalcore.Commands;

import fwoostybots.com.xosurvivalcore.Main;
import fwoostybots.com.xosurvivalcore.Utilities.Teleport;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class WildCommand implements CommandExecutor {
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private final Main main;

    public WildCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            int cooldownTime = main.getConfig().getInt("cool-down-time");
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase("wild")) {
                // Check if player is in HashMap
                if(cooldown.containsKey(player.getUniqueId())) {
                    long secondsLeft = ((cooldown.get(player.getUniqueId())/1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
                    String cool_down_message = main.getConfig().getString("cool-down-message").replace("{seconds-left}", String.valueOf(secondsLeft));

                    // Check if player is in cooling-off period
                    if(secondsLeft > 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', cool_down_message));
                        return true;
                    }
                    // Remove from hashmap, teleport player
                    else if (secondsLeft <= 0) {
                        cooldown.remove(player.getUniqueId());
                        return teleport(args, player);
                    }
                }
                else {
                    // First time command usage, teleport player
                    return teleport(args, player);
                }
            }
        }
        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to be a player to execute this command."));
            return true;
        }

        return false;
    }

    private boolean teleport(String[] args, Player player) {
        // Add cool-down to player
        cooldown.put(player.getUniqueId(), System.currentTimeMillis());

        // Check if the correct arguments are supplied
        if(args.length == 0) {
            if(player.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")
                    || player.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")) {

                String incorrectWorldMessage = main.getConfig().getString("incorrect-world-message");

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', incorrectWorldMessage));
                return true;
            }
            else {
                // Determine a safe location
                Location randomLocation = Teleport.findSafeLocation(player);

                // Teleport the player
                player.teleport(randomLocation);

                // Notify the player
                String random_location = main.getConfig().getString("random-location");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', random_location));
                return true;
            }
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage, /wild"));
            return true;
        }
    }
}
