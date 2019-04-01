package me.chasertw123.minigames.core.collectibles.morphs.monsters;

import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.morphs.MorphCollectible;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;

public class Morph_Skeleton extends MorphCollectible {

    public Morph_Skeleton(User player) {
        super(player, EntityType.SKELETON, "Skeleton", "", new cItemStack(Material.SKULL_ITEM), CollectibleRarity.COMMON);
    }

    @Override
    public void spawnOptions(LivingEntity entity) {
        ((Skeleton) entity).getEquipment().setItemInHand(new cItemStack(Material.CROPS));
    }

}
