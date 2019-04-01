package me.chasertw123.minigames.core.features.quests;

public enum QuestType {

    DAILY(500, 500, 0, 0, "Daily Quests can be done once per day each. Resets every morning."),
    WEEKLY(2000, 2000, 10, 0, "Weekly Quests can be done per week each. Resets every Friday morning."),
    MONTHLY(5000, 5000, 25, 2, "Monthly Quests can be done per month each. Resets at that start of every month in the morning.");

    private int coinReward, experienceReward, crystalReward, chestReward;
    private String description;

    QuestType(int coinReward, int experienceReward, int crystalReward, int chestReward, String description) {
        this.coinReward = coinReward;
        this.experienceReward = experienceReward;
        this.crystalReward = crystalReward;
        this.chestReward = chestReward;
        this.description = description;
    }

    public int getCoinReward() {
        return coinReward;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public int getCrystalReward() {
        return crystalReward;
    }

    public int getChestReward() {
        return chestReward;
    }

    public String getDescription() {
        return description;
    }

}
