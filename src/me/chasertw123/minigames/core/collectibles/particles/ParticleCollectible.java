package me.chasertw123.minigames.core.collectibles.particles;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.collectibles.Collectible;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.CollectibleType;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

/**
 * Created by Chase on 1/14/2018.
 */
public abstract class ParticleCollectible extends Collectible {

    private UUID owner;
    private long loopOccurrence;
    private BukkitTask loop = null;

    public ParticleCollectible(User paradisePlayer, String display, String description, ItemStack itemStack, CollectibleRarity rarity, boolean needsDeluxe, long loopOccurrence) {
        super(display, description, itemStack, CollectibleType.PARTICLE, rarity, needsDeluxe);
        this.owner = paradisePlayer.getUUID();
        this.loopOccurrence = loopOccurrence;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean isActive() {
        return loop != null;
    }

    abstract void onLoop();

    public void startLoop() {
        this.loop = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::onLoop, 0L, loopOccurrence);
    }

    public void cancelLoop() {
        loop.cancel();
    }
}
