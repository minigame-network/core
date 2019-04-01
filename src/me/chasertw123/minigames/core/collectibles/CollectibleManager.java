package me.chasertw123.minigames.core.collectibles;

import me.chasertw123.minigames.core.collectibles.gadgets.Gadget_SheepBomb;
import me.chasertw123.minigames.core.collectibles.morphs.monsters.Morph_Creeper;
import me.chasertw123.minigames.core.collectibles.morphs.monsters.Morph_Skeleton;
import me.chasertw123.minigames.core.collectibles.particles.Particle_FireWings;
import me.chasertw123.minigames.core.collectibles.particles.Particle_RainCloud;
import me.chasertw123.minigames.core.collectibles.particles.Particle_SnowCloud;
import me.chasertw123.minigames.core.collectibles.pets.cats.types.*;

import me.chasertw123.minigames.core.user.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chase on 1/13/2018.
 */
public class CollectibleManager {

    private Map<String, Class<? extends Collectible>> collectibles = new HashMap<>();

    public CollectibleManager() {

        // Baby Cat Pets
        this.registerCollectible("Pet:BabyBlackCat", PetCat_BabyBlack.class);
        this.registerCollectible("Pet:BabyOcelotCat", PetCat_BabyOcelot.class);
        this.registerCollectible("Pet:BabyRedCat", PetCat_BabyRed.class);
        this.registerCollectible("Pet:BabySiameseCat", PetCat_BabySiamese.class);

        // Adult Cat Pets
        this.registerCollectible("Pet:BlackCat", PetCat_Black.class);
        this.registerCollectible("Pet:OcelotCat", PetCat_Ocelot.class);
        this.registerCollectible("Pet:RedCat", PetCat_Red.class);
        this.registerCollectible("Pet:SiameseCat", PetCat_Siamese.class);

        // Particles
        this.registerCollectible("Particle:RainCloud", Particle_RainCloud.class);
        this.registerCollectible("Particle:SnowCloud", Particle_SnowCloud.class);
        this.registerCollectible("Particle:FireWings", Particle_FireWings.class);

        // Morphs
        this.registerCollectible("Morph:Creeper", Morph_Creeper.class);
        this.registerCollectible("Morph:Skeleton", Morph_Skeleton.class);

        // Gadgets
        this.registerCollectible("Gadget:SheepBomb", Gadget_SheepBomb.class);
    }

    public Class<? extends Collectible> getById(String id) {
        return collectibles.get(id);
    }

    public String getId(Class<? extends Collectible> clazz) {
        return collectibles.entrySet().stream().filter(entry ->
                entry.getValue().getName().equals(clazz.getName())).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public List<Collectible> getUnlockedCollectibles(Document unlocks, User user) {

        List<Collectible> loadedCollectibles = new ArrayList<>();
        unlocks.keySet().stream().filter(obj -> collectibles.containsKey(obj)).forEach(obj -> {

            Collectible loaded = null;
            try {
                switch (CollectibleType.valueOf(obj.split(":")[0].toUpperCase())) {
                    case PARTICLE:
                        loaded = collectibles.get(obj).getConstructor(User.class).newInstance(user);
                        break;

                    case MORPH:
                        loaded = collectibles.get(obj).getConstructor(User.class).newInstance(user);
                        break;

                    case GADGET:
                        loaded = collectibles.get(obj).getConstructor(User.class).newInstance(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (loaded != null)
                loadedCollectibles.add(loaded);
        });

        return loadedCollectibles;
    }

    public int getCollectibleTypeAmount(CollectibleType type) {

        int amount = 0;
        for (String s : collectibles.keySet())
            if (s.split(":")[0].equalsIgnoreCase(type.toString()))
                amount++;

        return amount;
    }

    public List<Class<? extends Collectible>> getCollectiblesByType(CollectibleType type) {

        List<Class<? extends Collectible>> collectiblesByType = new ArrayList<>();
        for (String s : collectibles.keySet())
            if (s.split(":")[0].equalsIgnoreCase(type.toString()))
                collectiblesByType.add(collectibles.get(s));

        return collectiblesByType;
    }


    private void registerCollectible(String id, Class<? extends Collectible> clazz) {
        collectibles.put(id, clazz);
    }
}
