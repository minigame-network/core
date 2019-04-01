package me.chasertw123.minigames.core.user.data.stats;

public enum StatClump {

    WATER_WARS_KILLS("Kills", Stat.WATER_WARS_SOLO_KILLS, Stat.WATER_WARS_TEAM_KILLS),
    WATER_WARS_DEATHS("Deaths", Stat.WATER_WARS_SOLO_DEATHS, Stat.WATER_WARS_TEAM_DEATHS),
    WATER_WARS_GAMES_WON("Wins", Stat.WATER_WARS_SOLO_GAMES_WON, Stat.WATER_WARS_TEAM_GAMES_WON),
    WATER_WARS_GAMES_PLAYED("Games Played", Stat.WATER_WARS_SOLO_GAMES_PLAYED, Stat.WATER_WARS_TEAM_GAMES_PLAYED);

    private final String identifier;
    private final Stat[] stats;

    StatClump(String identifier, Stat... stats) {
        this.identifier = identifier;
        this.stats = stats;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Stat[] getStats() {
        return stats;
    }
}
