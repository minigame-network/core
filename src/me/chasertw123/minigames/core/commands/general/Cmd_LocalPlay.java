package me.chasertw123.minigames.core.commands.general;

import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.ChatColor;

/**
 * Created by Scott Hiett on 8/3/2017.
 */
public class Cmd_LocalPlay extends UserPlayerCommand {

    public Cmd_LocalPlay() {
        super("plocal",0);
    }

    @Override
    public void runCommand(User pp, String[] args) {
        if(args.length != 1)
            return;

        pp.addToQueue(args[0]);
    }

}
