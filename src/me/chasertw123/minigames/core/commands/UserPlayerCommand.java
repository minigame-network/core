package me.chasertw123.minigames.core.commands;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Chase on 7/24/2017.
 */
public abstract class UserPlayerCommand extends UserCommand {

    public UserPlayerCommand(String commandName, int permission) {
        super(commandName, permission);
    }

    public abstract void runCommand(User pp, String[] args);

    @Override
    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be executed from console!");
            return;
        }

        runCommand(CoreAPI.getUser((Player) sender), args);
    }

}
