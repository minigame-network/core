package me.chasertw123.minigames.core.features.messages;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

/**
 * Created by Chase on 7/29/2017.
 */
public enum Messages {

    ACHIEVEMENTS_HELP(new Message_AchievementsHelp()),
    ACTIVATE_BOOSTER(new Message_ActivateBooster()),
    BUY_BOOSTER(new Message_BuyBooster()),
    DISCORD_LINK(new Message_Discord()),
    COMMAND_HELP(new Message_HelpCommand()),
    BUY_STAT_RESET_TOKENS(new Message_StatResetToken());

    private Message messageClass = null;
    private BaseComponent[] message = null;

    Messages(Message messageClass) {
        this.messageClass = messageClass;
    }

    public void send(User pp) {
        pp.getPlayer().spigot().sendMessage(messageClass.buildMessage(pp));
    }

    public void send(Player player) {
        player.spigot().sendMessage(messageClass.buildMessage(CoreAPI.getUser(player)));
    }
}
