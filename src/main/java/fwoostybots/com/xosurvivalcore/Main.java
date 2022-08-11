package fwoostybots.com.xosurvivalcore;

import fwoostybots.com.xosurvivalcore.Commands.*;
import fwoostybots.com.xosurvivalcore.Utilities.*;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new fwoostybots.com.xosurvivalcore.JoinEvent(this), this);

        getLogger().info("XOSurvival-Core plugin has enabled.");

        getCommand("god").setExecutor(new GodCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("wildgui").setExecutor(new WildGUI(this));
        getCommand("wild").setExecutor(new WildCommand(this));

        Teleport teleUtil = new Teleport(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("XOSurvival-Core plugin has disabled.");
    }
}
