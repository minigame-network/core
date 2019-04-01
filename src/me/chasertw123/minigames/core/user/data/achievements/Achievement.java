package me.chasertw123.minigames.core.user.data.achievements;

import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.shared.framework.ServerGameType;

public enum Achievement {

    // General Achievements
    HELLO_WORLD("Hello world!", AchievementDifficulty.NOVICE, "Say something in chat"),
    PLAY_SOMETHING("Play something", AchievementDifficulty.NOVICE, "Join in on the fun! Play any minigame"),
    FIND_A_FRIEND("Find a Friend", AchievementDifficulty.NOVICE, "Make a friend using the friends system"),
    PARTY_TIME("Party Time", AchievementDifficulty.NOVICE, "Party up with another player"),
    PERSONAL_BOOSTER("Personal Boost", AchievementDifficulty.MEDIUM, "Activate any type of personal boost"),
    PERSONAL_BANKER("Personal Banker", AchievementDifficulty.INSANE, "Have a total of 1,000,000 coins saved up"),

    TOTAL_COINS_I("First Pay Day", AchievementDifficulty.NOVICE, "Earn a total of 1,000 coins"),
    TOTAL_COINS_II("Makin' Bank", AchievementDifficulty.EASY, "Earn a total of 100,000 coins"),
    TOTAL_COINS_III("Makin' it Rain", AchievementDifficulty.MEDIUM, "Earn a total of 500,000 coins"),
    TOTAL_COINS_IV("First Million", AchievementDifficulty.HARD, "Earn a total of 1,000,000 coins"),
    TOTAL_COINS_V("How to even spend it?", AchievementDifficulty.INSANE, "Earn a total of 5,000,000 coins"),
    TOTAL_COINS_VI("True Baller", AchievementDifficulty.EPIC, "Earn a total of 100,000,000 coins"),

    // Splegg
    SPLEGG_FIRST_TO_DIE(ServerGameType.SPLEGG, "First to Die", AchievementDifficulty.EASY, "Die First"),
    SPLEGG_TNT(ServerGameType.SPLEGG, "I'm Dynamite!", AchievementDifficulty.EASY, "Ignite a block of TNT with your eggs"),
    SPLEGG_GOD(ServerGameType.SPLEGG, "SPLEGG GOD!!", AchievementDifficulty.INSANE, "Win a game of Splegg without shooting any eggs"),
    SPLEGG_TRIGGER(ServerGameType.SPLEGG, "TRIGGER HAPPY", AchievementDifficulty.MEDIUM, "Shoot the most eggs in your game"),
    SPLEGG_BLOCK(ServerGameType.SPLEGG, "Aim + Clicks = Win", AchievementDifficulty.MEDIUM, "Destroy the most blocks in your game"),

    // Water Wars Achievements
    WATER_WARS_WATERY_GRAVE(ServerGameType.WATER_WARS, "Watery Grave", AchievementDifficulty.NOVICE, "Die from taking too long of a swim"),
    WATER_WARS_ONE_PUNCH(ServerGameType.WATER_WARS, "ONEEEE PUNCHHH!!!", AchievementDifficulty.MEDIUM, "Finish someone off with your fists"),
    WATER_WARS_DOMINATION(ServerGameType.WATER_WARS, "Domination", AchievementDifficulty.INSANE, "Get 24 kills in one solo game"),
    WATER_WARS_BULLSEYE(ServerGameType.WATER_WARS, "Bullseye", AchievementDifficulty.EASY, "Kill someone with a bow and arrow"),
    WATER_WARS_CAGE(ServerGameType.WATER_WARS, "Pimp my Cage", AchievementDifficulty.EASY, "Unlock your first cage"),
    WATER_WARS_KIT(ServerGameType.WATER_WARS, "Pimp my Kit", AchievementDifficulty.EASY, "Unlock your first kit"),
    WATER_WARS_PERK(ServerGameType.WATER_WARS, "Commando Pro", AchievementDifficulty.EASY, "Buy your first perk"),

