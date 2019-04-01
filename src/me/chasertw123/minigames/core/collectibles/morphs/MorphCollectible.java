package me.chasertw123.minigames.core.collectibles.morphs;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.chasertw123.minigames.core.collectibles.Collectible;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.CollectibleType;
import me.chasertw123.minigames.core.collectibles.CollectibleVisibility;
import me.chasertw123.minigames.core.user.User;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public abstract class MorphCollectible extends Collectible implements CollectibleVisibility {

    private UUID owner;
    private LivingEntity entity = null;
    private EntityType entityType;

    public MorphCollectible(User player, EntityType entityType, String display, String description, ItemStack itemStack, CollectibleRarity rarity) {
        super(display, description, itemStack, CollectibleType.MORPH, rarity);

        this.owner = player.getPlayer().getUniqueId();
        this.entityType = entityType;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(owner);
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void hideCollectible(User user) {
        // TODO
    }

    @Override
    public void showCollectible(User user) {
        // TODO
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

        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) this.entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();

        if (tag == null)
            tag = new NBTTagCompound();

        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        tag.setInt("Silent", 1);
        tag.setInt("Invulnerable", 1);
        nmsEntity.f(tag);
    }

    public void kill() {

        if (!this.isEntitySpawned())
            return;

        this.entity.remove();
        this.entity = null;
    }

    public void updateEntityLocation() {

        if (!this.isEntitySpawned())
            return;

        Player player = this.getPlayer();
        Location location = player.getLocation().clone().add(player.getLocation().clone().getDirection().normalize().multiply(-0.45D));

        PacketContainer pc1 = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK), pc2 = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);

        pc1.getModifier().writeDefaults();
        pc1.getIntegers().write(0, this.getEntity().getEntityId());
        pc1.getBytes().write(0, (byte) ((player.getLocation().getYaw() * 256.0F / 360.0F))).write(1, (byte) ((player.getLocation().getPitch() * 256.0F / 360.0F)));
        pc1.getBooleans().write(0, true);

        pc2.getModifier().writeDefaults();
        pc2.getIntegers().write(0, this.getEntity().getEntityId());
        pc2.getBytes().write(0, (byte) ((player.getLocation().getYaw() * 256.0F / 360.0F)));

        entity.teleport(location);

        Bukkit.getOnlinePlayers().forEach(p -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc1);
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc2);
            } catch (InvocationTargetException e) {
                    e.printStackTrace();
            }
        });
    }

    public abstract void spawnOptions(LivingEntity entity);
}
