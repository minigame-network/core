package me.chasertw123.minigames.core.listeners.general;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.event.UserRankStatusUpdateEvent;
import me.chasertw123.minigames.core.features.quests.QuestType;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.settings.Setting;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.rank.Rank;
import me.chasertw123.minigames.shared.utils.StringUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.apache.commons.lang3.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.*;

/**
 * Created by Scott Hiett on 7/25/2017.
 */
public class Event_MessageListener implements PluginMessageListener {

    private Random r = new Random();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if(!channel.equalsIgnoreCase("MCParadise"))
            return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        String fullChannel = in.readUTF();
        String subchannel;

        if (!fullChannel.equalsIgnoreCase("MCParadise"))
            subchannel = fullChannel;
        else
            subchannel = in.readUTF();

        if (subchannel.equalsIgnoreCase("SetRank")) {

            Player p = Bukkit.getPlayer(in.readUTF());
            if (p == null)
                return;

            User paradisePlayer = CoreAPI.getUser(p);
            paradisePlayer.setRank(Rank.fromString(in.readUTF()));

            Bukkit.getServer().getPluginManager().callEvent(new UserRankStatusUpdateEvent(paradisePlayer));
        }

        else if (subchannel.equalsIgnoreCase("QuestReset")) {

            String timePeriod = in.readUTF();

            for(User paradisePlayer : CoreAPI.getOnlinePlayers())
                paradisePlayer.clearQuests(QuestType.valueOf(timePeriod.toUpperCase()));
        }

        else if (subchannel.equalsIgnoreCase("SetDeluxe")) {

            Player p = Bukkit.getPlayer(in.readUTF());
            if (p == null)
                return;

            User paradisePlayer = CoreAPI.getUser(p);
            paradisePlayer.setDeluxe(Long.parseLong(in.readUTF()));

            Bukkit.getServer().getPluginManager().callEvent(new UserRankStatusUpdateEvent(paradisePlayer));
        }

        else if (subchannel.equalsIgnoreCase("SendMessage")) {

            String senderName = in.readUTF();
            String recieverName = in.readUTF();
            String content = in.readUTF();

            Player toSendTo = Bukkit.getServer().getPlayer(recieverName);
            if (toSendTo == null)
                return;

            User paradisePlayer = CoreAPI.getUser(toSendTo);
            if (paradisePlayer.getSetting(Setting.PRIVATE_MESSAGES)) {
                paradisePlayer.sendMessage(ChatColor.RED + senderName + " " + ChatColor.WHITE + "➜ " + ChatColor.GOLD + "You" + ChatColor.WHITE + " > " + content);

                // Send a packet to the server

            }
        }

        else if (subchannel.equalsIgnoreCase("AddFriend")) {

            Player pl = Bukkit.getServer().getPlayer(in.readUTF());
            User pp = CoreAPI.getUser(pl);

            UUID uuid = UUID.fromString(in.readUTF());

            if(uuid.equals(pl.getUniqueId()))
                return; // Can't be friends with yourself.

            if(!pp.getFriends().contains(uuid))
                pp.addFriend(uuid);
        }

        else if (subchannel.equalsIgnoreCase("RemoveFriend")) {

            Player pl = Bukkit.getServer().getPlayer(in.readUTF());
            User pp = CoreAPI.getUser(pl);

            UUID uuid = UUID.fromString(in.readUTF());

            String otherName = in.readUTF();

            if(pp.getFriends().contains(uuid)) {
                pp.removeFriend(uuid);

                pp.sendMessage("You and " + ChatColor.RED + otherName + ChatColor.WHITE + " are no longer friends.");
            }
        }

