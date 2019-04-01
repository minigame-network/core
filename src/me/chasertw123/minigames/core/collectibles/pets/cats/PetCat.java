package me.chasertw123.minigames.core.collectibles.pets.cats;

import me.chasertw123.minigames.core.api.CustomHead;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.pets.PetCollectible;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.utils.StringUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 1/13/2018.
 */
public class PetCat extends PetCollectible {

    private Ocelot.Type catType;
    private boolean baby;

    public PetCat(User paradisePlayer, Ocelot.Type catType, boolean baby, String name, int exp, int happiness, int hunger, int thirst) {
        super(paradisePlayer, EntityType.OCELOT, catTypeToDisplay(catType, baby), catTypeToDescription(catType), catTypeToPlayerHead(catType),
                baby ? CollectibleRarity.RARE : CollectibleRarity.UNCOMMON, name, exp, happiness, hunger, thirst);

        this.catType = catType;
        this.baby = baby;
    }

    public PetCat(User paradisePlayer, Ocelot.Type catType, boolean baby) {
        super(paradisePlayer, EntityType.OCELOT, catTypeToDisplay(catType, baby), catTypeToDescription(catType), catTypeToPlayerHead(catType), baby ? CollectibleRarity.RARE : CollectibleRarity.UNCOMMON);
        this.catType = catType;
        this.baby = baby;
    }

    @Override
    public void spawnOptions(LivingEntity entity) {

        ((Ocelot) entity).setCatType(catType);

        if (baby)
            ((Ocelot) entity).setBaby();
    }

    private static String catTypeToDisplay(Ocelot.Type catType, boolean baby) {
        return StringUtil.niceString(baby ? "Baby " : "" + catType.toString().replaceAll("_", " "));
    }

    private static String catTypeToDescription(Ocelot.Type catType) {
        switch (catType) {
            case BLACK_CAT:
                return "They say black cats are bad luck; but look its soooo cute!";

            case RED_CAT:
                return "Insert something fun about a cat being the color red.";

            case SIAMESE_CAT:
                return "Insert something about a siamese cat and what they are.";

            case WILD_OCELOT:
                return "A wild jungle kitty that  it super cool and stuff.";
        }

        return "";
    }

    private static ItemStack catTypeToPlayerHead(Ocelot.Type catType) {
        switch (catType) {
            case BLACK_CAT:
                return CustomHead.BLACK_CAT.craftSkull();

            case RED_CAT:
                return CustomHead.RED_CAT.craftSkull();

            case SIAMESE_CAT:
                return CustomHead.SIAMESE_CAT.craftSkull();

            case WILD_OCELOT:
                return null;
        }

        return null;
    }
}
