package fwoostybots.com.xosurvivalcore.Commands;

import java.util.List;
import java.util.Map;

import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import fwoostybots.com.xosurvivalcore.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GodCommand implements TabExecutor {
    private Main main;
    private final ResourcePackManager resourcepackManager;

    public GodCommand(Main main, ResourcePackManager resourcepackManager) {
        this.main = main;
        this.resourcepackManager = resourcepackManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix_message = main.getConfig().getString("prefix-message");
        String no_permission_message = main.getConfig().getString("no-permission-message");
        if (sender instanceof Player) {
            if (sender.hasPermission("xo.god")) {
                Player player = (Player) sender;
                try {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (sender.hasPermission("xo.god.other")) {
                        if (target != null) {
                            if (target.isInvulnerable()) {
                                target.setInvulnerable(false);
                                String god_disabled_other_message = main.getConfig().getString("god-disabled-other-message");
                                String god_disabled_message = main.getConfig().getString("god-disabled-message");
                                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + god_disabled_other_message, Placeholder.component("player", target.displayName())));
                                target.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + god_disabled_message));
                            } else {
                                target.setInvulnerable(true);
                                String god_enabled_other_message = main.getConfig().getString("god-enabled-other-message");
                                String god_enabled_message = main.getConfig().getString("god-enabled-message");
                                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + god_enabled_other_message, Placeholder.component("player", target.displayName())));
                                target.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + god_enabled_message));
                            }
                        } else {
                            String invalid_player_message = main.getConfig().getString("invalid-player-message");
                            sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                        }
                    } else {
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("xo.god.other"))));
                    }
                } catch (Exception e) {
                    if (player.isInvulnerable()) {
                        player.setInvulnerable(false);
                        boolean test = resourcepackManager.getStatus(player.getUniqueId());
                        String god_disabled_message = main.getConfig().getString("god-disabled-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + god_disabled_message));
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(String.valueOf(test)));
                    } else {
                        player.setInvulnerable(true);
                        String god_enabled_message = main.getConfig().getString("god-enabled-message");
                        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + god_enabled_message));
                    }
                }
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("xo.god"))));
            }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}