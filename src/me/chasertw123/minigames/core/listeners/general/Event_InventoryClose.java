package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Created by Chase on 6/28/2017.
 */
public class Event_InventoryClose implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIventoryCloseEvent(InventoryCloseEvent event) {

        User pp = CoreAPI.getUser((Player) event.getPlayer());
        AbstractGui currentGui = pp.getCurrentGui();

        if (currentGui != null && currentGui.getInventory().getName().equals(event.getInventory().getName())) {
            currentGui.onClose();
            pp.setCurrentGui(null);
        }
    }
}