    WATER_WARS_TEAM_WINS_I("Water Champion I", AchievementDifficulty.NOVICE, "Win 1 game (team)", Stat.WATER_WARS_TEAM_GAMES_WON, 1),
    WATER_WARS_TEAM_WINS_II("Water Champion II", AchievementDifficulty.EASY, "Win 10 games (team)", Stat.WATER_WARS_TEAM_GAMES_WON, 10),
    WATER_WARS_TEAM_WINS_III("Water Champion III", AchievementDifficulty.MEDIUM, "Win 100 games (team)", Stat.WATER_WARS_TEAM_GAMES_WON, 100),
    WATER_WARS_TEAM_WINS_IV("Water Champion IV", AchievementDifficulty.HARD, "Win 500 games (team)", Stat.WATER_WARS_TEAM_GAMES_WON, 500),
    WATER_WARS_TEAM_WINS_V("Water Champion V", AchievementDifficulty.INSANE, "Win 1,000 games (team)", Stat.WATER_WARS_TEAM_GAMES_WON, 1000),
    WATER_WARS_TEAM_WINS_VI("Water Champion VI", AchievementDifficulty.EPIC, "Win 5,000 games (team)", Stat.WATER_WARS_TEAM_GAMES_WON, 5000),

    WATER_WARS_TEAM_KILLS_I("Team Slayer I", AchievementDifficulty.NOVICE, "Get 20 total kills (team)", Stat.WATER_WARS_TEAM_KILLS, 20),
    WATER_WARS_TEAM_KILLS_II("Team Slayer II", AchievementDifficulty.EASY, "Get 100 total kills (team)", Stat.WATER_WARS_TEAM_KILLS, 100),
    WATER_WARS_TEAM_KILLS_III("Team Slayer III", AchievementDifficulty.MEDIUM, "Get 500 total kills (team)", Stat.WATER_WARS_TEAM_KILLS, 500),
    WATER_WARS_TEAM_KILLS_IV("Team Slayer IV", AchievementDifficulty.HARD, "Get 5,000 total kills (team)", Stat.WATER_WARS_TEAM_KILLS, 5000),
    WATER_WARS_TEAM_KILLS_V("Team Slayer V", AchievementDifficulty.INSANE, "Get 10,000 total kills (team)", Stat.WATER_WARS_TEAM_KILLS, 10000),
    WATER_WARS_TEAM_KILLS_VI("Team Slayer VI", AchievementDifficulty.EPIC, "Get 100,000 total kills (team)", Stat.WATER_WARS_TEAM_KILLS, 100000),

    WATER_WARS_TEAM_GAMES_PLAYED_I("Team Player I", AchievementDifficulty.NOVICE, "Play 3 games (team)", Stat.WATER_WARS_TEAM_GAMES_PLAYED, 3),
    WATER_WARS_TEAM_GAMES_PLAYED_II("Team Player II", AchievementDifficulty.EASY, "Play 30 games (team)", Stat.WATER_WARS_TEAM_GAMES_PLAYED, 30),
    WATER_WARS_TEAM_GAMES_PLAYED_III("Team Player III", AchievementDifficulty.MEDIUM, "Play 300 games (team)", Stat.WATER_WARS_TEAM_GAMES_PLAYED, 300),
    WATER_WARS_TEAM_GAMES_PLAYED_IV("Team Player IV", AchievementDifficulty.HARD, "Play 1,500 games (team)", Stat.WATER_WARS_TEAM_GAMES_PLAYED, 1500),
    WATER_WARS_TEAM_GAMES_PLAYED_V("Team Player V", AchievementDifficulty.INSANE, "Play 3,000 games (team)", Stat.WATER_WARS_TEAM_GAMES_PLAYED, 3000),
    WATER_WARS_TEAM_GAMES_PLAYED_VI("Team Player VI", AchievementDifficulty.EPIC, "Play 15,000 games (team)", Stat.WATER_WARS_TEAM_GAMES_PLAYED, 15000),

