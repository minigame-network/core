package me.chasertw123.minigames.core.user.data.reports;

import me.chasertw123.minigames.core.api.CustomHead;
import me.chasertw123.minigames.core.user.OfflineUser;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.infraction.Infraction;
import me.chasertw123.minigames.shared.infraction.InfractionReason;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Gui_Infraction extends AbstractGui {

    private static int[] FILLER_SLOTS = {0, 1, 2, 3, 5, 6, 7, 8, 9, 45, 46, 47, 48, 50, 51, 52, 53};

    public Gui_Infraction(User staff, OfflineUser other) {
        super(6, "Infraction: " + other.getUsername(), staff);

        setItem(this.craftPlayerHead(other), 4, (s, c, p) -> {});
        setItem(new cItemStack(Material.BARRIER, ChatColor.RED + "" + ChatColor.BOLD + "CANCEL"), 49, (s, c, p) -> p.closeInventory());

        for (int slot : FILLER_SLOTS)
            setItem(new cItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15, " "), slot, (s, c, p) -> {});

        int slot = 9;
        for (InfractionReason type : InfractionReason.values()) {
            setItem(craftItemStack(type), slot++, (s, c, p) -> {
                p.closeInventory();
                staff.sendPrefixedMessage(ChatColor.RED + "Giving " + other.getUsername() + " an infraction for " + ChatColor.YELLOW + type.getReason() + ChatColor.RED + ".");
                other.addInfraction(new Infraction(other.getUUID(), staff.getUUID(), type));
            });
        }

        setItem(CustomHead.QUESTION_MARK.craftSkull().setDisplayName(ChatColor.YELLOW + "Custom").addLore("Create a custom infraction with a custom reason.", ChatColor.GRAY.toString()), slot, (s, c, p) -> {
            p.closeInventory();
            staff.sendPrefixedMessage(ChatColor.RED + "Please fill out the punishment in this format: " + ChatColor.YELLOW + "/punish " + other.getUsername() + " custom <infraction points> <reason>");
            p.chat("/infraction " + other.getUsername() + " custom ");
        });
    }

    private ItemStack craftPlayerHead(OfflineUser opp) {
        return new cItemStack(opp.getUsername(), ChatColor.YELLOW + opp.getUsername()).addLore(ChatColor.WHITE + "Infrations: " + ChatColor.GREEN + opp.getInfractions().size(),
                ChatColor.WHITE + "Punishments: " + ChatColor.GREEN + opp.getPunishments().size(), ChatColor.WHITE + "Infraction Points: " + ChatColor.GREEN + opp.getInfractionPoints());
    }

    private ItemStack craftItemStack(InfractionReason reason) {
        cItemStack i = new cItemStack(Material.BOOK, ChatColor.YELLOW + reason.getReason());
        i.addFancyLore(reason.getDescription(), org.bukkit.ChatColor.GRAY.toString());
        i.addLore(" ", org.bukkit.ChatColor.RED + "Infraction Points: " + org.bukkit.ChatColor.WHITE + reason.getPunishmentValue());

        return i;
    }

}
