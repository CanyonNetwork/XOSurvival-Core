package fwoostybots.com.xosurvivalcore;

import fwoostybots.com.xosurvivalcore.Commands.*;
import fwoostybots.com.xosurvivalcore.Managers.ResourcePackManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.plugin.RegisteredServiceProvider;

public final class Main extends JavaPlugin {

    private static Main instance;

    private LuckPerms luckPerms;

    private CommandManager commandManager;
    private ResourcePackManager resourcePackManager;

    @Override
    public void onEnable() {
        // Adds "this" for "this.main" to work
        if (instance == null) instance = this;
        // Adds the ResourcePackManager manager to be public
        resourcePackManager = new ResourcePackManager();
        // Adds luckPerms to be public
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.luckPerms = provider.getProvider();
        }

        // Register events to make them work
        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.Events.JoinEvent(this, resourcePackManager, luckPerms), this);
        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.Events.ChatEvent(this, resourcePackManager, luckPerms), this);
        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.Events.PlayerEvents(this), this);

        // Register the CommandManager
        commandManager = new CommandManager();

        getLogger().info("XOSurvival-Core plugin has enabled.");

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }


    public ResourcePackManager getResourcePackManager() {
        // To access resourcePackManager everywhere in your project you just Main.getInstance().getResourcePackManager()
        return this.resourcePackManager;
    }

    public static Main getInstance() {
        // Gets the main instance of JavaPlugin.
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("XOSurvival-Core plugin has disabled.");
    }
}