    WATER_WARS_SOLO_WINS_I("Water Winner I", AchievementDifficulty.NOVICE, "Win 1 game (solo)", Stat.WATER_WARS_SOLO_GAMES_WON, 1),
    WATER_WARS_SOLO_WINS_II("Water Winner II", AchievementDifficulty.EASY, "Win 10 games (solo)", Stat.WATER_WARS_SOLO_GAMES_WON, 10),
    WATER_WARS_SOLO_WINS_III("Water Winner III", AchievementDifficulty.MEDIUM, "Win 100 games (solo)", Stat.WATER_WARS_SOLO_GAMES_WON, 100),
    WATER_WARS_SOLO_WINS_IV("Water Winner IV", AchievementDifficulty.HARD, "Win 500 games (solo)", Stat.WATER_WARS_SOLO_GAMES_WON, 500),
    WATER_WARS_SOLO_WINS_V("Water Winner V", AchievementDifficulty.INSANE, "Win 1,000 games (solo)", Stat.WATER_WARS_SOLO_GAMES_WON, 1000),
    WATER_WARS_SOLO_WINS_VI("Water Winner VI", AchievementDifficulty.EPIC, "Win 5,000 games (solo)", Stat.WATER_WARS_SOLO_GAMES_WON, 5000),

    WATER_WARS_SOLO_KILLS_I("Slayer I", AchievementDifficulty.NOVICE, "Get 20 total kills (solo)", Stat.WATER_WARS_SOLO_KILLS, 20),
    WATER_WARS_SOLO_KILLS_II("Slayer II", AchievementDifficulty.EASY, "Get 100 total kills (solo)", Stat.WATER_WARS_SOLO_KILLS, 100),
    WATER_WARS_SOLO_KILLS_III("Slayer III", AchievementDifficulty.MEDIUM, "Get 500 total kills (solo)", Stat.WATER_WARS_SOLO_KILLS, 500),
    WATER_WARS_SOLO_KILLS_IV("Slayer IV", AchievementDifficulty.HARD, "Get 5,000 total kills (solo)", Stat.WATER_WARS_SOLO_KILLS, 5000),
    WATER_WARS_SOLO_KILLS_V("Slayer V", AchievementDifficulty.INSANE, "Get 10,000 total kills (solo)", Stat.WATER_WARS_SOLO_KILLS, 10000),
    WATER_WARS_SOLO_KILLS_VI("Slayer VI", AchievementDifficulty.EPIC, "Get 100,000 total kills (solo)", Stat.WATER_WARS_SOLO_KILLS, 100000),

    WATER_WARS_SOLO_GAMES_PLAYED_I("Loner I", AchievementDifficulty.NOVICE, "Play 3 games (solo)", Stat.WATER_WARS_SOLO_GAMES_PLAYED, 3),
    WATER_WARS_SOLO_GAMES_PLAYED_II("Loner II", AchievementDifficulty.EASY, "Play 30 games (solo)", Stat.WATER_WARS_SOLO_GAMES_PLAYED, 30),
    WATER_WARS_SOLO_GAMES_PLAYED_III("Loner III", AchievementDifficulty.MEDIUM, "Play 300 games (solo)", Stat.WATER_WARS_SOLO_GAMES_PLAYED, 300),
    WATER_WARS_SOLO_GAMES_PLAYED_IV("Loner IV", AchievementDifficulty.HARD, "Play 1,500 games (solo)", Stat.WATER_WARS_SOLO_GAMES_PLAYED, 1500),
    WATER_WARS_SOLO_GAMES_PLAYED_V("Loner V", AchievementDifficulty.INSANE, "Play 3,000 games (solo)", Stat.WATER_WARS_SOLO_GAMES_PLAYED, 3000),
    WATER_WARS_SOLO_GAMES_PLAYED_VI("Loner VI", AchievementDifficulty.EPIC, "Play 15,000 games (solo)", Stat.WATER_WARS_SOLO_GAMES_PLAYED, 15000),

