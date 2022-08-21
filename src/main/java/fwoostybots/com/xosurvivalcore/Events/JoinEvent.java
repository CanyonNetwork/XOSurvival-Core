package fwoostybots.com.xosurvivalcore.Events;

import fwoostybots.com.xosurvivalcore.Main;
import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import net.luckperms.api.LuckPerms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JoinEvent implements Listener {

    private Main main;
    private final ResourcePackManager resourcepackManager;
    private final LuckPerms luckPerms;

    public JoinEvent(Main main, ResourcePackManager resourcepackManager, LuckPerms luckPerms) {
        this.main = main;
        this.resourcepackManager = resourcepackManager;
        this.luckPerms = luckPerms;
    }

    // resourcePackStatus map
    Map<UUID, Boolean> resourcePackStatus = new HashMap<>();

    // Join event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setResourcePack("https://www.dropbox.com/s/rieh4n8tvkjh1gw/dd.zip?dl=1");
        String join_message = main.getConfig().getString("join-message");
        String resourcepack_join_message = main.getConfig().getString("resourcepack-join-message");
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        String prefix = user.getCachedData().getMetaData().getPrefix();
        for (Player e : Bukkit.getOnlinePlayers()) {
            try {
                boolean packstatus = resourcepackManager.getStatus(e.getUniqueId());
                // Message sent if they have the resource pack enabled
                if (packstatus) {
                    e.sendMessage(ChatColor.translateAlternateColorCodes('&', resourcepack_join_message.replaceAll("<player>", player.getName()).replaceAll("<prefix>", prefix)));
                    // Message sent if they don't have the resource pack enabled
                } else {
                    e.sendMessage(ChatColor.translateAlternateColorCodes('&', join_message.replaceAll("<player>", player.getName()).replaceAll("<prefix>", prefix)));
                }
            } catch (Exception exception) {
                Map<Integer, String> inheritedPrefixes = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefixes();
                String highestWhatever =  inheritedPrefixes.entrySet().stream().filter(entry -> !(entry.getValue() != null)).sorted(Map.Entry.comparingByKey()).findFirst().get().getValue();
                e.sendMessage(ChatColor.translateAlternateColorCodes('&', join_message.replaceAll("<player>", player.getName()).replaceAll("<prefix>", prefix)));
                e.sendMessage(String.valueOf(highestWhatever));
                print(inheritedPrefixes);
            }
        }
    }

    public static void print(final Map<?, ?> map) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Resource Pack Status
    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        String prefix_message = main.getConfig().getString("prefix-message");
        String failed_resource_pack_message = main.getConfig().getString("failed-resource-pack-message");
        String declined_resource_pack_message = main.getConfig().getString("declined-resource-pack-message");
        // If the resource pack download failed
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            resourcepackManager.setStatus(player.getUniqueId(), false);
            player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + failed_resource_pack_message));
        }

        // If the user declined the resource pack download
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            resourcepackManager.setStatus(player.getUniqueId(), false);
            player.sendMessage(MiniMessage.miniMessage().deserialize(prefix_message + ' ' + declined_resource_pack_message));
        }

        // If the user accepted the resource pack download
        else {
            resourcepackManager.setStatus(player.getUniqueId(), true);
        }
    }
}
