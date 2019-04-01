package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.AbstractItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * Created by Chase on 6/28/2017.
 */
public class Event_InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player))
            return;

        User pp = CoreAPI.getUser((Player) e.getWhoClicked());
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
            for (AbstractItem ai : pp.getAbstractItems().values())
                if (ai.getItemStack().getItemMeta().getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName())) {

                    AbstractItem.InteractType interactType = AbstractItem.InteractType.OTHER;
                    if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT)
                        interactType = AbstractItem.InteractType.LEFT;

                    else if (e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT)
                        interactType = AbstractItem.InteractType.RIGHT;

                    ai.getAction().interact(interactType);
                    e.setCancelled(true);
                    return;
                }

        if (e.getInventory().getType() == InventoryType.PLAYER && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            return;
        }

        if (pp.getCurrentGui() == null)
            return;

        e.setCancelled(true);

        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && pp.getCurrentGui().getItemStack(e.getSlot()) != null)
            pp.getCurrentGui().getAction(e.getSlot()).click(e.getSlot(), e.getClick(), (Player) e.getWhoClicked());
    }
}
