package me.chasertw123.minigames.core.commands.general;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.commands.UserCommand;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd_ModifyStat extends UserCommand {

    // /modifystat <user> <stat> <value>

    public Cmd_ModifyStat() {
        super("modifystat", 3);
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {

            if (args.length < 3 || args.length > 4) {
                sender.sendMessage("Incorrect arguments: /modifystat <user> <stat> <value> [discrete]");
                return;
            }

            Stat statToModify = Stat.fromString(args[1]);
            if (statToModify == null) {
                sender.sendMessage("Unknown stat: " + args[1]);
                return;
            }

            if (!isInteger(args[2])) {
                sender.sendMessage("Invalid number: " + args[2]);
                return;
            }

            int modifyAmount = Integer.parseInt(args[2]);
            boolean discreteModify = false;

            if (args.length == 4) {
                if (!args[3].equalsIgnoreCase("true") && !args[3].equalsIgnoreCase("false")) {
                    sender.sendMessage("Discrete must be either true of false! Found: " + args[3]);
                    return;
                }

                discreteModify = BooleanUtils.toBoolean(args[3]);
            }

            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            out.writeUTF("ModifyStat");
            out.writeUTF("Console");
            out.writeUTF(args[0]);
            out.writeUTF(statToModify.toString());
            out.writeUTF(modifyAmount + "");
            out.writeUTF(discreteModify + "");

            Bukkit.getServer().sendPluginMessage(Main.getInstance(), "MCParadise", out.toByteArray());
            return;
        }

        User user = CoreAPI.getUser((Player) sender);

        if (args.length < 3) {
            user.sendPrefixedMessage(ChatColor.RED + "Incorrect arguments: /modifystat <user> <stat> <value> [discrete]");
            return;
        }

        Stat statToModify = Stat.fromString(args[1]);
        if (statToModify == null) {
            user.sendPrefixedMessage(ChatColor.RED + "Unknown stat: " + args[1]);
            return;
        }

        if (!isInteger(args[2])) {
            user.sendPrefixedMessage(ChatColor.RED + "Invalid number: " + args[2]);
            return;
        }

        int modifyAmount = Integer.parseInt(args[2]);
        boolean discreteModify = true;

        if (args.length == 4) {
            if (!args[3].equalsIgnoreCase("true") && !args[3].equalsIgnoreCase("false")) {
                user.sendPrefixedMessage(ChatColor.RED + "Discrete must be either true of false! Found: " + args[3]);
                return;
            }

            discreteModify = BooleanUtils.toBoolean(args[3]);
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("ModifyStat");
        out.writeUTF(user.getUsername());
        out.writeUTF(args[0]);
        out.writeUTF(statToModify.toString());
        out.writeUTF(modifyAmount + "");
        out.writeUTF(discreteModify + "");

        user.getPlayer().sendPluginMessage(Main.getInstance(), "MCParadise", out.toByteArray());
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
