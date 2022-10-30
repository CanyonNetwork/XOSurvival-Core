package fwoostybots.com.xosurvivalcore.Events;

import fwoostybots.com.xosurvivalcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerEvents implements Listener{

    private Main main;

    public PlayerEvents(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        String world = e.getPlayer().getWorld().getName();
        TeleportCause cause = e.getCause();
        if (world.equals("spawn")) {
            if (cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
                e.setCancelled(true);
                Location loc = new Location(Bukkit.getWorld("spawn"), 0.5, 100, 55.5, 0, 0);
                player.teleport(loc);
                // wait 1 second
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.performCommand("wild");
                    }
                }.runTaskLater(this.main, 5L);
            }
        }
    }
}
