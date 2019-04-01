package me.chasertw123.minigames.core.features.chests;

import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.gui.AbstractGui;

public class GUI_Chest extends AbstractGui {

    public GUI_Chest(User paradisePlayer) {
        super(3, "Select Chest");

        for(ChestType chestType : ChestType.values())
            setItem(chestType.getItemStack(paradisePlayer), chestType.getSlot(), (s, c, p) -> {

            });
    }

}
