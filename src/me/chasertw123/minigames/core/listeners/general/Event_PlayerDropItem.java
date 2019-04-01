package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.shared.framework.ServerSetting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Chase on 7/31/2017.
 */
public class Event_PlayerDropItem implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!CoreAPI.getUser(e.getPlayer()).getServerSetting(ServerSetting.ITEM_DROPPING))
            e.setCancelled(true);
    }
}
