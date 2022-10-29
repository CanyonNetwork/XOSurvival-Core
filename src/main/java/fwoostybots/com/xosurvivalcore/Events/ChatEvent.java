package fwoostybots.com.xosurvivalcore.Events;

import fwoostybots.com.xosurvivalcore.Main;
import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import net.luckperms.api.LuckPerms;

import java.util.Map;

public class ChatEvent implements Listener {

    private Main main;
    private final ResourcePackManager resourcepackManager;
    private final LuckPerms luckPerms;

    public ChatEvent(Main main, ResourcePackManager resourcepackManager, LuckPerms luckPerms) {
        this.main = main;
        this.resourcepackManager = resourcepackManager;
        this.luckPerms = luckPerms;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Component message = event.message();
        String resourcepack_chat_message = main.getConfig().getString("resourcepack-chat-message");
        String chat_message = main.getConfig().getString("chat-message");
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        // Get the highest priority prefix
        Map<Integer, String> inheritedPrefixes2 = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefixes();
        String highestPrefix =  inheritedPrefixes2.entrySet().stream().filter(entry -> !(entry.getValue() == null)).sorted((o1, o2) -> -o1.getKey().compareTo(o2.getKey())).findFirst().get().getValue();
        // Get the lowest priority prefix
        Map<Integer, String> inheritedPrefixes = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefixes();
        String lowestPrefix =  inheritedPrefixes.entrySet().stream().filter(entry -> !(entry.getValue() == null)).sorted(Map.Entry.comparingByKey()).findFirst().get().getValue();
        for (Player e : Bukkit.getOnlinePlayers()) {
            //try {
                boolean packstatus = resourcepackManager.getStatus(e.getUniqueId());
                // Message sent if they have the resource pack enabled
                if (packstatus) {
                    resourcepack_chat_message = resourcepack_chat_message.replace("%", "%%");
                    resourcepack_chat_message = resourcepack_chat_message.replace("%%prefix%%", highestPrefix);
                    resourcepack_chat_message = resourcepack_chat_message.replace("%%player%%", player.getName());
                    resourcepack_chat_message = resourcepack_chat_message.replace("%%message%%", PlainTextComponentSerializer.plainText().serialize(message));
                    e.sendMessage(ChatColor.translateAlternateColorCodes('&', resourcepack_chat_message));
                    // Message sent if they don't have the resource pack enabled
                } else {
                    chat_message = chat_message.replace("%", "%%");
                    chat_message = chat_message.replace("%%prefix%%", lowestPrefix);
                    chat_message = chat_message.replace("%%player%%", player.getName());
                    chat_message = chat_message.replace("%%message%%", PlainTextComponentSerializer.plainText().serialize(message));
                    e.sendMessage(ChatColor.translateAlternateColorCodes('&', resourcepack_chat_message));
                    e.sendMessage(ChatColor.translateAlternateColorCodes('&', chat_message));
                }
            //} catch (Exception exception) {
            //    e.sendMessage(ChatColor.translateAlternateColorCodes('&', chat_message.replaceAll("<player>", player.getName()).replaceAll("<prefix>", lowestPrefix).replaceAll("<message>", PlainTextComponentSerializer.plainText().serialize(message))));
            //}
        }
    }
}