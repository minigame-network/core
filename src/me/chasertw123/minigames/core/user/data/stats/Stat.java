package me.chasertw123.minigames.core.user.data.stats;

import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.utils.StringUtil;
import org.bukkit.ChatColor;

public enum Stat {

    // Non-Server Specific Stats
    EXPERIENCE("Experience", false, null),
    CHESTS_OPENED("Treasure Opened", false, null),
    CRYSTALS("Crystals", false, null),
    COINS("Coins", false, null),
    TOTAL_COINS("Total Coins", false, null),
    FRIEND_SLOTS("Friend Slots", false, null, 25),
    STAT_RESET_TOKEN("Stat Reset Tokens", false, null),

    // Playtime Stats
    WATER_WARS_PLAYTIME("Playtime", false, ServerGameType.WATER_WARS),
    EVOLUTION_PLAYTIME("Playtime", false, ServerGameType.EVOLUTION),
    MCPARTY_PLAYTIME("Playtime", false, ServerGameType.MCPARTY),

    // Water Wars Solo Stats
    WATER_WARS_SOLO_KILLS("Kills (Solo)", ServerGameType.WATER_WARS),
    WATER_WARS_SOLO_DEATHS("Deaths (Solo)", ServerGameType.WATER_WARS),
    WATER_WARS_SOLO_GAMES_WON("Wins (Solo)", ServerGameType.WATER_WARS),
    WATER_WARS_SOLO_GAMES_PLAYED("Games Played (Solo)", ServerGameType.WATER_WARS),
    WATER_WARS_SOLO_MOST_KILLS("Highest Game Kills (Solo)", ServerGameType.WATER_WARS),

    // Water Wars Team Stats
    WATER_WARS_TEAM_KILLS("Kills (Team)", ServerGameType.WATER_WARS),
    WATER_WARS_TEAM_DEATHS("Deaths (Team)", ServerGameType.WATER_WARS),
    WATER_WARS_TEAM_GAMES_WON("Wins (Team)", ServerGameType.WATER_WARS),
    WATER_WARS_TEAM_GAMES_PLAYED("Games Played (Team)", ServerGameType.WATER_WARS),
    WATER_WARS_TEAM_MOST_KILLS("Highest Game Kills (Team)", ServerGameType.WATER_WARS),

    // Water Wars General Stats
    WATER_WARS_CHESTS_OPENED("Chest Opened", ServerGameType.WATER_WARS),
    WATER_WARS_ITEMS_ENCHANTED("Items Enchanted", ServerGameType.WATER_WARS),

    // Evolution Stats
    EVOLUTION_GAMES_WON("Wins", ServerGameType.EVOLUTION),
    EVOLUTION_GAMES_PLAYED("Games Played", ServerGameType.EVOLUTION),
    EVOLUTION_KILLS("Kills", ServerGameType.EVOLUTION),
    EVOLUTION_DEATHS("Deaths", ServerGameType.EVOLUTION),
    EVOLUTION_EVOLVES("Evolutions", ServerGameType.EVOLUTION),
    EVOLUTION_ABILITIES_USED("Abilities Used", ServerGameType.EVOLUTION),
    EVOLUTION_POWERUPS_COLLECTED("Power-Ups Collected", ServerGameType.EVOLUTION),
    EVOLUTION_MOST_KILLS("Highest Game Kills", ServerGameType.EVOLUTION),
    EVOLUTION_MOST_ABILITIES_USED("Highest Game Abilities Used", ServerGameType.EVOLUTION),
    EVOLUTION_MOST_POWERUPS_COLLECTED("Highest Game Power-Ups Collected", ServerGameType.EVOLUTION),

