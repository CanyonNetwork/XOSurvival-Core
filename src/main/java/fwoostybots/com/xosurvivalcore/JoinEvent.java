package fwoostybots.com.xosurvivalcore;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class JoinEvent implements Listener {

    // Give the player the resource pack prompt
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setResourcePack("https://www.dropbox.com/s/rieh4n8tvkjh1gw/dd.zip?dl=1");
    }

    // Resource Pack Status
    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        // If the resource pack download failed
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            player.kick(Component.text("Server resource pack has failed to download.\nPlease rejoin."));
        }

        // If the user declined the resource pack download
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            player.kick(Component.text("You failed to download the resource pack."));
        }
    }
}
