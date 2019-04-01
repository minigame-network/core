package me.chasertw123.minigames.core.features.chests;

import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ChestType {

    STANDARD(new ItemStack(Material.CHEST), 10),
    HOLIDAY(new ItemStack(Material.RED_MUSHROOM), 12),
    SPRING(new ItemStack(Material.BOOKSHELF), 14),
    SUMMER(new ItemStack(Material.YELLOW_FLOWER), 16);

    private ItemStack itemStack;
    private int slot;

    ChestType(ItemStack itemStack, int slot) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack(User paradisePlayer) {
        return new cItemStack(itemStack).setDisplayName(StringUtil.niceString(this.toString()))
                .setLore(paradisePlayer.getChests(this) > 0 ? ChatColor.GREEN + "You can afford this!"
                        : ChatColor.RED + "You can't afford this!");
    }

}
