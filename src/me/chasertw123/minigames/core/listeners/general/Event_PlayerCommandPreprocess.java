package me.chasertw123.minigames.core.listeners.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.features.messages.Messages;
import me.chasertw123.minigames.shared.rank.RankType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Chase on 7/25/2017.
 */
public class Event_PlayerCommandPreprocess implements Listener {

    private List<String> disallowedCommands = Arrays.asList("help", "version", "icanhasbukkit", "pl", "plugins", "?", "hd", "holo", "citizens", "npc", "pac", "nocheatplus", "ncp", "me");

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        if (CoreAPI.getUser(event.getPlayer()).getRank().getRankType() == RankType.UPPERSTAFF)
            return;

        disallowedCommands.stream().filter(s -> s.equalsIgnoreCase(event.getMessage().substring(1).split(" ")[0]) || s.startsWith("minecraft:")).findAny().ifPresent(s -> {
            Messages.COMMAND_HELP.send(event.getPlayer());
            event.setCancelled(true);
        });
    }
}
