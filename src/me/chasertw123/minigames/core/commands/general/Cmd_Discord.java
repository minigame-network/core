package me.chasertw123.minigames.core.commands.general;

import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.features.messages.Messages;
import me.chasertw123.minigames.core.user.User;

/**
 * Created by Chase on 8/10/2017.
 */
public class Cmd_Discord extends UserPlayerCommand {

    public Cmd_Discord() {
        super("discord", 0);
    }

    @Override
    public void runCommand(User pp, String[] args) {
        Messages.DISCORD_LINK.send(pp);
    }
}
