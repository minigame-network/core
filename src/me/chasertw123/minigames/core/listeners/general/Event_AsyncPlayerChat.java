package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.event.UserChatEvent;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.infraction.Punishment;
import me.chasertw123.minigames.shared.infraction.PunishmentType;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.stream.Collectors;

/**
 * Created by Chase on 7/24/2017.
 */
public class Event_AsyncPlayerChat implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        User sender = CoreAPI.getUser(e.getPlayer());
        for (Punishment punishment : sender.getPunishments())
            if (punishment.getType() == PunishmentType.MUTE && (punishment.getDateIssued() + punishment.getTimeScale().getUnixTime()) > System.currentTimeMillis()) {
                sender.sendPrefixedMessage(ChatColor.RED + "You're currently muted. Remaining time: " + punishment.getTimeRemaining());
                return;
            }

        UserChatEvent event = new UserChatEvent(sender, e.getMessage(), CoreAPI.getOnlinePlayers().stream()
                .filter(receiver -> !receiver.getUUID().equals(sender.getUUID()))
                .collect(Collectors.toList()));

        Main.getInstance().getServer().getPluginManager().callEvent(event);

        if (event.isCancelled())
            return;

        if (event.getRecipientMessage() != null && event.getRecipients().size() > 0)
            event.getRecipients().forEach(pp -> pp.getPlayer().spigot().sendMessage(event.getRecipientMessage()));

        if (event.getSenderMessage() != null)
            sender.getPlayer().spigot().sendMessage(event.getSenderMessage());
    }

}
