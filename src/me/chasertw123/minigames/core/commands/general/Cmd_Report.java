package me.chasertw123.minigames.core.commands.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.reports.Report;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by Chase on 6/27/2017.
 */
public class Cmd_Report extends UserPlayerCommand {

    private static final String REPORT_PREFIX = ChatColor.RED + "" + ChatColor.BOLD + "Report " + ChatColor.GRAY + " > " + ChatColor.RESET;

    public Cmd_Report() {
        super("report", 0);
    }

    @Override
    public void runCommand(User pp, String[] args) {

        if (args.length == 0) {
            pp.sendMessage(COMMAND_USAGE_PREFIX +  "/report <username> [message]");
            return;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            pp.sendMessage(REPORT_PREFIX + "Unable to find " + args[0] + " online on this server.");
            return;
        }

        User reported = CoreAPI.getUser(Bukkit.getPlayer(args[0]));

        if (reported.getUUID().equals(pp.getUUID())) {
            pp.sendMessage(REPORT_PREFIX + "Why would you want to report yourself?");
            return;
        }

        StringBuilder sb = new StringBuilder("No message :(");
        if (args.length > 1) {
            sb.setLength(0);

            for (int i = 1; i != args.length; i++)
                sb.append(args[i] + " ");
        }

        Report r = new Report(reported, reported, sb.toString());
        reported.addReport(r);

        ComponentBuilder cb1 = new ComponentBuilder("\nReports ").color(net.md_5.bungee.api.ChatColor.RED).bold(true)
                .append("> ").color(net.md_5.bungee.api.ChatColor.GRAY).bold(false)
                .append("Your report has been filed!\n").color(net.md_5.bungee.api.ChatColor.WHITE);

        ComponentBuilder cb2 = new ComponentBuilder("Report Info").color(net.md_5.bungee.api.ChatColor.RED).bold(true)
                .append("\nFiled on: ").bold(false).color(net.md_5.bungee.api.ChatColor.WHITE).append(r.getDate()).color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("\n\nFiled by: ").color(net.md_5.bungee.api.ChatColor.WHITE).append(pp.getUsername()).color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("\nFilled against: ").color(net.md_5.bungee.api.ChatColor.WHITE).append(reported.getUsername()).color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("\n\nMessages Left: ").color(net.md_5.bungee.api.ChatColor.WHITE);

        int i = 0;
        while (i + 40 < sb.length() && (i = sb.lastIndexOf(" ", i + 40)) != -1)
            sb.replace(i, i + 1, "\n");

        for (String s : sb.toString().split("\n"))
            cb2.append(s + "\n").color(net.md_5.bungee.api.ChatColor.GRAY);

        TextComponent tc = new TextComponent(cb1.create());
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb2.create()));

        pp.getPlayer().spigot().sendMessage(tc);
    }
}