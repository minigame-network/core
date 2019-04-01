package me.chasertw123.minigames.core.collectibles.gadgets;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.collectibles.CollectibleRarity;
import me.chasertw123.minigames.core.collectibles.CollectibleVisibility;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.settings.Setting;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Chase on 1/19/2018.
 */
@SuppressWarnings("deprecation")
public class Gadget_SheepBomb extends GadgetCollectible implements CollectibleVisibility {

    public static final List<Gadget_SheepBomb> SHEEP_BOMBS = new ArrayList<>();

    private static final String DISPLAY = "Sheep Bomb";
    private static final String DESCRIPTION = "Handle this with care! This has been known to wipe out entire fields!";
    private static final ItemStack ITEMSTACK = new cItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());

    private ArrayList<Sheep> sheepArrayList = new ArrayList<>();

    public Gadget_SheepBomb(User user) {
        super(user, DISPLAY, DESCRIPTION, ITEMSTACK, CollectibleRarity.EPIC, 16000);
    }

    @Override
    public void onRightClick() {

        if (isOnCooldown()) {
            displayCooldownToPlayer();
            return;
        }

        Location loc = getPlayer().getLocation().clone().add(getPlayer().getEyeLocation().getDirection().multiply(0.5));
        loc.setY(getPlayer().getLocation().getBlockY() + 1);

        Sheep sheep = getPlayer().getWorld().spawn(loc, Sheep.class);
        sheep.setNoDamageTicks(100000);
        sheep.setVelocity(getPlayer().getLocation().getDirection().multiply(1.25D));

        CoreAPI.getOnlinePlayers()
                .stream()
                .filter(pp -> !pp.getUUID().equals(getOwner()) && !pp.getSetting(Setting.PLAYER_VISIBILITY))
                .forEach(this::hideCollectible);

        sheepArrayList.add(sheep);

        clearPathfinderGoals(sheep);

        SHEEP_BOMBS.add(this);

        new SheepColorRunnable(7, true, sheep, this);
        setUsed();
    }

    @Override
    public void onLeftClick() { }

    @Override
    public void onClear() {
        sheepArrayList.stream().filter(sheep -> !sheep.isDead()).forEach(Sheep::remove);
        SHEEP_BOMBS.remove(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public cItemStack getHandStack() {

        cItemStack item = new cItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
        item.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Sheep" + ChatColor.RED + "" + ChatColor.BOLD + "Bomb " + ChatColor.GRAY + "(Right Click)");
        item.addFancyLore(DESCRIPTION, ChatColor.GRAY.toString());

        return item;
    }

    @Override
    public void hideCollectible(User paradisePlayer) {
        sheepArrayList.forEach(entity -> Main.getEntityHider().hideEntity(paradisePlayer.getPlayer(), entity));
    }

    @Override
    public void showCollectible(User paradisePlayer) {
        sheepArrayList.forEach(entity -> Main.getEntityHider().showEntity(paradisePlayer.getPlayer(), entity));
    }

    public ArrayList<Sheep> getSheepArrayList() {
        return sheepArrayList;
    }

    private void clearPathfinderGoals(org.bukkit.entity.Entity entity) {
        EntityInsentient entitySheep = (EntityInsentient) ((CraftEntity) entity).getHandle();
        try {

            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);

            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);

            bField.set(entitySheep.goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(entitySheep.targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(entitySheep.goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(entitySheep.targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SheepColorRunnable extends BukkitRunnable {

        private Random r = new Random();

        private boolean red;
        private double time;
        private Sheep sheep;
        private Gadget_SheepBomb sheepBomb;

        public SheepColorRunnable(double time, boolean red, Sheep sheep, Gadget_SheepBomb sheepBomb) {
            this.red = red;
            this.time = time;
            this.sheep = sheep;
            this.runTaskLater(Main.getInstance(), (int) time);
            this.sheepBomb = sheepBomb;
        }

        @Override
        public void run() {

            if (getPlayer() == null) {
                cancel();
                return;
            }

            User paradisePlayer = CoreAPI.getUser(getPlayer());

            if (red)
                sheep.setColor(DyeColor.RED);

            else
                sheep.setColor(DyeColor.WHITE);

            paradisePlayer.playWorldSound(sheep.getLocation(), Sound.NOTE_STICKS, 1.4F, 1.5F);

            red = !red;
            time -= 0.2;

            if (time < 0.5) {

                paradisePlayer.playWorldSound(sheep.getLocation(), Sound.EXPLODE, 1.4F, 1.5F);
                paradisePlayer.playParticleEffect(sheep.getLocation(), Effect.EXPLOSION_HUGE, 0, 0, 0F, 0F, 0F, 0F, 1);

                for (int i = 0; i < 25; i++) {

                    if (getPlayer() == null)
                        return;

                    Location babySpawn = sheep.getLocation().clone();
                    babySpawn.setYaw(r.nextFloat() * (180.0F - -180.0F) + -180.0F);

                    Sheep baby = (Sheep) sheep.getWorld().spawnEntity(babySpawn, EntityType.SHEEP);

                    baby.setColor(DyeColor.values()[r.nextInt(16)]);
                    baby.setVelocity(new Vector(r.nextDouble() - 0.5, r.nextDouble() / 2, r.nextDouble() - 0.5).multiply(1.5).add(new Vector(0, 0.8, 0)));
                    baby.setBaby();
                    baby.setAgeLock(true);

                    clearPathfinderGoals(baby);

                    CoreAPI.getOnlinePlayers()
                            .stream()
                            .filter(pp -> !pp.getUUID().equals(getOwner()) && !pp.getSetting(Setting.PLAYER_VISIBILITY))
                            .forEach(pp -> hideCollectible(pp));

                    sheepArrayList.add(baby);

                    EntityInsentient entitySheep = (EntityInsentient) ((CraftEntity) baby).getHandle();
                    NBTTagCompound tag = entitySheep.getNBTTag();
                    if (tag == null)
                        tag = new NBTTagCompound();

                    entitySheep.c(tag);
                    tag.setInt("Invulnerable", 1);
                    entitySheep.f(tag);

                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        paradisePlayer.playParticleEffect(baby.getLocation(), Effect.LARGE_SMOKE, 0, 0, 0.15F, 0F, 0.15F, 0, 3);
                        paradisePlayer.playWorldSound(baby.getLocation(), Sound.CHICKEN_EGG_POP, 1.5f, 1.0f);
                        sheepArrayList.remove(baby);
                        baby.remove();
                        SHEEP_BOMBS.remove(sheepBomb);
                    }, 40 + r.nextInt(40));
                }

                sheepArrayList.remove(sheep);
                sheep.remove();
                cancel();

            }

            else {
                Bukkit.getScheduler().cancelTask(getTaskId());
                new SheepColorRunnable(time, red, sheep, sheepBomb);
            }
        }
    }
}
