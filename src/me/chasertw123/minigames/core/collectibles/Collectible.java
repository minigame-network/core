package me.chasertw123.minigames.core.collectibles;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 8/8/2017.
 */
public abstract class Collectible {

    private String display, description;
    private ItemStack itemStack;
    private CollectibleType type;
    private CollectibleRarity rarity;
    private boolean needsDeluxe;

    public Collectible(String display, String description, ItemStack itemStack, CollectibleType type, CollectibleRarity rarity) {
        this.display = display;
        this.description = description;
        this.itemStack = itemStack;
        this.type = type;
        this.rarity = rarity;
    }

    public Collectible(String display, String description, ItemStack itemStack, CollectibleType type, CollectibleRarity rarity, boolean needsDeluxe) {
        this(display, description, itemStack, type, rarity);
        this.needsDeluxe = needsDeluxe;
    }

    public String getDisplay() {
        return display;
    }

    public String getDescription() {
        return description;
    }

    public CollectibleRarity getRarity() {
        return rarity;
    }

    public CollectibleType getType() {
        return type;
    }

    public cItemStack getItemStack() {
        return new cItemStack(itemStack);
    }

    public boolean needsDeluxe() {
        return needsDeluxe;
    }
}
