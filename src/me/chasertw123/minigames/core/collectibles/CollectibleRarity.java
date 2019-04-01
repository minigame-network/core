package me.chasertw123.minigames.core.collectibles;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Chase on 8/8/2017.
 */
public enum CollectibleRarity {

    COMMON(ChatColor.WHITE, 2, 5),
    UNCOMMON(ChatColor. GREEN, 5, 15),
    RARE(ChatColor.BLUE, 10, 30),
    EPIC(ChatColor.DARK_PURPLE, 15, 50),
    LEGENDARY(ChatColor.GOLD, 25, 100);

    private ChatColor color;
    private int duplicateReward, purchaseValue;

    CollectibleRarity(ChatColor color, int duplicateReward, int purchaseValue) {
        this.color = color;
        this.duplicateReward = duplicateReward;
        this.purchaseValue = purchaseValue;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getDuplicateReward() {
        return duplicateReward;
    }

    public int getPurchaseValue() {
        return purchaseValue;
    }
}
