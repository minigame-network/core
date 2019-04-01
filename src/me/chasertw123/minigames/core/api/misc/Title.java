package me.chasertw123.minigames.core.api.misc;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class Title {

    /**
     * Sends a title to a Player.
     *
     * @param p       The Player to send the title to.
     * @param fadeIn  The time it takes for the title to fade in.
     * @param stay    The time that the title stays.
     * @param fadeOut The time it takes for the title to fade out.
     * @param title   The String of the title. Color codes supported.
     */
    public static void sendTitle(Player p, int fadeIn, int stay, int fadeOut, String title) {
        sendAll(p, fadeIn, stay, fadeOut, title, null, null, null, null);
    }

    /**
     * Sends a subtitle to a Player.
     *
     * @param p        The Player to send the title to.
     * @param fadeIn   The time it takes for the title to fade in.
     * @param stay     The time that the title stays.
     * @param fadeOut  The time it takes for the title to fade out.
     * @param subtitle The String of the subtitle. Color codes supported.
     */
    public static void sendSubtitle(Player p, int fadeIn, int stay, int fadeOut, String subtitle) {
        sendAll(p, fadeIn, stay, fadeOut, null, subtitle, null, null, null);
    }

    /**
     * Sends a title and subtitle to a Player.
     *
     * @param p        The Player to send the titles to.
     * @param fadeIn   The time it takes for the title to fade in.
     * @param stay     The time that the title stays.
     * @param fadeOut  The time it takes for the title to fade out.
     * @param title    The String of the title. Color codes supported.
     * @param subtitle The String of the subtitle. Color codes supported.
     */
    public static void sendTitles(Player p, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
        sendAll(p, fadeIn, stay, fadeOut, title, subtitle, null, null, null);
    }

    /**
     * Sends an action bar message to a Player.
     *
     * @param p    The Player to send the actionbar to.
     * @param text The String of the message. Color codes supported.
     */
    public static void sendActionbar(Player p, String text) {
        sendAll(p, 0, 0, 0, null, null, text, null, null);
    }

    /**
     * Sends the tablist header and footer to a Player.
     *
     * @param p      The Player to send the tablist header/footer to.
     * @param header The text in the header. Color codes supported.
     * @param footer The text in the footer. Color codes supported.
     */
    public static void sendTablist(Player p, String header, String footer) {
        sendAll(p, 0, 0, 0, null, null, null, header, footer);
    }

    /**
     * @param p          The Player to send the "stuff" to.
     * @param fadeIn     The time it takes for the title to fade in.
     * @param stay       The time that the title stays.
     * @param fadeOut    The time it takes for the title to fade out.
     * @param title      The String of the title. Color codes supported.
     * @param subtitle   The String of the subtitle. Color codes supported.x
     * @param actionText The String of the message. Color codes supported.
     * @param header     The text in the header. Color codes supported.
     * @param footer     The text in the footer. Color codes supported.
     */
    public static void sendAll(Player p, int fadeIn, int stay, int fadeOut, String title, String subtitle, String actionText, String header, String footer) {
        PlayerConnection playerConnection = ((CraftPlayer) p).getHandle().playerConnection;

        if (title != null && subtitle != null) {
            PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
            playerConnection.sendPacket(packetPlayOutTimes);
        }

        if (title != null) {
            IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
            playerConnection.sendPacket(titlePacket);
        }

        if (subtitle != null) {
            IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', subtitle) + "\"}");
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleComponent);
            playerConnection.sendPacket(subtitlePacket);
        }

        if (actionText != null) {
            IChatBaseComponent actionComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', actionText) + "\"}");
            PacketPlayOutChat actionPacket = new PacketPlayOutChat(actionComponent, (byte) 2);
            playerConnection.sendPacket(actionPacket);
        }

        if (header != null || footer != null) {
            header = header == null ? "" : ChatColor.translateAlternateColorCodes('&', header);
            footer = footer == null ? "" : ChatColor.translateAlternateColorCodes('&', footer);

            IChatBaseComponent headerComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
            IChatBaseComponent footerComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");

            PacketPlayOutPlayerListHeaderFooter packetTablist = new PacketPlayOutPlayerListHeaderFooter(headerComponent);
            try {
                Field field = packetTablist.getClass().getDeclaredField("b");
                field.setAccessible(true);
                field.set(packetTablist, footerComponent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            playerConnection.sendPacket(packetTablist);
        }
    }
}
