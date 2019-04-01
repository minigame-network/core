package me.chasertw123.minigames.core.features.messages;

import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Chase on 9/8/2017.
 */
public class Message_StatResetToken implements Message {

    @Override
    public TextComponent buildMessage(User pp) {

        TextComponent tc = new TextComponent(new ComponentBuilder("You don't have any stat reset tokens. ").color(ChatColor.YELLOW)
                .append("CLICK HERE ").color(ChatColor.AQUA).append("to buy some from our store!").color(ChatColor.YELLOW).create());

        tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcparadise.net/index.php?pages/store/"));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open a link to our store where you can buy stat reset tokens!")
                .color(ChatColor.LIGHT_PURPLE).create()));

        return tc;
    }
}
