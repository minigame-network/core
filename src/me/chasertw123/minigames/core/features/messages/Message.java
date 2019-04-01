package me.chasertw123.minigames.core.features.messages;

import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Chase on 7/29/2017.
 */
public interface Message {

    TextComponent buildMessage(User pp);

}
