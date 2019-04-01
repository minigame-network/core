package me.chasertw123.minigames.core.user.data.settings;

import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Setting {

    PLAYER_VISIBILITY(true, new ItemStack(Material.WATCH), "Player Visibility", "Toggles ability to see other players and the effects around them."),
    CHAT_VISIBILITY(true, new ItemStack(Material.PAPER), "Chat Visibility", "Toggles ability to see general chat. You will still see staff messages."),
    PRIVATE_MESSAGES(true, new ItemStack(Material.DARK_OAK_DOOR_ITEM), "Private Messages", "Toggles the ability to receive private messages."),
    FRIEND_REQUESTS(true, new ItemStack(Material.SKULL_ITEM, 1, (short) 3), "Friend Requests", "Toggles ability to receive friend requests."),
    PARTY_REQUESTS(true, new ItemStack(Material.BOOK), "Party Requests", "Toggle ability to receive party requests."),
    AUTO_MESSAGES(true, new ItemStack(Material.JUKEBOX), "Auto-Messages", "Toggles the server sending you auto-messages in chat while you're in the hub."),
    SCOREBOARD(true, new ItemStack(Material.LEASH), "Hub Scoreboard", "Toggles ability to see the hub scoreboard on the right of your screen."),
    BOSS_BAR(true, new ItemStack(Material.DRAGON_EGG), "Hub Boss Bar", "Toggle ability to see hub boss bar at the top of your screen."),
    AUTO_REJOIN(false, new ItemStack(Material.EMPTY_MAP), "Auto Rejoin", "Toggles if rejoin a new game after one ends."),
    AUTO_FLY(false, new ItemStack(Material.FEATHER), "Auto Fly", "Toggles when you join the hub if you are already able to fly.");

    private boolean defaultValue;
    private ItemStack itemStack;
    private String display, description;

    Setting(boolean defaultValue, ItemStack itemStack, String display, String description) {
        this.defaultValue = defaultValue;
        this.itemStack = itemStack;
        this.display = display;
        this.description = description;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public cItemStack toItemStack(User user) {
        if (user.getSetting(this))
            return new cItemStack(itemStack).setDisplayName(ChatColor.GREEN + display).addFancyLore(description, ChatColor.GRAY.toString());

        return new cItemStack(itemStack).setDisplayName(ChatColor.RED + display).addFancyLore(description, ChatColor.GRAY.toString());
    }

}
