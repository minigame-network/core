package me.chasertw123.minigames.core.features.messages;

import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.achievements.AchievementDifficulty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import us.myles.ViaVersion.api.Via;

/**
 * Created by Chase on 7/29/2017.
 */
public class Message_AchievementsHelp implements Message {

    @Override
    @SuppressWarnings("unchecked")
    public TextComponent buildMessage(User pp) {

        int version = Via.getAPI().getPlayerVersion(pp.getPlayer());
        String achDesc = "An achievement is a type of accomplishment that has to be earned my playing on the server. All minigames have their own achievements to be earned. Some are super hard to " +
                "get and some are really easy! Work hard and one day you could unlock them all. Hover over the different difficulties to learn more about them and the type of rewards they give!";

        ComponentBuilder cb1 = new ComponentBuilder("\n          ").append("-----------").strikethrough(true).color(ChatColor.GREEN).append(" Achievements ").reset().bold(true)
                .append("-----------").reset().strikethrough(true).color(ChatColor.GREEN).append("\n\n").reset().append("                    ")
                .append("What is an achievement?\n\n").bold(true), whatIsHover = new ComponentBuilder("What is an achievement?\n\n").bold(true);

        if (version >= 335)
            whatIsHover.append(achDesc).bold(false).color(ChatColor.GRAY);

        else
            whatIsHover.append("An achievement is a type of accomplishment that has to be\n").bold(false).color(ChatColor.GRAY)
                    .append("earned my playing on the server. All minigames have\n").append("their own achievements to be earned. Some are super hard\n")
                    .append("to get and some are really easy! Work hard and one\n").append("day you could unlock them all. Hover over the different\n")
                    .append("difficulties to learn more about them and the type\n").append("of rewards they give!");

        cb1.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, whatIsHover.create())).append("      ");

        for (AchievementDifficulty ad : AchievementDifficulty.values()) {

            ComponentBuilder desc = null;
            cb1.append(ad.toString()).bold(true).color(ad.getChatColor());

            if (version >= 335)
                desc = new ComponentBuilder(ad.toString().substring(0, 1) + ad.toString().toLowerCase().substring(1) + " Difficulty Achievements\n\n").bold(true).color(ad.getChatColor())
                        .append(ad.getDescription()).bold(false).color(ChatColor.GRAY);

            else {
                desc = new ComponentBuilder(ad.toString().substring(0, 1) + ad.toString().toLowerCase().substring(1) + " Difficulty Achievements\n\n").bold(true).color(ad.getChatColor());
                StringBuilder sb = new StringBuilder(ad.getDescription());

                int i = 0;
                while (i + 50 < sb.length() && (i = sb.lastIndexOf(" ", i + 50)) != -1)
                    sb.replace(i, i + 1, "\n");

                for (String s :sb.toString().split("\n"))
                    desc.append(s + "\n").bold(false).color(ChatColor.GRAY);
            }

            cb1.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, desc.create())).append("  ").reset();
        }

        cb1.append("\n\n          ").reset().append("-----------").strikethrough(true).color(ChatColor.GREEN).append(" Hover 4 Info ").reset().bold(true)
                .append("-----------").reset().strikethrough(true).color(ChatColor.GREEN).append("\n").reset();

        return new TextComponent(cb1.create());
    }
}
