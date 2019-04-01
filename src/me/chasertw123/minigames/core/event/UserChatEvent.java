package me.chasertw123.minigames.core.event;

import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by Chase on 8/31/2017.
 */
public class UserChatEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private User user;
    private String message;
    private List<User> recipients;
    private TextComponent recipientMessage, senderMessage;

    public UserChatEvent(User user, String message, List<User> recipients) {
        this.user = user;
        this.message = message;
        this.recipients = recipients;

        ChatColor rankColor = user.getRank().getRankColor(), chatColor = user.getRank().getChatColor();
        if (!user.getRank().isStaff() && user.isDeluxe()) {
            rankColor = ChatColor.GOLD;
            chatColor = ChatColor.WHITE;
        }

        TextComponent formattedMessage = new TextComponent(new ComponentBuilder(user.getUsername()).color(rankColor).append(" » ")
                .color(ChatColor.DARK_GRAY).append(message).color(chatColor).create());

        this.recipientMessage = formattedMessage;
        this.senderMessage = formattedMessage;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public TextComponent getRecipientMessage() {
        return recipientMessage;
    }

    public void setRecipientMessage(TextComponent recipientMessage) {
        this.recipientMessage = recipientMessage;
    }

    public TextComponent getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(TextComponent senderMessage) {
        this.senderMessage = senderMessage;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
