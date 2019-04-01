package me.chasertw123.minigames.core.collectibles.morphs.monsters;

import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.morphs.MorphCollectible;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class Morph_Creeper extends MorphCollectible {

    public Morph_Creeper(User player) {
        super(player, EntityType.CREEPER, "", "", new cItemStack(Material.SKULL_ITEM), CollectibleRarity.RARE);
    }

    @Override
    public void spawnOptions(LivingEntity entity) {

    }

}
