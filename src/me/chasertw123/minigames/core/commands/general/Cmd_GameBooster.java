package me.chasertw123.minigames.core.commands.general;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.event.BoosterUpdateEvent;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

/**
 * Created by Scott Hiett on 8/5/2017.
 */
public class Cmd_GameBooster extends UserPlayerCommand {

    public Cmd_GameBooster() {
        super("gamebooster", 0);
    }

    @Override
    public void runCommand(User pp, String[] args) {

        if (args.length != 1){
            pp.sendPrefixedMessage(ChatColor.RED + "Incorrect arguments: /gamebooster <gamemode>");
            return;
        }

        ServerGameType gameMode = null;
        for (ServerGameType serverGameType : ServerGameType.values())
            if (serverGameType.toString().equalsIgnoreCase(args[0]))
                gameMode = serverGameType;

        if (gameMode == null){
            pp.sendPrefixedMessage(ChatColor.RED + "That is not a game mode!");
            return;
        }

        if (pp.getBoosters().get(gameMode) == 0) {
            pp.sendPrefixedMessage(ChatColor.RED + "You do not have a game booster for " + gameMode.getDisplay() + "!");
            return;
        }

        CoreAPI.getBoosterManager().refreshBoosters();
        pp.getPlayer().closeInventory();

        if (CoreAPI.getBoosterManager().boosterActive(gameMode)) {
            pp.sendPrefixedMessage(ChatColor.RED + "There is already a booster active for " + gameMode.getDisplay() + "!");
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("GameBooster");
        out.writeUTF(pp.getUsername());
        out.writeUTF(gameMode.toString().toUpperCase());

        pp.getPlayer().sendPluginMessage(Main.getInstance(), "MCParadise", out.toByteArray());
        pp.getBoosters().replace(gameMode, pp.getBoosters().get(gameMode) - 1);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Bukkit.getPluginManager().callEvent(new BoosterUpdateEvent()), 10L);
    }

}
