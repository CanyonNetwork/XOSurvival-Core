package fwoostybots.com.xosurvivalcore.Commands;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import fwoostybots.com.xosurvivalcore.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WildGUI implements CommandExecutor  {
    private Main main;
    private final ResourcePackManager resourcepackManager;

    public WildGUI(Main main, ResourcePackManager resourcepackManager) {
        this.main = main;
        this.resourcepackManager = resourcepackManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            // Prefix message
            String prefix_message = main.getConfig().getString("prefix-message");

            // Add the sender to the player attribute
            Player player = (Player) sender;

            boolean packstatus = resourcepackManager.getStatus(player.getUniqueId());
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
                String warp_message = main.getConfig().getString("warp-message");
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

        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}