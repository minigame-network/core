package me.chasertw123.minigames.core.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Chase on 1/14/2018.
 */
public class BoosterUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
