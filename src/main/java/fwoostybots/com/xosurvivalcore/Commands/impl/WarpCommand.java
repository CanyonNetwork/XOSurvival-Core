package fwoostybots.com.xosurvivalcore.Commands.impl;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import fwoostybots.com.xosurvivalcore.Commands.Command;
import fwoostybots.com.xosurvivalcore.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WarpCommand extends Command {

    public WarpCommand() {
        super("warp", "Warps you to somewhere i dont really know", List.of("warp"));
    }

    @Override
    public void runPlayer(@NotNull Player player, String[] args) {
        // Prefix message
        String prefix_message = Main.getInstance().getConfig().getString("prefix-message");

        boolean packstatus = Main.getInstance().getResourcePackManager().getStatus(player.getUniqueId());
        // GUI used if they have the resource pack enabled
        if (packstatus) {
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
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the overworld"))));
            });

            GuiItem spawnItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("spawn"), 0.5, 100, 0.5, 0, 0);
                player.teleport(loc);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("spawn"))));
            });

            GuiItem endItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("world_the_end"), 100.5, 49, 0.5, 90, 0);
                player.teleport(loc);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the end"))));
            });

            GuiItem netherItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
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
            Gui gui = Gui.gui()
                    .title(Component.text("§d§lWarps"))
                    .rows(3)
                    .create();

            // GUI open event
            gui.setOpenGuiAction(event -> {
                // Something
            });

            // GUI click event
            GuiItem overworldItem = ItemBuilder.from(Material.GRASS_BLOCK).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("world"), 30.5, 162, 0.5, -90, 0);
                player.teleport(loc);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the overworld"))));
            });

            GuiItem spawnItem = ItemBuilder.from(Material.LILAC).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("spawn"), 0.5, 100, 0.5, 0, 0);
                player.teleport(loc);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("spawn"))));
            });

            GuiItem endItem = ItemBuilder.from(Material.END_STONE).asGuiItem(event -> {
                event.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("world_the_end"), 100.5, 49, 0.5, 90, 0);
                player.teleport(loc);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the end"))));
            });

            GuiItem netherItem = ItemBuilder.from(Material.NETHERRACK).asGuiItem(event -> {
                event.setCancelled(true);
                String warp_message = Main.getInstance().getConfig().getString("warp-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + warp_message, Placeholder.component("location", Component.text("the nether"))));
            });

            GuiItem fillBlock = ItemBuilder.from(Material.WHITE_STAINED_GLASS_PANE).asGuiItem(event -> {
                event.setCancelled(true);
            });

            // GUI close event
            gui.setCloseGuiAction(event -> {
                // Something
            });

            // Warping overworld item
            overworldItem.getItemStack();
            ItemStack item1 = overworldItem.getItemStack();
            ItemMeta meta1 = item1.getItemMeta();
            meta1.displayName(Component.text("§dOverworld Warp"));
            meta1.lore(List.of(Component.text("§eWarp to the overworld!")));

            item1.setItemMeta(meta1);

            gui.setItem(10, overworldItem);

            // Warping spawn item
            spawnItem.getItemStack();
            ItemStack item2 = spawnItem.getItemStack();
            ItemMeta meta2 = item2.getItemMeta();
            meta2.displayName(Component.text("§dSpawn Warp"));
            meta2.lore(List.of(Component.text("§eWarp to spawn!")));

            item2.setItemMeta(meta2);

            gui.setItem(12, spawnItem);

            // Warping end item
            endItem.getItemStack();
            ItemStack item3 = endItem.getItemStack();
            ItemMeta meta3 = item3.getItemMeta();
            meta3.displayName(Component.text("§dEnd Warp"));
            meta3.lore(List.of(Component.text("§eWarp to the end!")));

            item3.setItemMeta(meta3);

            gui.setItem(14, endItem);

            // Warping nether item
            netherItem.getItemStack();
            ItemStack item4 = netherItem.getItemStack();
            ItemMeta meta4 = item4.getItemMeta();
            meta4.displayName(Component.text("§dNether Warp"));
            meta4.lore(List.of(Component.text("§eWarp to the nether!")));

            item4.setItemMeta(meta4);

            gui.setItem(16, netherItem);


            // GUI fill block
            fillBlock.getItemStack();
            ItemStack item5 = fillBlock.getItemStack();
            ItemMeta meta5 = item5.getItemMeta();
            meta5.displayName(Component.text(""));

            item5.setItemMeta(meta5);

            gui.setItem(0, fillBlock);
            gui.setItem(1, fillBlock);
            gui.setItem(2, fillBlock);
            gui.setItem(3, fillBlock);
            gui.setItem(4, fillBlock);
            gui.setItem(5, fillBlock);
            gui.setItem(6, fillBlock);
            gui.setItem(7, fillBlock);
            gui.setItem(8, fillBlock);
            gui.setItem(9, fillBlock);
            gui.setItem(11, fillBlock);
            gui.setItem(13, fillBlock);
            gui.setItem(15, fillBlock);
            gui.setItem(17, fillBlock);
            gui.setItem(18, fillBlock);
            gui.setItem(19, fillBlock);
            gui.setItem(20, fillBlock);
            gui.setItem(21, fillBlock);
            gui.setItem(22, fillBlock);
            gui.setItem(23, fillBlock);
            gui.setItem(24, fillBlock);
            gui.setItem(25, fillBlock);
            gui.setItem(26, fillBlock);

            // Opening the completed GUI
            gui.open(player);
        }
    }
}
