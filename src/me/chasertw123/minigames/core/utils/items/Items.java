package me.chasertw123.minigames.core.utils.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Created by Chase on 9/9/2017.
 */
public enum Items {

    RETURN_TO_HUB(new cItemStack(Material.BED, ChatColor.RED + "Return to Hub " + ChatColor.GRAY + "(Right Click)")),

    PLAYER_TELEPORTER(new cItemStack(Material.COMPASS, ChatColor.AQUA + "Player Teleporter " + ChatColor.GRAY + "(Right Click)")),

    SPECTATING_SETTINGS(new cItemStack(Material.REDSTONE_COMPARATOR, ChatColor.BLUE + "Spectating Settings " + ChatColor.GRAY + " (Right Click)")),

    PLAY_AGAIN(new cItemStack(Material.PAPER, ChatColor.GREEN + "Play Again " + ChatColor.GRAY + "(Right Click)")),

    MAP_VOTING(new cItemStack(Material.EMPTY_MAP, ChatColor.YELLOW + "Map Voting " + ChatColor.GRAY + "(Right Click)")),

    HIDE_PLAYERS(new cItemStack(Material.PRISMARINE_SHARD, ChatColor.GREEN + "Player Visibility " + ChatColor.GRAY + "(Right-Click)")
            .addFancyLore("Click to disable seeing other players and their effects. You will still be able to see your effects. They should improve your performance.", ChatColor.GRAY.toString())),

    SHOW_PLAYERS(new cItemStack(Material.FLINT, ChatColor.RED + "Player Visibility " + ChatColor.GRAY + "(Right-Click)")
            .addFancyLore("Click to enable seeing other players and their effects. They may impact your performance.", ChatColor.GRAY.toString()));

    private cItemStack itemStack;

    Items(cItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public cItemStack getItemStack() {
        return itemStack;
    }
}
