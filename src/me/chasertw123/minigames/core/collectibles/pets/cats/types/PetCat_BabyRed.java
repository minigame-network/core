package me.chasertw123.minigames.core.collectibles.pets.cats.types;

import me.chasertw123.minigames.core.collectibles.pets.cats.PetCat;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.entity.Ocelot;

/**
 * Created by Chase on 1/14/2018.
 */
public class PetCat_BabyRed extends PetCat {

    public PetCat_BabyRed(User paradisePlayer, String name, int exp, int happiness, int hunger, int thirst) {
        super(paradisePlayer, Ocelot.Type.RED_CAT, true, name, exp, happiness, hunger, thirst);
    }

    public PetCat_BabyRed(User paradisePlayer) {
        super(paradisePlayer, Ocelot.Type.RED_CAT, true);
    }

}
