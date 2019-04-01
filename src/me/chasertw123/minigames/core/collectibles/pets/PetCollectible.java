package me.chasertw123.minigames.core.collectibles.pets;

import me.chasertw123.minigames.core.collectibles.Collectible;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.CollectibleType;
import me.chasertw123.minigames.core.collectibles.CollectibleVisibility;
import me.chasertw123.minigames.core.user.User;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * Created by Chase on 1/13/2018.
 */
public abstract class PetCollectible extends Collectible implements CollectibleVisibility {

    private static final int MAX_HAPPINESS = 100;
    private static final int IDEAL_HUNGER = 100;
    private static final int IDEAL_THIRST = 100;

    private UUID owner;
    private LivingEntity entity = null;
    private EntityType entityType;

    private String name;
    private int exp, happiness, hunger, thirst;

    public PetCollectible(User owner, EntityType entityType, String display, String description, ItemStack itemStack, CollectibleRarity rarity, String name, int exp, int happiness, int hunger, int thirst) {
        super(display, description, itemStack, CollectibleType.PET, rarity);
        this.entityType = entityType;
        this.owner = owner.getUUID();
        this.name = name != null ? name : display;
        this.exp = exp;
        this.happiness = happiness;
        this.hunger = hunger;
        this.thirst = thirst;
    }

    public PetCollectible(User owner, EntityType entityType, String display, String description, ItemStack itemStack, CollectibleRarity rarity) {
        this(owner, entityType, display, description, itemStack, rarity, null, 0, 50, 50, 50);
    }

    public UUID getOwner() {
        return owner;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(owner);
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isEntitySpawned() {
        return entity != null;
    }

    public void spawn() {

        if (this.isEntitySpawned())
            return;

        this.entity = (LivingEntity) getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), entityType);
        this.entity.setCustomName(this.getPlayer().getName());
        this.entity.setCustomNameVisible(true);
        this.spawnOptions(this.entity);

        EntityCreature c = (EntityCreature) ((CraftEntity) entity).getHandle();

        List goalB = (List) getPrivateField("b", PathfinderGoalSelector.class, c.goalSelector);
        List goalC = (List) getPrivateField("c", PathfinderGoalSelector.class, c.goalSelector);
        List targetB = (List) getPrivateField("b", PathfinderGoalSelector.class, c.targetSelector);
        List targetC = (List) getPrivateField("c", PathfinderGoalSelector.class, c.targetSelector);

        goalB.clear();
        goalC.clear();
        targetB.clear();
        targetC.clear();

        c.goalSelector.a(0, new PathfinderGoalLookAtPlayer(c, EntityHuman.class, 8.0F));
    }

    public void updatePet() {

        Object craftEntity = ((CraftEntity) entity).getHandle();
        Block behind = getBlockBehindPlayer(getPlayer());

        ((EntityInsentient) craftEntity).getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(48);

        if (getPlayer().getLocation().distanceSquared(entity.getLocation()) >= 7)
            ((EntityInsentient) craftEntity).getNavigation().a(behind.getLocation().getBlockX(), behind.getLocation().getBlockY(), behind.getLocation().getBlockZ(), 1.0);

        if (getPlayer().getLocation().distanceSquared(entity.getLocation()) >= 48)
            entity.teleport(behind.getLocation());
    }

    public void killEntity() {
        if (entity != null && !entity.isDead())
            entity.remove();

        this.entity = null;
    }

    public abstract void spawnOptions(LivingEntity entity);

    public void onInteract(User paradisePlayer) {

        if (paradisePlayer.getPlayer().isSneaking()) {

            if (!paradisePlayer.getPlayer().getUniqueId().equals(owner)) {
                paradisePlayer.sendMessage(ChatColor.RED + "It's not very polite to just try and ride someone else's pet.");
                return;
            }

            // TODO: Attempting to ride le pet

        }
    }

    @Override
    public void showCollectible(User paradisePlayer) {
        // TODO
    }

    @Override
    public void hideCollectible(User paradisePlayer) {
        // TODO
    }

    // TODO: Save
    public static String toString(PetCollectible petCollectible) {
        return "";
    }

    // TODO: Load
    public static PetCollectible fromString(String s) {
        return null;
    }

    private Object getPrivateField(String fieldName, Class clazz, Object object) {

        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

    private Block getBlockBehindPlayer(Player player) {

        Location loc = player.getLocation();
        org.bukkit.World world = player.getWorld();
        Block behind = loc.getBlock();

        int direction = (int)loc.getYaw();

        if (direction < 0) {
            direction += 360;
            direction = (direction + 45) / 90;
        }

        else
            direction = (direction + 45) / 90;

        switch (direction) {
            case 1:
                behind = world.getBlockAt(behind.getX() + 1, behind.getY(), behind.getZ());
                break;

            case 2:
                behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() + 1);
                break;

            case 3:
                behind = world.getBlockAt(behind.getX() - 1, behind.getY(), behind.getZ());
                break;

            case 4:
                behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
                break;

            case 0:
                behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
                break;

            default:
                break;
        }

        return behind;
    }
}
