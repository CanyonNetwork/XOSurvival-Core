package fwoostybots.com.xosurvivalcore.Commands;

import java.util.List;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import fwoostybots.com.xosurvivalcore.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class GUICommand implements TabExecutor {
    private Main main;

    public GUICommand(Main main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            // Add the sender to the player attribute
            Player player = (Player) sender;

            // Creating the GUI
            Gui gui = Gui.gui()
                    .title(Component.text("GUI Title!"))
                    .rows(6)
                    .create();

            // GUI open event
            gui.setOpenGuiAction(event -> {
                // Handle your open action
            });

            // GUI click event
            GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem(event -> {
                event.setCancelled(true);
                String test_message = main.getConfig().getString("test-message");
                player.sendMessage(MiniMessage.miniMessage().deserialize(test_message));
            });

            // GUI close event
            gui.setCloseGuiAction(event -> {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 0.7F, 1.5F);
            });

            // Adding items and opening the GUI
            gui.setItem(10, guiItem);
            gui.open(player);

        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}