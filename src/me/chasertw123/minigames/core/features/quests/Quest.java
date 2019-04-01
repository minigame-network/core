package me.chasertw123.minigames.core.features.quests;

import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Quest {

    // Daily Quests
    WATER_WARRIOR(QuestType.DAILY, "Water Warrior", "Get 8 kills in one Water Wars game.", 1),
    EVOLUTION_NINJA(QuestType.DAILY, "Evolution Ninja", "Evolve 15 times today.", 15),

    // Weekly Quests


    // Monthly Quests



    ;

    private QuestType questType;
    private String display, description;
    private int stages;

    Quest(QuestType questType, String display, String description, int stages) {
        this.questType = questType;
        this.display = display;
        this.description = description;
        this.stages = stages;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public String getDisplay() {
        return display;
    }

    public String getDescription() {
        return description;
    }

    public int getStages() {
        return stages;
    }

    public ItemStack getItemStack(User paradisePlayer) {

        cItemStack item = new cItemStack(Material.EMPTY_MAP, ChatColor.GREEN + StringUtil.niceString(questType.toString()) + ": " + display);

        item.addFancyLore(description, ChatColor.GRAY.toString());
        item.addLore("", ChatColor.GRAY + "Rewards:");

        if (questType.getExperienceReward() < 0)
            item.addLore(ChatColor.GRAY + "+" + ChatColor.GREEN + questType.getExperienceReward() + ChatColor.GRAY + " Experience");

        if (questType.getCoinReward() < 0)
            item.addLore(ChatColor.GRAY + "+" + ChatColor.YELLOW + questType.getCoinReward() + ChatColor.GRAY + " Coins");

        if (questType.getCrystalReward() < 0)
            item.addLore(ChatColor.GRAY + "+" + ChatColor.LIGHT_PURPLE + questType.getCrystalReward() + ChatColor.GRAY + " Crystals");

        if (questType.getChestReward() < 0)
            item.addLore(ChatColor.GRAY + "+" + ChatColor.AQUA + questType.getChestReward() + ChatColor.GRAY + " Chests");

        item.addLore("");
        item.addFancyLore(questType.getDescription(), ChatColor.DARK_GRAY.toString());
        item.addLore("");

        // TODO: Check state of quest
        // TODO: Display "Click to start this quest!" if not started
        // TODO: If started display progress in both x/y and a progress bar [||||||||||||||||]

        return item;
    }
}
