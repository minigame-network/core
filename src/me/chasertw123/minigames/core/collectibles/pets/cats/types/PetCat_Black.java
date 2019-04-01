package me.chasertw123.minigames.core.collectibles.pets.cats.types;

import me.chasertw123.minigames.core.collectibles.pets.cats.PetCat;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.entity.Ocelot;

/**
 * Created by Chase on 1/14/2018.
 */
public class PetCat_Black extends PetCat {

    public PetCat_Black(User paradisePlayer, String name, int exp, int happiness, int hunger, int thirst) {
        super(paradisePlayer, Ocelot.Type.BLACK_CAT, false, name, exp, happiness, hunger, thirst);
    }

    public PetCat_Black(User paradisePlayer) {
        super(paradisePlayer, Ocelot.Type.BLACK_CAT, false);
    }

}
