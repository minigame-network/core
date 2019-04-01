package me.chasertw123.minigames.core.features.messages;

import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Chase on 8/16/2017.
 */
public class Message_BuyBooster implements Message {

    @Override
    public TextComponent buildMessage(User pp) {

        ComponentBuilder cb = new ComponentBuilder("\nMinaria").bold(true).color(ChatColor.BLUE).append(" » ").bold(false).color(ChatColor.DARK_GRAY)
                .append("No Coin Booster active. ").color(ChatColor.LIGHT_PURPLE).append("CLICK HERE ").bold(true).color(ChatColor.YELLOW)
                .append("to buy a " + CoreAPI.getServerDataManager().getServerGameType().getDisplay() + " 3x Coin Booster!").bold(false).color(ChatColor.LIGHT_PURPLE);

        TextComponent tc = new TextComponent(cb.create());

        tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcparadise.net/index.php?pages/store/"));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open a link to our store where you can buy a "
                + CoreAPI.getServerDataManager().getServerGameType().getDisplay() + " 3x Coin Booster!").color(ChatColor.GREEN).create()));

        return tc;
    }
}
