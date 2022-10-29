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

public class WildGUI extends Command {

    public WildGUI() {
        super("wild", "Opens the Wild GUI", List.of("warpmesomewhere"));
    }

    @Override
    public void runPlayer(@NotNull Player player, String[] args) {
        // Prefix message
        String prefix_message = Main.getInstance().getConfig().getString("prefix-message");

        // Creating the GUI
        Gui gui = Gui.gui()
                .title(Component.text("§6§lWild"))
                .rows(1)
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

        gui.setItem(0, overworldItem);
        gui.setItem(1, overworldItem);
        gui.setItem(2, overworldItem);
        gui.setItem(3, overworldItem);

        // Opening the completed GUI
        gui.open(player);
    }
}
