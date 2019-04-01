package me.chasertw123.minigames.core.user.data.achievements;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Chase on 7/19/2017.
 */
public enum AchievementDifficulty {

    NOVICE(0, 10, ChatColor.GREEN, "Novice is the easiest difficulty. Most of these types of achievements can be earned in a few minutes or some even a few seconds. No reward is offered with these achievements due to their low difficulty."),
    EASY(1, 11, ChatColor.YELLOW, "Easy is the second easiest difficulty, even though it's called easy. Easy achievements like the name suggests are petty easy to get. They take at most an hour to get, therefore they offer no rewards fot completing."),
    MEDIUM(2, 12, ChatColor.BLUE, "Medium is the middle ground of difficulty, it's not to hard but it's not easy for sure. Unlocking one of these will come quickly as you play the game and currently no reward is offered for completing these."),
    HARD(3, 13, ChatColor.LIGHT_PURPLE, "Hard is where it starts to get tough. You have to have some serious dedication to get to this point. Sadly we don't have anything to offer at this point reward wise. Once more collectibles are available hard achievements will receive rewards for completing."),
    INSANE(4, 1, ChatColor.RED, "Insane like the name requires you to actually be insane to complete. On completion of an insane achievement a message is broadcast to the entire server. Sadly like hard achievements we don't have anything to offer at this point reward wise. Once more collectables are available insane achievements will receive rewards for completing as well."),
    EPIC(5, 14, ChatColor.GOLD, "Epic achievements are a true and amazing task to complete. When completed a message will be sent to every player on every server telling them of what you managed to accomplish. Also if you start a new post in the forums telling everyone of your achievement, you will be eligible for a $20 coupon code for the MCParadise store.");

    private String description;
    private int difficulty, itemstackData;
    private ChatColor chatColor;

    AchievementDifficulty(int difficulty, int itemstackData, ChatColor chatColor, String description) {
        this.difficulty = difficulty;
        this.itemstackData = itemstackData;
        this.chatColor = chatColor;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public short getItemStackData() {
        return (short) itemstackData;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