    // MCParty Stats
    MCPARTY_GAMES_WON("Wins", ServerGameType.MCPARTY),
    MCPARTY_GAMES_PLAYED("Games Played", ServerGameType.MCPARTY),
    MCPARTY_TOKENS_COLLECTED("Tokens Collected", ServerGameType.MCPARTY),
    MCPARTY_STARS_COLLECTED("Stars Collected", ServerGameType.MCPARTY),
    MCPARTY_BLUE_SPACE_LANDED_ON("Blue Spaces Landed On", ServerGameType.MCPARTY),
    MCPARTY_RED_SPACES_LANDED_ON("Red Spaces Landed On", ServerGameType.MCPARTY),
    MCPARTY_GREEN_SPACES_LANDED_ON("Green Spaces Landed On", ServerGameType.MCPARTY),
    MCPARTY_LUCKY_SPACES_LANDED_ON("Lucky Spaces Landed On", ServerGameType.MCPARTY),
    MCPARTY_DONKEY_KONG_SPACES_LANDED_ON("[PH] Donkey Kong Spaces Landed On", ServerGameType.MCPARTY),
    MCPARTY_BOWSER_SPACES_LANDED_ON("[PH] Bowser Spaces Landed On", ServerGameType.MCPARTY),
    MCPARTY_MINIGAME_TOKENS_WON("Minigame Points", ServerGameType.MCPARTY),
    MCPARTY_SPACES_TRAVELED("Spaces Traveled", ServerGameType.MCPARTY),
    MCPARTY_CANDY_USED("Candies Eaten", ServerGameType.MCPARTY),
    MCPARTY_CANDY_COLLECTED("Candies Obtained", ServerGameType.MCPARTY),
    MCPARTY_TOKENS_SPENT_SHOPPING("Tokens Spent Shopping", ServerGameType.MCPARTY),
    MCPARTY_MINIGAMES_PLAYED("Minigames Played", ServerGameType.MCPARTY),
    MCPARTY_MINIGAMES_WON("Minigames Won", ServerGameType.MCPARTY),

    // Splegg Stats
    SPLEGG_EGGS_SHOT("Eggs Shot", ServerGameType.SPLEGG),
    SPLEGG_PLAYTIME("Playtime", ServerGameType.SPLEGG),
    SPLEGG_POWERUPS_COLLECTED("Powerups Collected", ServerGameType.SPLEGG),
    SPLEGG_BLOCKS_BROKEN("Blocks Broken", ServerGameType.SPLEGG),
    SPLEGG_GAMES_WON("Games Won", ServerGameType.SPLEGG),
    SPLEGG_GAMES_PLAYED("Games Played", ServerGameType.SPLEGG);

    private String display;
    private boolean canReset;
    private int defaultValue;

    private ServerGameType serverGameType;

    Stat(String display, boolean canReset, ServerGameType serverGameType) {
        this.display = display;
        this.canReset = canReset;
        this.serverGameType = serverGameType;
        this.defaultValue = 0;
    }

    Stat(String display, ServerGameType serverGameType) {
        this(display, true, serverGameType);
    }

    Stat(String display, boolean canReset, ServerGameType serverGameType, int defaultValue) {
        this(display, serverGameType);
        this.canReset = canReset;
        this.defaultValue = defaultValue;
    }

    Stat(String display, ServerGameType serverGameType, int defaultValue) {
        this(display, true, serverGameType, defaultValue);
    }

    public String getDisplay() {
        return display;
    }

    public boolean canReset() {
        return canReset;
    }

    public ServerGameType getServerGameType() {
        return serverGameType;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public String toLore(User user) {

        if (toString().contains("PLAYTIME"))
            return ChatColor.WHITE + display + ChatColor.GRAY + ": " + StringUtil.toFriendlyTimeFormat(user.getStat(this));

        return ChatColor.WHITE + display + ChatColor.GRAY + ": " + user.getStat(this);
    }

    public static Stat fromString(String s) {

        Stat foundStat = null;
        for (Stat stat : Stat.values())
            if (stat.toString().equalsIgnoreCase(s)) {
                foundStat = stat;
                break;
            }

        return foundStat;
    }
}
