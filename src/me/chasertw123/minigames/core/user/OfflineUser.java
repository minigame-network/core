package me.chasertw123.minigames.core.user;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.infraction.Infraction;
import me.chasertw123.minigames.shared.infraction.Punishment;
import me.chasertw123.minigames.shared.infraction.PunishmentTimeScale;
import me.chasertw123.minigames.shared.infraction.PunishmentType;
import me.chasertw123.minigames.shared.rank.Rank;
import me.chasertw123.minigames.shared.rank.RankType;
import me.chasertw123.minigames.shared.user.iUser;
import me.chasertw123.minigames.shared.utils.StringUtil;
import net.md_5.bungee.api.ChatColor;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static me.chasertw123.minigames.shared.infraction.Punishment.BASE_MUTE_POINTS;
import static me.chasertw123.minigames.shared.infraction.Punishment.PERMANENT_BAN_POINTS;

public class OfflineUser implements iUser {

    private UUID uuid;
    private String username;
    private long lastOnline, deluxe;
    private Rank rank;

    private List<Infraction> infractions;
    private List<Punishment> punishments;
    private int infractionPoints;

    /**
     * Creates a new {@link OfflineUser} instance
     * @param uuid UUID of player
     * @param username Last known username of player
     * @param rank Rank of player
     * @param lastOnline Last time they were online
     */
    public OfflineUser(UUID uuid, String username, Rank rank, long lastOnline, List<Infraction> infractions, int infractionPoints, List<Punishment> punishments, long deluxe) {
        System.out.println("UUID" + uuid);
        System.out.println("Username " + username);
        System.out.println("Rank " + rank);
        System.out.println("Last Online " + lastOnline);
        System.out.println("Infractions " + infractions.toString());
        System.out.println("Punishments " + punishments.toString());
        System.out.println("Infraction Points " + infractionPoints);
        System.out.println("Deluxe " + deluxe);

        this.uuid = uuid;
        this.deluxe = deluxe;
        this.username = username;
        this.infractionPoints = infractionPoints;
        this.rank = rank;
        this.lastOnline = lastOnline;
        this.infractions = infractions;
        this.punishments = punishments;
    }

    public OfflineUser(Document userData, Document userInfractionsData, Document userPunishmentsData) {
        this.uuid = UUID.fromString(userData.getString("uuid"));
        this.username = userData.getString("lastknownusername");
        this.rank = Rank.valueOf(userData.getString("rank"));
        this.deluxe = userData.getLong("deluxe");
        this.infractions = (userInfractionsData == null ? new ArrayList<>() : Infraction.serializeInfractions(this.uuid, userInfractionsData));
        this.punishments = (userPunishmentsData == null ? new ArrayList<>() : Punishment.serializePunishments(this.uuid, userPunishmentsData));
        this.infractionPoints = 0;
        this.infractions.forEach(inf -> this.infractionPoints += inf.getInfractionValue());
        this.lastOnline = 0; //TODO: This
    }

