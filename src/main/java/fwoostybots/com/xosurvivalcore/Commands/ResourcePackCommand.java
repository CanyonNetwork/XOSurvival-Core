package fwoostybots.com.xosurvivalcore.Commands;
// boolean test = resourcepackManager.getStatus(player.getUniqueId());

import java.util.List;

import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import fwoostybots.com.xosurvivalcore.Main;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ResourcePackCommand implements TabExecutor {
    private Main main;
    private final ResourcePackManager resourcepackManager;

    public ResourcePackCommand(Main main, ResourcePackManager resourcepackManager) {
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
        String resource_pack_status_message = main.getConfig().getString("resource-pack-status-message");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                Player target = Bukkit.getPlayer(args[0]);
                if (player.hasPermission("xo.texturepack.other")) {
                    if (target != null) {
                        boolean packstatus = resourcepackManager.getStatus(target.getUniqueId());
                        if (packstatus) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + resource_pack_status_message, Placeholder.component("player", Component.text(target.getName()))));
                            player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + resource_pack_status_message, Placeholder.component("player", target.displayName())));
                        } else {
                            target.kick(MiniMessage.miniMessage().deserialize("<gradient:#FF0000:#FF3DC5>To download the resource pack, click the server, then click \"edit\". \nLastly, \"enable\" the server resource packs!</gradient>"));
                        }
                    } else {
                        String invalid_player_message = main.getConfig().getString("invalid-player-message");
                        player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                    }
                } else {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("xo.texturepack.other"))));
                }
            } catch (Exception e) {
                boolean packstatus = resourcepackManager.getStatus(player.getUniqueId());
                }
        } else {
            String non_player_message = main.getConfig().getString("non-player-message");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(non_player_message));
        }
        return true;
    }
}
