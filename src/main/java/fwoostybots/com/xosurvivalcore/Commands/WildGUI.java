package fwoostybots.com.xosurvivalcore.Commands;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import fwoostybots.com.xosurvivalcore.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WildGUI implements CommandExecutor  {
    private Main main;

    public WildGUI(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            // Add the sender to the player attribute
            Player player = (Player) sender;

            // Creating the GUI
            Gui gui = Gui.gui()
                    .title(Component.text("§f七七七七七七七七三"))
                    .rows(6)
                    .create();

            // GUI open event
            gui.setOpenGuiAction(event -> {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 0.5F, 1.5F);
            });

            // GUI click event
            GuiItem overworldItem = ItemBuilder.from(Material.MAP).asGuiItem(event -> {
                event.setCancelled(true);
                String test_message = main.getConfig().getString("test-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(test_message));
            });

            // GUI close event
            gui.setCloseGuiAction(event -> {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1.5F);
            });

            // Warping overworld item
            overworldItem.getItemStack();
            ItemStack item = overworldItem.getItemStack();
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(1010);
            meta.displayName(Component.text("§dOverworld Warp"));
            meta.lore(List.of(Component.text("§eWarp to the overworld!")));

            item.setItemMeta(meta);

            gui.setItem(19, overworldItem);
            gui.setItem(20, overworldItem);
            gui.setItem(28, overworldItem);
            gui.setItem(29, overworldItem);

            // Opening the completed GUI
            gui.open(player);

        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}