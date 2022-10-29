package fwoostybots.com.xosurvivalcore.Commands.impl;

import fwoostybots.com.xosurvivalcore.Commands.Command;
import fwoostybots.com.xosurvivalcore.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourcePackCommand extends Command {

    public ResourcePackCommand() {
        super("resourcepack", "I dont know what this command does", List.of());
    }

    @Override
    public void runPlayer(@NotNull Player player, String[] args) {
        String prefix_message = Main.getInstance().getConfig().getString("prefix-message");
        String no_permission_message = Main.getInstance().getConfig().getString("no-permission-message");
        String resource_pack_status_message = Main.getInstance().getConfig().getString("resource-pack-status-message");

        try {
            Player target = Bukkit.getPlayer(args[0]); // No null check, will error if the player isnt provided. Nice one @Someone
            if (player.hasPermission("xo.texturepack.other")) {
                if (target != null) {
                    boolean packstatus = Main.getInstance().getResourcePackManager().getStatus(target.getUniqueId());
                    if (packstatus) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + resource_pack_status_message, Placeholder.component("player", Component.text(target.getName()))));
                    }
                    else {
                        target.kick(MiniMessage.miniMessage().deserialize("<gradient:#FF0000:#FF3DC5>To download the resource pack, click the server, then click \"edit\". \nLastly, \"enable\" the server resource packs!</gradient>"));
                    }
                }
                else {
                    String invalid_player_message = Main.getInstance().getConfig().getString("invalid-player-message");
                    player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + invalid_player_message));
                }
            }
            else {
                player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + no_permission_message, Placeholder.component("permission", Component.text("xo.texturepack.other"))));
            }
        }
        catch (Exception e) {
            boolean packstatus = Main.getInstance().getResourcePackManager().getStatus(player.getUniqueId());
        }

    }
}
