package me.chasertw123.minigames.core.collectibles.pets.cats.types;

import me.chasertw123.minigames.core.collectibles.pets.cats.PetCat;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.entity.Ocelot;

/**
 * Created by Chase on 1/14/2018.
 */
public class PetCat_Red extends PetCat {

    public PetCat_Red(User paradisePlayer, String name, int exp, int happiness, int hunger, int thirst) {
        super(paradisePlayer, Ocelot.Type.RED_CAT, false, name, exp, happiness, hunger, thirst);
    }

    public PetCat_Red(User paradisePlayer) {
        super(paradisePlayer, Ocelot.Type.RED_CAT, false);
    }

}
