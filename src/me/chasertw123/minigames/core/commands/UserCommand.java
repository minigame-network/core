package me.chasertw123.minigames.core.commands;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Chase on 6/27/2017.
 */
public abstract class UserCommand implements CommandExecutor {

    protected static final String COMMAND_USAGE_PREFIX = ChatColor.RED + "Invalid Arguments! Correct Arguments: " + ChatColor.RESET;

    private int permission;

    /**
     *
     * @param commandName {@link String} to identify the command
     */
    public UserCommand(String commandName, int permission) {
        this.permission = permission;
        Main.getInstance().getCommand(commandName).setExecutor(this);
    }

    /**
     * Sets the permission level for given command
     *
     * @return {@link Integer} of lowest {@link me.chasertw123.minigames.shared.rank.RankType}
     * that can use this command
     */
    public int getPermission() {
        return permission;
    }

    /**
     * Code executed when a player has the permission level to use
     * said command
     *
     * @param sender {@link CommandSender} executing the command
     * @param args Array of strings after command
     */
    public abstract void runCommand(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            User pp = CoreAPI.getUser((Player) sender);

            if (pp.getRank().getRankType().getRankLevel() < getPermission()) {
                pp.sendMessage(ChatColor.RED + "Incorrect Rank! You are unable to run this command.");
                return true;
            }
        }

        runCommand(sender, args);
        return true;
    }
}
