package me.chasertw123.minigames.core.collectibles.particles;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Particle_FireWings extends ParticleCollectible {

    private Location lastLocation;
    private long checkClears = 0;

    public Particle_FireWings(User user) {
        super(user, "Fire Wings", "I'm a flaming mess.", new ItemStack(Material.FIREBALL), CollectibleRarity.LEGENDARY, true, 6);
        this.lastLocation = user.getPlayer().getLocation();
    }

    @Override
    void onLoop() {

        Player player = Bukkit.getServer().getPlayer(getOwner());
        if (!areLocationSimilar(player.getLocation(), lastLocation)) {
            lastLocation = player.getLocation();
            checkClears = 0;
            return;
        }

        if (++checkClears < 2)
            return;

        Location loc = player.getEyeLocation().subtract(0.0D, 0.3D, 0.0D);
        loc.setPitch(0.0F);
        loc.setYaw(player.getEyeLocation().getYaw());

        Vector v1 = loc.getDirection().normalize().multiply(-0.3D);
        v1.setY(0);
        loc.add(v1);

        for (double i = -10.0D; i < 6.2D; i += 0.2D) {

            double var = Math.sin(i / 12.0D);
            double x = Math.sin(i) * (Math.exp(Math.cos(i)) - 2.0D * Math.cos(4.0D * i) - Math.pow(var, 5.0D)) / 2.0D;
            double z = Math.cos(i) * (Math.exp(Math.cos(i)) - 2.0D * Math.cos(4.0D * i) - Math.pow(var, 5.0D)) / 2.0D;
            Vector v = new Vector(-x, 0.0D, -z);

            rotateAroundAxisX(v, (loc.getPitch() + 90.0F) * 0.017453292F);
            rotateAroundAxisY(v, -loc.getYaw() * 0.017453292F);

            CoreAPI.getUser(player).playParticleEffect(loc.clone().add(v), Effect.FLAME, 0, 0, 0.0F, 0.00F, 0.0F, 0F, 1);
        }
    }

    private boolean areLocationSimilar(Location l1, Location l2) {
        return l1.getX() == l2.getX() && l1.getY() == l2.getY() && l1.getZ() == l2.getZ() && (l1.getYaw() >= (l2.getYaw() - 30) && l1.getYaw() <= (l2.getYaw() + 30));
    }

    public static final Vector rotateAroundAxisX(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static final Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

}
