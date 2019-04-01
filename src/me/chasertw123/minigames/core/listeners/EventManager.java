package me.chasertw123.minigames.core.listeners;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.features.chests.Chest;
import me.chasertw123.minigames.core.listeners.general.Event_MessageListener;
import me.chasertw123.minigames.shared.framework.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Scott Hiett on 6/27/2017.
 */
public class EventManager {

    private Listener[] minigameListeners = {
            new me.chasertw123.minigames.core.listeners.minigame.Event_PlayerJoin()
    };

    private Listener[] generalListeners = {
            new me.chasertw123.minigames.core.listeners.general.Event_AsyncPlayerChat(),
            new me.chasertw123.minigames.core.listeners.general.Event_EntityDamage(),
            new me.chasertw123.minigames.core.listeners.general.Event_FoodLevelChange(),
            new me.chasertw123.minigames.core.listeners.general.Event_InventoryClick(),
            new me.chasertw123.minigames.core.listeners.general.Event_InventoryClose(),
            new me.chasertw123.minigames.core.listeners.general.Event_LeavesDecay(),
            new me.chasertw123.minigames.core.listeners.general.Event_PlayerCommandPreprocess(),
            new me.chasertw123.minigames.core.listeners.general.Event_PlayerQuit(),
            new me.chasertw123.minigames.core.listeners.general.Event_PlayerDropItem(),
            new me.chasertw123.minigames.core.listeners.general.Event_PlayerInteract(),
            new me.chasertw123.minigames.core.listeners.general.Event_PlayerJoin()
    };

    public EventManager() {

        Event_MessageListener messageListener = new Event_MessageListener();
        new me.chasertw123.minigames.core.listeners.general.packets.Event_TabComplete();
        new me.chasertw123.minigames.core.listeners.general.packets.Event_NamedSoundEffect();

        Main.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
        Main.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "MCParadise");

        Main.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "MCParadise", messageListener);
        Main.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "BungeeCord", messageListener);

        List<Listener> activeListeners = new ArrayList<>();
        activeListeners.addAll(Arrays.asList(generalListeners));

        // TODO: Remove after testing
//        if (Main.getAPI().getServerType() == ServerType.HUB) {
//            Chest.addChest(new Chest(new Location(Bukkit.getWorld("world"), 34D, 95D, -21D), new Location(Bukkit.getWorld("world"), 37.5, 95.0, -20.5, 90, 20)));
//        }

        // Set this as else ->

        if (CoreAPI.getServerDataManager().getServerType() == ServerType.MINIGAME)
            activeListeners.addAll(Arrays.asList(minigameListeners));

        for (Listener listener : activeListeners)
            Bukkit.getServer().getPluginManager().registerEvents(listener, Main.getInstance());
    }
}
