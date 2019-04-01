package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Chase on 9/2/2017.
 */
public class Event_PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
                if (player.getUniqueId() != e.getPlayer().getUniqueId())
                    player.hidePlayer(e.getPlayer());

            for (Player player : Bukkit.getServer().getOnlinePlayers())
                if (player.getUniqueId() != e.getPlayer().getUniqueId())
                    player.showPlayer(e.getPlayer());
        }, 1L);
    }
}