    public OfflineUser(UUID uuid) {
        // THIS IS A NEW USER

        if(Bukkit.getPlayer(uuid) == null) {
            System.out.println("New user and object is null!");

            return;
        }

        Player p = Bukkit.getPlayer(uuid);

        this.uuid = uuid;
        this.username = p.getName();
        this.deluxe = 0;
        this.rank = Rank.MEMBER;
        this.infractions = new ArrayList<>();
        this.punishments = new ArrayList<>();
        this.infractionPoints = 0;
        this.lastOnline = 0;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getUsername(){
        return username;
    }

    public Rank getRank(){
        return rank;
    }

    public List<Infraction> getInfractions() {
        return infractions;
    }

    public List<Punishment> getPunishments() {
        return punishments;
    }

    public long getDeluxe() {
        return deluxe;
    }

    public boolean isDeluxe() {
        return deluxe > System.currentTimeMillis() || rank.getRankType().getRankLevel() >= RankType.STAFF.getRankLevel();
    }

    /**
     * Set the expiration time for player's deluxe
     * @param deluxe time at which player's deluxe will expire
     */
    public void setDeluxe(long deluxe) {
        this.deluxe = deluxe;
    }

    /**
     * Sets the player's global server rank
     * @param rank Rank to set the player to
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * Gets the last time a player was online on the entire network
     * @return last time player was on network
     */
    public long getLastOnline() {
        return lastOnline;
    }


    /**
     * Adds an {@link Infraction} to a player's account. May result in
     * player being muted or being banned
     * @param infraction {@link Infraction} applying to player
     */
    public void addInfraction(Infraction infraction) {
        this.infractions.add(infraction);
        this.infractionPoints += infraction.getInfractionValue();

        int infractionPoints = this.getInfractionPoints();

        Punishment punishment = null;
        if (infractionPoints >= BASE_MUTE_POINTS) {

            int muteCount = 0, banCount = 0;
            for (Punishment p : this.getPunishments()) {
                if (p.getType() == PunishmentType.BAN)
                    banCount++;
                else
                    muteCount++;
            }

            PunishmentType punishmentType = infractionPoints > (PunishmentTimeScale.values().length * 5) + BASE_MUTE_POINTS ? PunishmentType.BAN : PunishmentType.MUTE;
            PunishmentTimeScale punishmentTimeScale = PunishmentTimeScale.values()[punishmentType == PunishmentType.BAN ? banCount : muteCount];

            if (punishmentType == PunishmentType.MUTE) {
                int scaleCount = (infractionPoints - BASE_MUTE_POINTS) / 5;
                punishmentTimeScale = PunishmentTimeScale.values()[(muteCount + scaleCount) > PunishmentTimeScale.values().length
                        ? PunishmentTimeScale.PERMANENT.ordinal() : muteCount + scaleCount];
            }

            if (punishmentType == PunishmentType.BAN && infractionPoints >= PERMANENT_BAN_POINTS)
                punishmentTimeScale = PunishmentTimeScale.PERMANENT;

            punishment = new Punishment(punishmentType, punishmentTimeScale);
            this.setInfractionPoints(0);

            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            out.writeUTF("Punishment");
            out.writeUTF(this.getUsername());
            out.writeUTF(punishment.toString().toUpperCase());

            if (punishmentType == PunishmentType.BAN) {
                out.writeUTF(ChatColor.RED + "You're Banned! Your ban " + (punishment.getTimeScale() == PunishmentTimeScale.PERMANENT
                        ? "is " + ChatColor.YELLOW + "PERMANENT" : ChatColor.YELLOW + "EXPIRES" + ChatColor.RED + " in "
                        + ChatColor.YELLOW + punishment.getTimeRemaining()) + ChatColor.RED + ".");
            } else {
                out.writeUTF(punishmentTimeScale.toString().toUpperCase());
                out.writeUTF(System.currentTimeMillis() + "");
            }

            if (Bukkit.getServer().getPlayer(this.getUUID()) != null)
                Bukkit.getServer().getPlayer(this.getUUID()).sendPluginMessage(Main.getInstance(), "MCParadise", out.toByteArray());
        }

        if (punishment == null)
            return;

//        SQLDatabase.savePunishment(uuid, punishment);
        punishments.add(punishment);
    }

    /**
     * Get the current amount of infraction points a player has.
     * @return amount of infraction points
     */
    public int getInfractionPoints() {
        return infractionPoints;
    }

    /**
     * Set amount of infraction points a player has. Will not result
     * in a ban of mute if applying infraction points.
     * @param infractionPoints Amount of Infraction points to set to the player
     */
    public void setInfractionPoints(int infractionPoints) {
        this.infractionPoints = infractionPoints;
    }

    /**
     * Creates an cItemStack to represent the player's data
     * @return cItemStack representing player
     */
    public cItemStack toItemStack() {
        return new cItemStack(getUsername(), ChatColor.GREEN + getUsername()).addLore(ChatColor.GRAY + "Rank: " + getRank().getRankColor() + getRank().toString(),
                ChatColor.GRAY + "Last Online: " + ChatColor.GOLD + StringUtil.compareDateToNow(new Date(getLastOnline())));
    }

}
