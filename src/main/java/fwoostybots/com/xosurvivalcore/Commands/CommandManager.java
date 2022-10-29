package fwoostybots.com.xosurvivalcore.Commands;

import fwoostybots.com.xosurvivalcore.Commands.impl.ResourcePackCommand;
import fwoostybots.com.xosurvivalcore.Commands.impl.WarpCommand;
import fwoostybots.com.xosurvivalcore.Commands.impl.WildCommand;
import fwoostybots.com.xosurvivalcore.Commands.impl.WildGUI;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class CommandManager {

    private final List<Command> commands = List.of(
            new WarpCommand(),
            new WildGUI(),
            new ResourcePackCommand(),
            new WildCommand()
    );


    public CommandManager() {
        commands.forEach(command -> {
            Bukkit.getCommandMap().register("help", command);
        });
    }


    public @Nullable String getPermissionOf(String commandName) {
        for (Command command : commands) {
            if (command.getName() == commandName || command.getAliases().contains(commandName)) {
                return command.getPermissionString();
            }
        }
        return null;
    }


}
