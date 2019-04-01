package me.chasertw123.minigames.core.event;

import me.chasertw123.minigames.core.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Chase on 1/13/2018.
 */
public class UserRankStatusUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private User user;

    public UserRankStatusUpdateEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