        else if (subchannel.equalsIgnoreCase("NewFriendRequest")) {

            Player to = Bukkit.getServer().getPlayer(in.readUTF());
            if(to == null)
                return;

            String from = in.readUTF();

            //Send clickable message

            to.sendMessage(CoreAPI.PREFIX + "You have received a friend request from " + from + "!");

            ComponentBuilder cb = new ComponentBuilder("Click to: ").bold(false).color(net.md_5.bungee.api.ChatColor.WHITE)
                    .append("ACCEPT ").bold(true).color(net.md_5.bungee.api.ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + from))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/friend accept " + from).color(net.md_5.bungee.api.ChatColor.WHITE).create())).append("DECLINE").bold(true)
                    .color(net.md_5.bungee.api.ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend decline " + from))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/friend decline " + from).color(net.md_5.bungee.api.ChatColor.WHITE).create()));

            to.spigot().sendMessage(cb.create());
        }

        else if (subchannel.equalsIgnoreCase("GiveGameBooster")) {

            String username = in.readUTF();
            String gameMode = in.readUTF();
            int amount = Integer.parseInt(in.readUTF());

            Player p = Bukkit.getServer().getPlayer(username);
            if (p == null)
                return;

            ServerGameType serverGameType = ServerGameType.valueOf(gameMode.toUpperCase());
            User pp = CoreAPI.getUser(p);

            pp.getBoosters().replace(serverGameType, pp.getBoosters().get(serverGameType) + amount);
            pp.sendPrefixedMessage(ChatColor.YELLOW + "You have received " + ChatColor.GOLD  + amount + "x " + ChatColor.YELLOW
                    + StringUtil.niceString(gameMode.replaceAll("_", "")) + " booster" + (amount == 1 ? "" : "s") + "!");
        }

        else if (subchannel.equalsIgnoreCase("CheckQuests")) {

            String username = in.readUTF();
            Long lastOnlineTime = Long.parseLong(in.readUTF());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(lastOnlineTime));
            calendar.setTimeZone(TimeZone.getTimeZone("GMT"));

            // get last month, get last friday, and get today. if its after any of those then reset the quests.

            // Last month

            Calendar lastMonthCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

            lastMonthCal.add(Calendar.MONTH, -1);

            lastMonthCal.set(Calendar.DAY_OF_MONTH, lastMonthCal.getActualMinimum(Calendar.DAY_OF_MONTH));
            lastMonthCal.set(Calendar.HOUR, 0);
            lastMonthCal.set(Calendar.MINUTE, 0);
            lastMonthCal.set(Calendar.SECOND, 0);

            Date lastMonth = lastMonthCal.getTime();

            // Last week

            Calendar lastWeekCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

            if(lastWeekCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
                while (lastWeekCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
                    lastWeekCal.add(Calendar.DATE, -1);

            lastWeekCal.set(Calendar.HOUR, 7);
            lastWeekCal.set(Calendar.MINUTE, 59);
            lastWeekCal.set(Calendar.SECOND, 59);

            Date lastWeek = lastWeekCal.getTime();

            // Today

            Date lastDay = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();

            // Now to check them

            User paradisePlayer = CoreAPI.getUser(Bukkit.getServer().getPlayer(username));

            if(calendar.getTime().after(lastMonth))
                paradisePlayer.clearQuests(QuestType.MONTHLY);

            if(calendar.getTime().after(lastWeek))
                paradisePlayer.clearQuests(QuestType.WEEKLY);

            if(calendar.getTime().after(lastDay))
                paradisePlayer.clearQuests(QuestType.DAILY);
        }

        else if (subchannel.equalsIgnoreCase("SendServer")) {

            Player p = Bukkit.getPlayer(in.readUTF());
            if (p == null)
                return;

            User pp = CoreAPI.getUser(p);
            pp.sendToServer(in.readUTF());
        }

        else if (subchannel.equalsIgnoreCase("CheckBoosters"))
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> CoreAPI.getBoosterManager().refreshBoosters(), 5L);

        else if (subchannel.equalsIgnoreCase("Vote")) {

            String username = in.readUTF();
            String serviceName = in.readUTF();
            String timeStamp = in.readUTF();

            Player p = Bukkit.getPlayer(username);
            if (p == null)
                return;

            User pp = CoreAPI.getUser(p);
            ServerGameType serverGameType = ServerGameType.values()[r.nextInt(ServerGameType.values().length)];

            pp.giveCoins(serverGameType, 1000, false);
            pp.giveExperience(1000);

            pp.sendPrefixedMessage(ChatColor.GREEN + "You received " + ChatColor.GOLD + "1000 " + serverGameType.getDisplay() + " coins" + ChatColor.GREEN + " and "
                    + ChatColor.BLUE + "1000 experience" + ChatColor.GREEN + " for voting!");
        }

        else if (subchannel.equalsIgnoreCase("ModifyStat")) {

            String playerToModifyName = in.readUTF();
            String statToModify = in.readUTF();
            String amountTOModify = in.readUTF();
            String discreteModify = in.readUTF();

            Player p = Bukkit.getPlayer(playerToModifyName);
            if  (p == null)
                return;

            User user = CoreAPI.getUser(p);
            Stat stat = Stat.fromString(statToModify);
            int amount = Integer.parseInt(amountTOModify);
            boolean discrete = BooleanUtils.toBoolean(discreteModify);

            user.setStat(stat, amount);

            if (!discrete)
                user.sendPrefixedMessage(ChatColor.GREEN + "An admin has updated your " + ChatColor.GOLD + stat.getDisplay()
                        + ChatColor.GREEN + " to " + ChatColor.GOLD + amount + ChatColor.GREEN + ".");
        }
    }

}
