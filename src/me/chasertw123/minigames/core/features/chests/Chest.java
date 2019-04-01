package me.chasertw123.minigames.core.features.chests;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.settings.Setting;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Hiett on 8/6/2017.
 */
public class Chest {

    public static List<Chest> chests = new ArrayList<>();

    private boolean active = false;
    private Location playerSpawnpoint, chestSpawnpoint;

    public Chest(Location chestSpawnpoint, Location playerSpawnpoint) {
        this.chestSpawnpoint = chestSpawnpoint;
        this.playerSpawnpoint = playerSpawnpoint;
    }

    public Location getChestSpawnpoint() {
        return chestSpawnpoint;
    }

    @SuppressWarnings("deprecation")
    public void openChest(User paradisePlayer, ChestType chestType) {

        if (active) {
            paradisePlayer.sendMessage(ChatColor.RED + "Someone is already opening a chest!");
            return;
        }

        final byte data = chestSpawnpoint.getBlock().getData();

        active = true;
        paradisePlayer.getPlayer().teleport(playerSpawnpoint);
        paradisePlayer.getPlayer().setWalkSpeed(0.0F);
        paradisePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false));
        paradisePlayer.getPlayer().setFlying(false);
        paradisePlayer.getPlayer().setAllowFlight(false);

        /*
        FallingBlock fallingBlock1 = chestSpawnpoint.getWorld().spawnFallingBlock(chestSpawnpoint.clone().add(0D, 15D, 0D), chestSpawnpoint.getBlock().getType(), chestSpawnpoint.getBlock().getData());
        FallingBlock fallingBlock2 = chestSpawnpoint.getWorld().spawnFallingBlock(chestSpawnpoint.clone().add(0D, 15D, 0D), chestSpawnpoint.getBlock().getType(), chestSpawnpoint.getBlock().getData());

        fallingBlock2.setPassenger(fallingBlock1);
        */

        ArmorStand armorStand = (ArmorStand) chestSpawnpoint.getWorld().spawnEntity(chestSpawnpoint.clone().add(0, 20, 0), EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setGravity(true);
        armorStand.setHelmet(new ItemStack(Material.CHEST));

        chestSpawnpoint.getBlock().setType(Material.AIR);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(armorStand.getLocation().clone().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                    // it has hit the ground
                    armorStand.remove();
                    chestSpawnpoint.getBlock().setType(Material.CHEST);
                    chestSpawnpoint.getBlock().setData(data);
                    active = false;
                    paradisePlayer.getPlayer().setWalkSpeed(0.2F);
                    paradisePlayer.getPlayer().removePotionEffect(PotionEffectType.JUMP);
                    paradisePlayer.getPlayer().setAllowFlight(paradisePlayer.getSetting(Setting.AUTO_FLY));

                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }

    public static void addChest(Chest chest) {
        chests.add(chest);
    }
}
