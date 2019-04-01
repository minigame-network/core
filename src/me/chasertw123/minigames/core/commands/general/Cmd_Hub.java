package me.chasertw123.minigames.core.commands.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.framework.ServerType;
import org.bukkit.ChatColor;

/**
 * Created by Chase on 8/2/2017.
 */
public class Cmd_Hub extends UserPlayerCommand {

    public Cmd_Hub() {
        super("hub", 0);
    }

    @Override
    public void runCommand(User pp, String[] args) {
        if (CoreAPI.getServerDataManager().getServerType() != ServerType.HUB) {
            pp.sendPrefixedMessage(ChatColor.GREEN + "Sending to hub...");
            pp.sendToServer("hub");
        }
    }
}
