package me.chasertw123.minigames.core.listeners.minigame;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.features.messages.Messages;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.booster.GameBooster;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Chase on 9/2/2017.
 */
public class Event_PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        User pp = CoreAPI.getUser(e.getPlayer());

        GameBooster activeBooster = null;
        for (GameBooster gameBooster : CoreAPI.getBoosterManager().getCurrentGameBoosters())
            if (ServerGameType.valueOf(gameBooster.getGameMode()) == CoreAPI.getServerDataManager().getServerGameType()) {
                activeBooster = gameBooster;
                break;
            }

        if (activeBooster == null && pp.getBoosters().get(CoreAPI.getServerDataManager().getServerGameType()) > 0)
            Messages.ACTIVATE_BOOSTER.send(pp);

        else if (activeBooster == null && pp.getBoosters().get(CoreAPI.getServerDataManager().getServerGameType()) == 0)
            Messages.BUY_BOOSTER.send(pp);

        else if (activeBooster != null)
            pp.sendPrefixedMessage(ChatColor.LIGHT_PURPLE + "A 3x Coins Booster is currently active by " + ChatColor.BOLD + "" + ChatColor.GREEN
                    + activeBooster.getActivatorName() + ChatColor.LIGHT_PURPLE + "!");
    }
}
