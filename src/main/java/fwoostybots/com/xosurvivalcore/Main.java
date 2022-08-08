package fwoostybots.com.xosurvivalcore;

import fwoostybots.com.xosurvivalcore.Commands.*;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("XOSurvival-Core plugin has enabled.");

        getCommand("god").setExecutor(new GodCommand(this));
        getCommand("gui").setExecutor(new GUICommand(this));

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("XOSurvival-Core plugin has disabled.");
    }
}