    WATER_WARS_LOOT_CHEST_I("Looter I", AchievementDifficulty.NOVICE, "Loot 20 chests", Stat.WATER_WARS_CHESTS_OPENED, 20),
    WATER_WARS_LOOT_CHEST_II("Looter II", AchievementDifficulty.EASY, "Loot 100 chests", Stat.WATER_WARS_CHESTS_OPENED, 100),
    WATER_WARS_LOOT_CHEST_III("Looter III", AchievementDifficulty.MEDIUM, "Loot 500 chests", Stat.WATER_WARS_CHESTS_OPENED, 500),
    WATER_WARS_LOOT_CHEST_IV("Looter IV", AchievementDifficulty.HARD, "Loot 1,000 chests", Stat.WATER_WARS_CHESTS_OPENED, 1000),
    WATER_WARS_LOOT_CHEST_V("Looter V", AchievementDifficulty.INSANE, "Loot 5,000 chests", Stat.WATER_WARS_CHESTS_OPENED, 5000),
    WATER_WARS_LOOT_CHEST_VI("Looter VI", AchievementDifficulty.EPIC, "Loot 25,000 chests", Stat.WATER_WARS_CHESTS_OPENED, 25000),

    WATER_WARS_ENCHANTED_I("Enchanted I", AchievementDifficulty.NOVICE, "Enchant 5 items", Stat.WATER_WARS_ITEMS_ENCHANTED, 5),
    WATER_WARS_ENCHANTED_II("Enchanted II", AchievementDifficulty.EASY, "Enchant 50 items", Stat.WATER_WARS_ITEMS_ENCHANTED, 50),
    WATER_WARS_ENCHANTED_III("Enchanted III", AchievementDifficulty.MEDIUM, "Enchant 500 items", Stat.WATER_WARS_ITEMS_ENCHANTED, 500),
    WATER_WARS_ENCHANTED_IV("Enchanted IV", AchievementDifficulty.HARD, "Enchant 2,500 items", Stat.WATER_WARS_ITEMS_ENCHANTED, 2500),
    WATER_WARS_ENCHANTED_V("Enchanted V", AchievementDifficulty.INSANE, "Enchant 5,000 items", Stat.WATER_WARS_ITEMS_ENCHANTED, 5000),
    WATER_WARS_ENCHANTED_VI("Enchanted VI", AchievementDifficulty.EPIC, "Enchant 25,000 items", Stat.WATER_WARS_ITEMS_ENCHANTED, 25000);

    private String display, description;
    private int amount = 1;

    private ServerGameType serverGameType;
    private AchievementDifficulty achievementDifficulty;
    private Stat stat = null;

    /**
     * Used to create achievements based on stats.
     * @param display
     * @param achievementDifficulty
     * @param description
     */
    Achievement(String display, AchievementDifficulty achievementDifficulty, String description, Stat stat, int amount) {
        this.serverGameType = stat.getServerGameType();
        this.display = display;
        this.achievementDifficulty = achievementDifficulty;
        this.description = description;
        this.stat = stat;
        this.amount = amount;
    }

    /**
     * Used to create achievements for a specific game type.
     *
     * @param serverGameType
     * @param display
     * @param achievementDifficulty
     * @param description
     */
    Achievement(ServerGameType serverGameType, String display, AchievementDifficulty achievementDifficulty, String description) {
        this.serverGameType = serverGameType;
        this.display = display;
        this.achievementDifficulty = achievementDifficulty;
        this.description = description;
    }

    /**
     * Used to create achievements that can be unlocked on any server.
     *
     * @param display
     * @param achievementDifficulty
     * @param description
     */
    Achievement(String display, AchievementDifficulty achievementDifficulty, String description) {
        this(null, display, achievementDifficulty, description);
    }

    public ServerGameType getServerGameType() {
        return serverGameType;
    }

    public String getDisplay() {
        return display;
    }

    public AchievementDifficulty getAchievementDifficulty() {
        return achievementDifficulty;
    }

    public String getDescription() {
        return description;
    }

    public Stat getStat() {
        return stat;
    }

    public int getStatAmount() {
        return amount;
    }
}
