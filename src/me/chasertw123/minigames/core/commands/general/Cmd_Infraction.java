package me.chasertw123.minigames.core.commands.general;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.commands.UserPlayerCommand;
import me.chasertw123.minigames.core.user.OfflineUser;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.reports.Gui_Infraction;
import me.chasertw123.minigames.shared.infraction.Infraction;
import me.chasertw123.minigames.shared.rank.RankType;
import net.md_5.bungee.api.ChatColor;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Cmd_Infraction extends UserPlayerCommand {

    public Cmd_Infraction() {
        super("punish", RankType.STAFF.getRankLevel());
    }

    @Override
    public void runCommand(User pp, String[] args) {

        if (args.length == 0) {
            pp.sendPrefixedMessage(ChatColor.RED + "Correct Usage: /punish <username>");
            return;
        }

        if (args[0].equalsIgnoreCase(pp.getUsername())) {
            pp.sendPrefixedMessage(ChatColor.RED + "You are unable to punish yourself. Try some meditation.");
            return;
        }

        OfflineUser opp = CoreAPI.getUser(args[0]);
        if (opp == null) {
            pp.sendPrefixedMessage(ChatColor.RED + "Invalid Player!");
            return;
        }

        if (pp.getRank().getRankType().getRankLevel() <= opp.getRank().getRankType().getRankLevel()) {
            pp.sendPrefixedMessage(ChatColor.RED + "You are unable to punish other staff members. I hope you're trolling.");
            return;
        }

        if (args.length == 1) {
            new Gui_Infraction(pp, opp);
            return;
        }

        if (!args[1].equalsIgnoreCase("custom"))
            return;

        if (!isInteger(args[2])) {
            pp.sendPrefixedMessage(ChatColor.RED + "Invalid Number!");
            return;
        }

        int infractionPoints = Integer.parseInt(args[2]);
        StringBuilder reason = new StringBuilder();

        // TODO: Build reason

        opp.addInfraction(new Infraction(pp.getUUID(), opp.getUUID(), infractionPoints, reason.toString()));
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

}
