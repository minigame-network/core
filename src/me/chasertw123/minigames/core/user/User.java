package me.chasertw123.minigames.core.user;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mongodb.client.model.Filters;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.database.GenericDatabaseMethods;
import me.chasertw123.minigames.core.features.chests.ChestType;
import me.chasertw123.minigames.core.features.quests.Quest;
import me.chasertw123.minigames.core.features.quests.QuestType;
import me.chasertw123.minigames.core.user.data.reports.Report;
import me.chasertw123.minigames.core.user.data.settings.Setting;
import me.chasertw123.minigames.core.event.AchievementUnlockEvent;
import me.chasertw123.minigames.core.event.UserQuitEvent;
import me.chasertw123.minigames.core.event.UserToggleSettingEvent;
import me.chasertw123.minigames.core.user.data.VoteSite;
import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.core.utils.items.AbstractItem;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.core.utils.scoreboard.Scoreboard;
import me.chasertw123.minigames.shared.booster.GameBooster;
import me.chasertw123.minigames.shared.database.Database;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.framework.ServerSetting;
import me.chasertw123.minigames.shared.framework.ServerType;
import me.chasertw123.minigames.shared.user.iUser;
import me.chasertw123.minigames.shared.utils.MessageUtil;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class User extends OfflineUser implements iUser {

    private static final int MAX_LEVEL = 500, BASE_XP = 1000;

    private long joinDate;
    private int gainedCoins = 0, gainedXp = 0;
    private boolean loaded = false, leveled = false, sendingToServer = false, spectating = false;

    private VoteSite currentVoteSite = null;

    private List<UUID> friends = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();
    private List<Achievement> achievements = new ArrayList<>();

    private TreeMap<Long, String> recentActivity = new TreeMap<>();
    private TreeMap<Long, Quest> recentQuests = new TreeMap<>();
    private TreeMap<Long, Achievement> recentAchievements = new TreeMap<>();

    private HashMap<ChestType, Integer> chests = new HashMap<>();
    private HashMap<ServerSetting, Boolean> playerServerSettings = new HashMap<>();
    private HashMap<VoteSite, Long> votes = new HashMap<>();
    private HashMap<Setting, Boolean> settings = new HashMap<>();
    private HashMap<Stat, Integer> stats =  new HashMap<>();
    private HashMap<ServerGameType, Integer> boosters = new HashMap<>();
    private HashMap<Quest, Integer> quests = new HashMap<>();

    private Map<Integer, AbstractItem> abstractItems = new HashMap<>();
    private Scoreboard scoreboard = null;
    private AbstractGui gui = null;

    /**
     * Creates a new {@link User} by checking if the database
     * contains any data, and if not it will set the default values
     *
     * @param ou {@link OfflineUser} you are making an Online instance of
     */
    @SuppressWarnings("unchecked")
    public User(OfflineUser ou) {
        super(ou.getUUID(), ou.getUsername(), ou.getRank(), 0, ou.getInfractions(), ou.getInfractionPoints(), ou.getPunishments(), ou.getDeluxe());

        long start = System.currentTimeMillis();
        Database database = CoreAPI.getDatabase();

        if (GenericDatabaseMethods.containsUser(database.getMongoCollection(Database.Collection.CORE_USER), ou.getUUID())) {

            Document userData = database.getMongoCollection(Database.Collection.CORE_USER)
                    .find(Filters.eq("uuid", ou.getUUID().toString())).first();

            ((Document) userData.get("stats")).keySet().forEach(obj -> this.stats.put(Stat.valueOf(obj), ((Document) userData.get("stats")).getInteger(obj)));
            ((Document) userData.get("chests")).keySet().forEach(obj -> this.chests.put(ChestType.valueOf(obj), ((Document) userData.get("chests")).getInteger(obj)));
            ((Document) userData.get("boosters")).keySet().forEach(obj -> this.boosters.put(ServerGameType.valueOf(obj), ((Document) userData.get("boosters")).getInteger(obj)));
            ((Document) userData.get("settings")).keySet().forEach(obj -> this.settings.put(Setting.valueOf(obj), ((Document) userData.get("settings")).getBoolean(obj)));
            ((Document) userData.get("votes")).keySet().forEach(obj -> this.votes.put(VoteSite.valueOf(obj), ((Document) userData.get("votes")).getLong(obj)));
            ((Document) userData.get("activity")).keySet().forEach(obj -> this.recentActivity.put(Long.valueOf(obj), ((Document) userData.get("activity")).getString(obj)));

            ((List<String>) userData.get("achievements")).forEach(obj -> this.achievements.add(Achievement.valueOf(obj)));
            ((List<String>) userData.get("reports")).forEach(obj-> this.reports.add(new Report(obj)));
            ((List<String>) userData.get("friends")).forEach(obj -> this.friends.add(UUID.fromString(obj)));

            this.joinDate = userData.getLong("joined");
            this.leveled = userData.getBoolean("leveled");
        }

        else
            this.joinDate = System.currentTimeMillis();

        this.currentVoteSite = VoteSite.getRandomVoteSite();
        Arrays.stream(ServerSetting.values()).filter(ServerSetting::effectsPlayer).forEach(obj -> this.playerServerSettings.put(obj, obj.getDefaultValue()));
        Arrays.stream(ChestType.values()).filter(obj -> !this.chests.containsKey(obj)).forEach(obj -> this.chests.put(obj, 0));
        Arrays.stream(Stat.values()).filter(obj -> !this.stats.containsKey(obj)).forEach(obj -> this.stats.put(obj, obj.getDefaultValue()));
        Arrays.stream(Setting.values()).filter(obj -> !this.settings.containsKey(obj)).forEach(obj -> this.settings.put(obj, obj.getDefaultValue()));
        Arrays.stream(ServerGameType.values()).filter(obj -> !this.boosters.containsKey(obj)).forEach(obj -> this.boosters.put(obj, 0));
        Arrays.stream(VoteSite.values()).filter(obj -> !this.votes.containsKey(obj)).forEach(obj -> this.votes.put(obj, 0L));

        System.out.println("Loaded " + getPlayer().getName() + "'s Core Data in: " + (System.currentTimeMillis() - start) + "ms");
    }

    public String getColoredName() {
        return getRank().getRankColor() + getPlayer().getName();
    }

    public HashMap<Quest, Integer> getQuests() {
        return quests;
    }

    public void addFriend(UUID uuid) {
        friends.add(uuid);
    }

    public void removeFriend(UUID uuid) {
        friends.remove(uuid);
    }

    public void setQuests(HashMap<Quest, Integer> quests) {
        this.quests = quests;
    }

    public void clearQuests(QuestType questType) {
        quests.keySet().stream().filter(quest -> quest.getQuestType() == questType).collect(Collectors.toList()).forEach(quests::remove);
    }

    public int getChests(ChestType rarity) {
        return chests.get(rarity);
    }

    public void giveChests(ChestType rarity, int amount) {
        chests.put(rarity, this.getChests(rarity) + amount);
    }

    public void giveChest(ChestType rarity) {
        this.giveChests(rarity, 1);
    }

    public int getTotalChests() {
        int total = 0;
        for (ChestType rarity : ChestType.values())
            total += this.getChests(rarity);

        return total;
    }

    /**
     * Gets the {@link Player} of this {@link User}
     * @return Player of this {@link User}
     */
    public Player getPlayer(){
        return Bukkit.getServer().getPlayer(getUUID());
    }

    /**
     * Sends a message to the player
     * @param message {@link String} that is sent to the player
     */
    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    /**
     * Sends a message to the player with the plugin's prefix in front of it
     * @param message {@link String} that is sent to the player
     */
    public void sendPrefixedMessage(String message) {
        getPlayer().sendMessage(CoreAPI.PREFIX + message);
    }

    /**
     * Checks if a player is being sent to another server instead of disconnecting from proxy
     * @return if player is being sent to another server
     */
    public boolean isSendingToServer() {
        return sendingToServer;
    }

    public void togglePlayerVisibility(boolean visibility) {
        if (CoreAPI.getServerDataManager().getServerType() == ServerType.HUB)
            Bukkit.getServer().getOnlinePlayers().stream()
                    .filter(player -> player.getUniqueId() != getUUID())
                    .forEach(player -> {
                        if (visibility)
                            getPlayer().showPlayer(player);

                        else
                            getPlayer().hidePlayer(player);
                    });
    }

    /**
     * Gets the list of players on player's friends list
     * @return List of UUIDs
     */
    public List<UUID> getFriends() {
        return friends;
    }

    /**
     * Get the amount of coins gained while on the server.
     * Resets on join and disconnect.
     * @return amount of coins get on this server
     */
    public int getGainedCoins() {
        return gainedCoins;
    }

    /**
     * Get the amount of xp gained while on this server.
     * Resets on join and disconnect.
     * @return amount of xp gained on this server
     */
    public int getGainedXp() {
        return gainedXp;
    }

    /**
     * Gets a specific setting
     * @param setting Setting to check value of
     * @return Boolean of setting
     */
    public boolean getSetting(Setting setting) {
        return settings.get(setting);
    }

    /**
     * Sets the value of a {@link Setting}
     * @param setting {@link Setting} you are setting the value
     * @param value {@link Boolean} you are setting
     */
    public void setSetting(Setting setting, boolean value) {
        this.settings.put(setting, value);
        Bukkit.getServer().getPluginManager().callEvent(new UserToggleSettingEvent(this, setting, value));
    }

    /**
     * Gets a specific setting
     * @param setting Setting to check value of
     * @return Boolean of setting
     */
    public boolean getServerSetting(ServerSetting setting) {
        return playerServerSettings.get(setting);
    }

    /**
     * Sets the value of a {@link Setting}
     * @param setting {@link Setting} you are setting the value
     * @param value {@link Boolean} you are setting
     */
    public void setServerSetting(ServerSetting setting, Boolean value) {
        this.playerServerSettings.replace(setting, value);
    }

    /**
     * GEts last time a player voted for a specific website
     * @param voteSite {@link VoteSite} checking when last voted for
     * @return Long of when last voted for {@link VoteSite}
     */
    public long getLastVoted(VoteSite voteSite) {
        return votes.get(voteSite);
    }

    /**
     * Sets when a player last voted on a {@link VoteSite}
     * @param voteSite {@link VoteSite} player voted on
     */
    public void setLastVoted(VoteSite voteSite) {
        this.votes.put(voteSite, System.currentTimeMillis());
    }

    /**
     * Used to give coins. Do not use #setStat to give coins as it will
     * not count boosters or towards gained Coins
     * @param serverGameType Type of coins to give
     * @param amount Amount of coins to give
     * @param tell Tell the player how many coins they gained
     */
    public void giveCoins(ServerGameType serverGameType, int amount, boolean tell) {

        int booster = 1, eventBooster = 0;
        if (CoreAPI.getBoosterManager().eventBoosterActivated() && CoreAPI.getServerDataManager().getServerType() != ServerType.HUB)
            eventBooster = CoreAPI.getBoosterManager().getCurrentEventBooster().getMultiplier();

        booster = eventBooster > 0 ? eventBooster : 1;

        String player = null;
        if (CoreAPI.getBoosterManager().getCurrentGameBoosters().size() > 0)
            for (GameBooster gameBooster : CoreAPI.getBoosterManager().getCurrentGameBoosters())
                if (ServerGameType.valueOf(gameBooster.getGameMode()) == serverGameType) {
                    booster += gameBooster.getMultiplier();
                    player = gameBooster.getActivatorName();
                    break;
                }

        gainedCoins += amount * booster;

        incrementStat(Stat.COINS, amount * booster);
        incrementStat(Stat.TOTAL_COINS, amount * booster);

        if (tell) {

            String message = ChatColor.GOLD + "+" + (amount * booster) + " Coins";
            if (player != null && eventBooster > 0)
                message = ChatColor.GOLD + "+" + (amount * booster) + " Coins (" + eventBooster + "x Event Booster, " + player + "'s Booster)";

            else if (player == null && eventBooster > 0)
                message = ChatColor.GOLD + "+" + (amount * booster) + " Coins (" + eventBooster + "x Event Booster)";

            else if (player != null && eventBooster == 0)
                message = ChatColor.GOLD + "+" + (amount * booster) + " Coins (" + player + "'s Booster)";

            this.sendMessage(message);
        }
    }

    /**
     * Used to give xp. Do not use #setStat to give xp as it will
     * not count boosters or towards gained xp
     * @param amount Amount of xp to give
     */
    public void giveExperience(int amount) {

        int booster = 1;
        if (CoreAPI.getBoosterManager().eventBoosterActivated())
            booster = CoreAPI.getBoosterManager().getCurrentEventBooster().getMultiplier();

        gainedXp += amount * booster;

        int currentLevel = getLevel();
        incrementStat(Stat.EXPERIENCE, amount * booster);
        int newLevel = getLevel();

        ServerType serverType = CoreAPI.getServerDataManager().getServerType();
        if (serverType == ServerType.HUB) {
            float xp = ((float) this.getStat(Stat.EXPERIENCE) / this.getLevelExperience());
            this.getPlayer().setExp(xp >= 1 ? 0.9999999F : xp);
        }

        if (newLevel == currentLevel)
            return;

        if (CoreAPI.getServerDataManager().getServerType() == ServerType.HUB) {
            this.getPlayer().playSound(this.getPlayer().getPlayer().getLocation(), Sound.LEVEL_UP, 1.25F, 0.5F);
            this.sendPrefixedMessage(ChatColor.YELLOW + "You leveled up! You're now level " + ChatColor.LIGHT_PURPLE + this.getLevel() + ChatColor.YELLOW + "!");
            return;
        }

        setLeveled(true);
    }

    /**
     * Gets the current MCParadise level of the player
     * @return MCParadise level of player
     */
    public int getLevel() {
        for (int level = 1; level <= MAX_LEVEL; level++)
            if (getStat(Stat.EXPERIENCE) < (level * Math.log10(level) * BASE_XP))
                return (level - 1);

        return MAX_LEVEL;
    }

    /**
     * Gets the total amount of xp for a MCParadise level
     * @return xp for MCParadise level
     */
    public int getLevelExperience() {
        int level = getLevel();
        return (int) ((level + 1) * Math.log10(level + 1) * BASE_XP);
    }

    /**
     * Gets the xp till next MCParadise level
     * @return xp till MCParadise level
     */
    public int getExperienceToNextLevel() {
        return getLevelExperience() - getStat(Stat.EXPERIENCE);
    }

    /**
     * Gets a specific stat of the player
     * @param stat {@link Stat} requested
     * @return Integer of stat requested
     */
    public int getStat(Stat stat) {
        return stats.get(stat);
    }

    /**
     * Sets the value of a {@link Stat}
     * @param stat {@link Stat} you are setting the value of
     * @param i Value you are setting to {@link Stat}
     */
    public void setStat(Stat stat, int i) {
        stats.put(stat, i);
        Arrays.stream(Achievement.values())
                .filter(achievement -> stat == achievement.getStat() && getStat(stat) >= achievement.getStatAmount() && !hasAchievement(achievement))
                .forEach(this::unlockAchievement);
    }

    /**
     * Increments a {@link Stat} by a value
     * @param stat {@link User} you are incrementing
     * @param i Amount you are incrementing {@link Stat} by
     */
    public void incrementStat(Stat stat, int i) {
        setStat(stat, getStat(stat) + i);
    }

    /**
     * Increments a {@link Stat} by 1
     * @param stat {@link User} you are incrementing
     */
    public void incrementStat(Stat stat) {
        incrementStat(stat, 1);
    }

    /**
     * Decrements a {@link Stat} by a value
     * @param stat {@link User} you are decrementing
     * @param i Amount you are decrementing {@link Stat} by
     */
    public void decrementStat(Stat stat, int i) {
        setStat(stat, getStat(stat) - i);
    }

    /**
     * Decrements a {@link Stat} by 1
     * @param stat {@link User} you are decrementing
     */
    public void decrementStat(Stat stat) {
        setStat(stat, getStat(stat) - 1);
    }

    /**
     * Gets all the achievements for the player
     * @return List {@link me.chasertw123.minigames.core.user.data.achievements.Achievement} for the player
     */
    public List<Achievement> getAchievements() {
        return achievements;
    }

    /**
     * Checks if player has {@link Achievement} unlocked
     * @param achievement {@link Achievement} you are checking
     * @return Boolean is {@link Achievement} is unlocked
     */
    public boolean hasAchievement(Achievement achievement) {
        return achievements.contains(achievement);
    }

    /**
     * Unlocks a {@link Achievement} and displays a message to the player along
     * with sending a packet to bungee with achievement information to be broadcast if
     * {@link me.chasertw123.minigames.core.user.data.achievements.AchievementDifficulty} is Insane or higher
     * @param achievement {@link Achievement} unlocked
     */
    public void unlockAchievement(Achievement achievement) {

        if (getAchievements().contains(achievement))
            return;

        Bukkit.getServer().getPluginManager().callEvent(new AchievementUnlockEvent(this, achievement));

        getAchievements().add(achievement);

        // Send centered message to the player

        sendCenteredMessage("&a&m-------&r &f&lAchievement Get!&r &a&m-------", true);
        sendCenteredMessage("");
        sendCenteredMessage(achievement.getAchievementDifficulty().getChatColor() + achievement.getDisplay(), true);
        sendCenteredMessage("&7" + achievement.getDescription(), true);
        sendCenteredMessage("");
        sendCenteredMessage("&a&m------------------------------", true);

        getPlayer().playSound(getPlayer().getLocation(), Sound.FIREWORK_LARGE_BLAST, 1F, 1F);

        // TODO: Send packet to bungee on epic
    }

    public void sendCenteredMessage(String message) {
        sendCenteredMessage(message, false);
    }

    public void sendCenteredMessage(String message, boolean translate) {
        getPlayer().sendMessage(MessageUtil.createCenteredMessage(translate ? ChatColor.translateAlternateColorCodes('&', message) : message));
    }

    /**
     * Returns a list of the player's recent activity
     * @return HashMap that contains a string and long
     */
    public TreeMap<Long, String> getRecentActivity() {
        return recentActivity;
    }

    /**
     * Adds a recent activity and removes any that exceed the limit
     * @param activity String activity player just completed
     */
    public void addActivity(String activity) {

        long currentTime = System.currentTimeMillis();
        recentActivity.put(currentTime, activity);

        if (recentActivity.size() > 15)
            recentActivity.remove(recentActivity.firstKey());
    }

    /**
     * Get a player's boosters
     * @return player's boosters
     */
    public HashMap<ServerGameType, Integer> getBoosters() {
        return boosters;
    }

    /**
     * Set player's current scoreboard
     * @param scoreboard Scoreboard player will be seeing
     */
    public void setScoreboard(Scoreboard scoreboard) {
        removeScoreboard();
        this.scoreboard = scoreboard;
        scoreboard.activate();
    }

    /**
     * Remove player's active scoreboard
     */
    public void removeScoreboard() {
        if (scoreboard != null)
            scoreboard.deactivate();
    }

    /**
     * Gets the player's current Gui can be null if they don't have an open gui
     * @return AbstractGui that the player has open
     */
    public AbstractGui getCurrentGui() {
        return gui;
    }

    /**
     * Sets the player's gui and closes any gui the player has open
     * @param gui AbstractGui to display to the player
     */
    public void setCurrentGui(AbstractGui gui) {
        this.gui = gui;
    }

    /**
     * Gets list of {@link AbstractItem} player has
     * @return List of {@link AbstractItem}
     */
    public Map<Integer, AbstractItem> getAbstractItems() {
        return abstractItems;
    }

    /**
     * Gets all the reports submitted against the player
     * @return List of Reports
     */
    public List<Report> getReports() {
        return reports;
    }

    /**
     * Add a report to a player's account
     * @param report {@link Report} being saved to this account
     */
    public void addReport(Report report){
        this.reports.add(report);
    }

    /**
     * Check if a player leveled up during a game
     * @return if leveled up during a game
     */
    public boolean hasLeveled() {
        return leveled;
    }

    /**
     * Set if player leveled during a game
     * @param leveled if leveled up during game
     */
    public void setLeveled(boolean leveled) {
        this.leveled = leveled;
    }

    /**
     * Gets the exact time when the player joined the server for the first time
     * @return when a player joined for the first time
     */
    public long getJoinDate() {
        return joinDate;
    }

    /**
     * Gets the current site we want the player to vote for
     * @return Site player needs to vote for before we give a new site
     */
    public VoteSite getCurrentVoteSite() {
        return currentVoteSite;
    }

    /**
     * Sets the current vote site we are giving to the player
     * @param currentVoteSite {@link VoteSite} we are feeding to the player
     */
    public void setCurrentVoteSite(VoteSite currentVoteSite) {
        this.currentVoteSite = currentVoteSite;
    }

    public void playParticleEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount) {
        CoreAPI.getOnlinePlayers()
                .stream()
                .filter(pp -> !pp.getUsername().equals(this.getUsername()) && pp.getSetting(Setting.PLAYER_VISIBILITY))
                .forEach(pp -> pp.getPlayer().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, 48));

        if (this.getPlayer() != null)
            this.getPlayer().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, 48);
    }

    public void playWorldSound(Location location, Sound sound, float volume, float pitch) {
        CoreAPI.getOnlinePlayers()
                .stream()
                .filter(pp -> !pp.getUsername().equals(this.getUsername()) && pp.getSetting(Setting.PLAYER_VISIBILITY))
                .forEach(pp -> pp.getPlayer().playSound(location, sound, volume, pitch));

        if (this.getPlayer() != null)
            this.getPlayer().playSound(location, sound, volume, pitch);
    }

    /**
     * Crafts a cItemStack to quickly some up information about this player
     * @return cItemStack of player head
     */
    @Override
    public cItemStack toItemStack() {
        return new cItemStack(getUsername(), ChatColor.GREEN + getUsername()).addLore(ChatColor.GRAY + "Rank: " + getRank().getRankColor() + getRank().toString(), "",
                ChatColor.GRAY + "Level: " + ChatColor.GOLD + this.getLevel(),
                ChatColor.GRAY + "Experience Left: " + ChatColor.GOLD + (this.getExperienceToNextLevel() <= 0 ? "MAX LEVEL" : this.getExperienceToNextLevel()), "",
                ChatColor.GRAY + "Friends: " + ChatColor.GOLD + friends.size(), "",
                ChatColor.GRAY + "Achievements Completed: " + ChatColor.GOLD + achievements.size(),
                ChatColor.GRAY + "Chests Opened: " + ChatColor.GOLD + getStat(Stat.CHESTS_OPENED));
    }

    /**
     * Sends packet to Bungee to put player in queue
     * @param server gamemode to queue player for
     */
    public void addToQueue(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("AddQueue");
        out.writeUTF(getUsername());
        out.writeUTF(server);

        getPlayer().sendPluginMessage(Main.getInstance(), "MCParadise", out.toByteArray());
    }

    /**
     * Sends the player to specific server
     * @param server {@link String} id of the server to send player to
     */
    public void sendToServer(String server) {

        Bukkit.getPluginManager().callEvent(new UserQuitEvent(this, UserQuitEvent.QuitType.SENDING_TO_SERVER));

        if (!savePlayerData())
            System.err.println("FAILED TO SAVE " + getUsername() + "'s USER DATA!");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(server);

        sendingToServer = true;
        getPlayer().sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
    }

    /**
     * Saves the player data
     * @return boolean if data saved
     */
    public boolean savePlayerData() {
        return GenericDatabaseMethods.saveUserData(this);
    }
}
