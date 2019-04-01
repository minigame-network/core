package me.chasertw123.minigames.core.collectibles.pets.cats.types;

import me.chasertw123.minigames.core.collectibles.pets.cats.PetCat;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.entity.Ocelot;

/**
 * Created by Chase on 1/14/2018.
 */
public class PetCat_Ocelot extends PetCat {

    public PetCat_Ocelot(User paradisePlayer, String name, int exp, int happiness, int hunger, int thirst) {
        super(paradisePlayer, Ocelot.Type.WILD_OCELOT, false, name, exp, happiness, hunger, thirst);
    }

    public PetCat_Ocelot(User paradisePlayer) {
        super(paradisePlayer, Ocelot.Type.WILD_OCELOT, false);
    }

}
