package me.chasertw123.minigames.core.utils.items;

import me.chasertw123.minigames.core.user.User;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 9/2/2017.
 */
public class AbstractItem {

    private User pp;
    private int slot;
    private ItemStack itemStack;
    private AbstractAction action;

    public AbstractItem(ItemStack itemStack, User pp, int slot, AbstractAction action) {
        this.itemStack = itemStack;
        this.pp = pp;
        this.slot = slot;
        this.action = action;

        pp.getAbstractItems().put(slot, this);
        pp.getPlayer().getInventory().setItem(slot, itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public AbstractAction getAction() {
        return action;
    }

    public interface AbstractAction {
        void interact(InteractType interactType);
    }

    public enum InteractType { LEFT, RIGHT, OTHER }
}
