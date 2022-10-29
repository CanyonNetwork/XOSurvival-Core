package fwoostybots.com.xosurvivalcore.Commands;

import fwoostybots.com.xosurvivalcore.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Command extends org.bukkit.command.Command {

    private final String name, description, usage;
    private final List<String> aliases;
    private final String permission;

    public Command(@NotNull String name, @NotNull String description,  @NotNull String usage, @NotNull List<String> aliases, @Nullable String permission) {
        super(name, description, usage, aliases);
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
        this.permission = permission;
    }

    public Command(@NotNull String name, @NotNull String description, @NotNull List<String> aliases, @NotNull String permission) {
        this(name, description, "/" + name, aliases, permission);
    }

    public Command(@NotNull String name, String description, List<String> aliases) {
        this(name, description, aliases, null);
    }


    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender == null) return false;
        if (sender instanceof ConsoleCommandSender) {
            runConsole((ConsoleCommandSender) sender, args);
            return true;
        }
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (permission != null && (p.hasPermission(permission) == false)) {
            String noPermission = Main.getInstance().getConfig().getString("no-permission-message");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission).replaceAll("<permission>", permission));
            return false;
        }
        runPlayer(p, args);
        return true;
    }


     public void runPlayer(@NotNull Player player, String[] args) {} // Override either one for usages
     public void runConsole(ConsoleCommandSender console, String[] args) {}


    public @Nullable String getPermissionString() {
        return this.permission;
    }


}
