package me.chasertw123.minigames.core.collectibles.particles;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Chase on 1/14/2018.
 */
public class Particle_RainCloud extends ParticleCollectible {

    private static final String DISPLAY = "Personal Rain Cloud";
    private static final String DESCRIPTION = "Write some dumb pun about a rain cloud, and cry myself to sleep because of it.";

    private Random random;
    private long lifepan = 0L;

    public Particle_RainCloud(User paradisePlayer) {
        super(paradisePlayer, DISPLAY, DESCRIPTION, new ItemStack(Material.WATER_BUCKET), CollectibleRarity.EPIC, false, 1L);
        random = new Random();
    }

    @Override
    void onLoop() {

        User pp = CoreAPI.getUser(Bukkit.getPlayer(this.getOwner()));
        Location location = pp.getPlayer().getEyeLocation().clone().add(0D, 1.5D, 0D);

        if (lifepan % 4 == 0)
            pp.playParticleEffect(location, Effect.CLOUD, 0, 0, 0.3F, 0.075F, 0.3F, 0F, 12);

        if (random.nextBoolean())
            pp.playParticleEffect(location, Effect.WATERDRIP, 0, 0, 0.135F, 0F, 0.135F, 0F, random.nextInt(3) + 1);

        lifepan++;
    }

    @Override
    public void cancelLoop() {
        super.cancelLoop();
        this.lifepan = 0L;
    }
}
