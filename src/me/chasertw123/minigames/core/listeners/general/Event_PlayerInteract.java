package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.AbstractItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 9/2/2017.
 */
public class Event_PlayerInteract implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {

        ItemStack hand = e.getPlayer().getItemInHand();
        User pp = CoreAPI.getUser(e.getPlayer());

        if (hand == null || hand.getType() == Material.AIR)
            return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            pp.getAbstractItems().values().stream()
                    .filter(ai -> ai.getItemStack().getItemMeta().getDisplayName().equals(hand.getItemMeta().getDisplayName()))
                    .findFirst()
                    .ifPresent(ai -> {
                        ai.getAction().interact(AbstractItem.InteractType.RIGHT);
                        e.setCancelled(true);
                        pp.getPlayer().updateInventory();
                    });
        }

        else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
            pp.getAbstractItems().values().stream()
                    .filter(ai -> ai.getItemStack().getItemMeta().getDisplayName().equals(hand.getItemMeta().getDisplayName()))
                    .findFirst()
                    .ifPresent(ai -> {
                        ai.getAction().interact(AbstractItem.InteractType.LEFT);
                        e.setCancelled(true);
                        pp.getPlayer().updateInventory();
            });
    }
}
