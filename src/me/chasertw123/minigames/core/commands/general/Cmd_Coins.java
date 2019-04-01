package me.chasertw123.minigames.core.commands.general;

import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import net.md_5.bungee.api.ChatColor;

public class Cmd_Coins extends UserPlayerCommand {

    public Cmd_Coins() {
        super("coins", 0);
    }

    @Override
    public void runCommand(User pp, String[] args) {
        pp.sendMessage(ChatColor.GOLD + "You have " + ChatColor.WHITE + pp.getStat(Stat.COINS) + ChatColor.GOLD + " coins.");
    }
}
