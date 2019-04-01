package me.chasertw123.minigames.core.event;

import me.chasertw123.minigames.core.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Chase on 1/7/2018.
 */
public class UserQuitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private User user;
    private QuitType quitType;

    public UserQuitEvent(User user, QuitType quitType) {
        this.user = user;
        this.quitType = quitType;
    }

    public User getUser() {
        return user;
    }

    public QuitType getQuitType() {
        return quitType;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum QuitType {SENDING_TO_SERVER, LEAVING_NETWORK}
}
