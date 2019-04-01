package me.chasertw123.minigames.core.collectibles.particles;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

/**
 * Created by Chase on 1/15/2018.
 */
public class Particle_SnowCloud extends ParticleCollectible {

    private static final String DISPLAY = "Personal Snow Cloud";
    private static final String DESCRIPTION = "Something about a could that follows you around. (WIP)";

    private long lifepan = 0L;
    private Random random = null;

    public Particle_SnowCloud(User paradisePlayer) {
        super(paradisePlayer, DISPLAY, DESCRIPTION, new cItemStack(Material.SNOW_BALL), CollectibleRarity.RARE, false, 1);
        this.random = new Random();
    }

    @Override
    void onLoop() {

        User pp = CoreAPI.getUser(Bukkit.getPlayer(this.getOwner()));
        Location location = pp.getPlayer().getEyeLocation().clone().add(0D, 1.5D, 0D);

        if (lifepan % 4 == 0)
            pp.playParticleEffect(location, Effect.CLOUD, 0, 0, 0.3F, 0.075F, 0.3F, 0F, 12);

        if (random.nextBoolean())
            pp.playParticleEffect(location, Effect.SNOW_SHOVEL, 0, 0, 0.175F, 0F, 0.175F, 0F, random.nextInt(3) + 1); // TODO: Verify Particle

        lifepan++;
    }

    @Override
    public void cancelLoop() {
        super.cancelLoop();
        this.lifepan = 0L;
    }
}
