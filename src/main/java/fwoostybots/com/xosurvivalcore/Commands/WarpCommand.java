package fwoostybots.com.xosurvivalcore.Commands;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import fwoostybots.com.xosurvivalcore.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WarpCommand implements CommandExecutor  {
    private Main main;

    public WarpCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            // Prefix message
            String prefix_message = main.getConfig().getString("prefix-message");

            // Add the sender to the player attribute
            Player player = (Player) sender;

            // Creating the GUI
            Gui gui = Gui.gui()
                    .title(Component.text("§f七七七七七七七七ㇺ"))
                    .rows(6)
                    .create();

            // GUI open event
            gui.setOpenGuiAction(event -> {
                // Something
            });

            // GUI click event
            GuiItem overworldItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("world"), 30.5, 162, 0.5, -90, 0);
                player.teleport(loc);
                String warp_message = main.getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the overworld"))));
            });

            GuiItem spawnItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("spawn"), 0.5, 100, 0.5, 0, 0);
                player.teleport(loc);
                String warp_message = main.getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("spawn"))));
            });

            GuiItem endItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("world_the_end"), 100.5, 49, 0.5, 90, 0);
                player.teleport(loc);
                String warp_message = main.getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the end"))));
            });

            GuiItem netherItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                String warp_message = main.getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the nether"))));
            });

            // GUI close event
            gui.setCloseGuiAction(event -> {
                // Something
            });

            // Warping overworld item
            overworldItem.getItemStack();
            ItemStack item1 = overworldItem.getItemStack();
            ItemMeta meta1 = item1.getItemMeta();
            meta1.setCustomModelData(1010);
            meta1.displayName(Component.text("§dOverworld Warp"));
            meta1.lore(List.of(Component.text("§eWarp to the overworld!")));

            item1.setItemMeta(meta1);

            gui.setItem(19, overworldItem);
            gui.setItem(20, overworldItem);
            gui.setItem(28, overworldItem);
            gui.setItem(29, overworldItem);

            // Warping spawn item
            spawnItem.getItemStack();
            ItemStack item2 = spawnItem.getItemStack();
            ItemMeta meta2 = item2.getItemMeta();
            meta2.setCustomModelData(1010);
            meta2.displayName(Component.text("§dSpawn Warp"));
            meta2.lore(List.of(Component.text("§eWarp to spawn!")));

            item2.setItemMeta(meta2);

            gui.setItem(22, spawnItem);

            // Warping end item
            endItem.getItemStack();
            ItemStack item3 = endItem.getItemStack();
            ItemMeta meta3 = item3.getItemMeta();
            meta3.setCustomModelData(1010);
            meta3.displayName(Component.text("§dEnd Warp"));
            meta3.lore(List.of(Component.text("§eWarp to the end!")));

            item3.setItemMeta(meta3);

            gui.setItem(39, endItem);
            gui.setItem(40, endItem);
            gui.setItem(41, endItem);
            gui.setItem(48, endItem);
            gui.setItem(49, endItem);
            gui.setItem(50, endItem);

            // Warping nether item
            netherItem.getItemStack();
            ItemStack item4 = netherItem.getItemStack();
            ItemMeta meta4 = item4.getItemMeta();
            meta4.setCustomModelData(1010);
            meta4.displayName(Component.text("§dNether Warp"));
            meta4.lore(List.of(Component.text("§eWarp to the nether!")));

            item4.setItemMeta(meta4);

            gui.setItem(24, netherItem);
            gui.setItem(25, netherItem);
            gui.setItem(33, netherItem);
            gui.setItem(34, netherItem);

            // Opening the completed GUI
            gui.open(player);

        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}