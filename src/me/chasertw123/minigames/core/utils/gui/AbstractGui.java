package me.chasertw123.minigames.core.utils.gui;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chase on 2/4/2017.
 */
public class AbstractGui {

    private Inventory inventory;

    private Map<Integer, AbstractAction> actions = new HashMap<>();
    private Map<Integer, ItemStack> items = new HashMap<>();

    public AbstractGui(int rows, String title) {
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
    }

    public AbstractGui(int rows, String title, User pp) {
        this(rows, title);
        openInventory(pp);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public AbstractAction getAction(int slot) {
        return actions.get(slot);
    }

    public void setItem(ItemStack item, int slot, AbstractAction action) {
        inventory.setItem(slot, item);
        actions.put(slot, action);
        items.put(slot, item);
    }

    public ItemStack getItemStack(int slot) {
        return items.get(slot);
    }

    public void openInventory(User pp) {
        pp.getPlayer().openInventory(getInventory());
        pp.setCurrentGui(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                pp.getPlayer().updateInventory();
            }
        }.runTaskLater(Main.getInstance(), 2);
    }

    public void onClose() {}

    public interface AbstractAction {
        void click(int slot, ClickType clickType, Player player);
    }
}
