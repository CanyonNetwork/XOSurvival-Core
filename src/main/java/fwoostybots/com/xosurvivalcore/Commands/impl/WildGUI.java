package fwoostybots.com.xosurvivalcore.Commands.impl;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import fwoostybots.com.xosurvivalcore.Commands.Command;
import fwoostybots.com.xosurvivalcore.Main;
import fwoostybots.com.xosurvivalcore.Utilities.Teleport;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WildGUI extends Command {

    public WildGUI() {
        super("wild", "Opens the Wild GUI", List.of("warpmesomewhere"));
    }

    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();

    @Override
    public void runPlayer(@NotNull Player player, String[] args) {
        // Prefix message
        String prefix_message = Main.getInstance().getConfig().getString("prefix-message");

        // Creating the GUI
        Gui gui = Gui.gui()
                .title(Component.text("§b§lWild"))
                .rows(1)
                .create();

        // GUI open event
        gui.setOpenGuiAction(event -> {
            // Something
        });

        // GUI click event
        GuiItem customworldItem = ItemBuilder.from(Material.FLOWERING_AZALEA_LEAVES).asGuiItem(event -> {
            event.setCancelled(true);
            int cooldownTime = Main.getInstance().getConfig().getInt("cool-down-time");
            World world = Bukkit.getWorld("world");
            if (cooldown.containsKey(player.getUniqueId())) {
                long secondsLeft = ((cooldown.get(player.getUniqueId())/1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
                String cool_down_message = Main.getInstance().getConfig().getString("cool-down-message").replace("{seconds-left}", String.valueOf(secondsLeft));
                if (secondsLeft > 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cool_down_message));
                    return;
                }
                else if (secondsLeft <= 0) {
                    cooldown.remove(player.getUniqueId());
                    teleport(args, player, world);
                    return;
                }
            }
            else {
                teleport(args, player, world);
                return;
            }
        });

        GuiItem overworldItem = ItemBuilder.from(Material.GRASS_BLOCK).asGuiItem(event -> {
            event.setCancelled(true);
            Location loc = new Location(Bukkit.getWorld("world"), 30.5, 162, 0.5, -90, 0);
            player.teleport(loc);
            String warp_message = Main.getInstance().getConfig().getString("warp-message");
            player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the overworld"))));
        });

        // GUI close event
        gui.setCloseGuiAction(event -> {
            // Something
        });

        // Warping custom world item
        customworldItem.getItemStack();
        ItemStack item1 = customworldItem.getItemStack();
        ItemMeta meta1 = item1.getItemMeta();
        meta1.displayName(Component.text("§dCustom World"));
        meta1.lore(List.of(Component.text("§eGo to the wilderness of the custom world!")));

        item1.setItemMeta(meta1);

        gui.setItem(0, customworldItem);

        // Warping custom world item
        overworldItem.getItemStack();
        ItemStack item2 = overworldItem.getItemStack();
        ItemMeta meta2 = item2.getItemMeta();
        meta2.displayName(Component.text("§aOverworld"));
        meta2.lore(List.of(Component.text("§eGo to the wilderness of the overworld!")));

        item2.setItemMeta(meta2);

        gui.setItem(4, overworldItem);

        // Opening the completed GUI
        gui.open(player);
    }
    private void teleport(String[] args, Player player, World world) {
        // Add cool-down to player
        cooldown.put(player.getUniqueId(), System.currentTimeMillis());

        // Check if the correct arguments are supplied
        if (args.length == 0) {
            if (player.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")
                    || player.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")) {

                String incorrectWorldMessage = Main.getInstance().getConfig().getString("incorrect-world-message");

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', incorrectWorldMessage));
                return;
            }
            else {
                // Determine a safe location
                Location randomLocation = Teleport.findSafeLocation(player, world);

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
