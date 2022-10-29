package fwoostybots.com.xosurvivalcore;

import fwoostybots.com.xosurvivalcore.Commands.*;
import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import fwoostybots.com.xosurvivalcore.Utilities.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.plugin.RegisteredServiceProvider;

public final class Main extends JavaPlugin {
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {

        // Adds the ResourcePackManager manager to be public
        ResourcePackManager resourcepackManager = new ResourcePackManager();

        // Adds luckPerms to be public
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.luckPerms = provider.getProvider();
        }

        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.Events.JoinEvent(this, resourcepackManager, luckPerms), this);
        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.Events.ChatEvent(this, resourcepackManager, luckPerms), this);

        getLogger().info("XOSurvival-Core plugin has enabled.");

        getCommand("resourcepack").setExecutor(new ResourcePackCommand(this, resourcepackManager));
        getCommand("warp").setExecutor(new WarpCommand(this, resourcepackManager));
        getCommand("wild").setExecutor(new WildGUI(this, resourcepackManager));
        getCommand("wildc").setExecutor(new WildCommand(this));

        Teleport teleUtil = new Teleport(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("XOSurvival-Core plugin has disabled.");
    }
}