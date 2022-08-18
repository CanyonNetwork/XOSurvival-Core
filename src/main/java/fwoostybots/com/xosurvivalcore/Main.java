package fwoostybots.com.xosurvivalcore;

import fwoostybots.com.xosurvivalcore.Commands.*;
import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import fwoostybots.com.xosurvivalcore.Utilities.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

// Vault API Stuff
import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.RegisteredServiceProvider;

public final class Main extends JavaPlugin {
    private static Chat chat = null;

    @Override
    public void onEnable() {

        // Adds the ResourcePackManager manager to be public
        ResourcePackManager resourcepackManager = new ResourcePackManager();

        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.Events.JoinEvent(this, resourcepackManager), this);

        getLogger().info("XOSurvival-Core plugin has enabled.");

        getCommand("god").setExecutor(new GodCommand(this, resourcepackManager));
        getCommand("resourcepack").setExecutor(new ResourcePackCommand(this, resourcepackManager));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("wildgui").setExecutor(new WildGUI(this));
        getCommand("wild").setExecutor(new WildCommand(this));

        Teleport teleUtil = new Teleport(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        setupChat();
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public static Chat getChat() {
        return chat;
    }

    @Override
    public void onDisable() {
        getLogger().info("XOSurvival-Core plugin has disabled.");
    }
}