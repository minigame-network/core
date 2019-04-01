package me.chasertw123.minigames.core.features.messages;

import me.chasertw123.minigames.core.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Chase on 7/29/2017.
 */
public class Message_HelpCommand implements Message {

    @Override
    public TextComponent buildMessage(User pp) {

        TextComponent tc1 = new TextComponent(new ComponentBuilder("\n").append("For questions or issues related to payments: ").color(ChatColor.YELLOW)
                .append("support.minaria.net\n").color(ChatColor.BLUE).create()),

                tc2 = new TextComponent(new ComponentBuilder("Report server issues or bugs on the forums: ").color(ChatColor.YELLOW)
                        .append("issues.minaria.net\n").color(ChatColor.BLUE).create()),

                tc3 = new TextComponent(new ComponentBuilder("Find a rule breaker? Report them on the forums: ").color(ChatColor.YELLOW)
                        .append("report.minaria.net\n").color(ChatColor.BLUE).create()),

                tc4 = new TextComponent(new ComponentBuilder("Need more help? Check out our help section: ").color(ChatColor.YELLOW)
                        .append("help.minaria.net\n").color(ChatColor.BLUE).create());

        for (TextComponent tc : new TextComponent[] {tc1, tc2, tc3, tc4})
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open the link!").color(ChatColor.LIGHT_PURPLE).create()));


        tc1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://support.minaria.net/"));
        tc2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://issues.minaria.net/"));
        tc3.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://report.minaria.net/"));
        tc4.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://help.minaria.net/"));

        tc1.addExtra(tc2);
        tc1.addExtra(tc3);
        tc1.addExtra(tc4);

        return tc1;
    }
}
