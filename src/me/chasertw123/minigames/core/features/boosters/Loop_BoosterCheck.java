package me.chasertw123.minigames.core.features.boosters;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.event.BoosterUpdateEvent;
import me.chasertw123.minigames.shared.booster.GameBooster;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Hiett on 8/4/2017.
 */
public class Loop_BoosterCheck extends BukkitRunnable {

    public Loop_BoosterCheck() {
        this.runTaskTimer(Main.getInstance(), 0L, 20 * 60);
    }

    @Override
    public void run() {
        if (CoreAPI.getBoosterManager().eventBoosterActivated())
            if (CoreAPI.getBoosterManager().getCurrentEventBooster().getEndTime() < System.currentTimeMillis())
                CoreAPI.getBoosterManager().setCurrentEventBooster(null);

        if (CoreAPI.getBoosterManager().getCurrentGameBoosters().size() > 0) {

            List<GameBooster> toRemove = new ArrayList<>();
            for (GameBooster bo : CoreAPI.getBoosterManager().getCurrentGameBoosters())
                if (bo.getEndTime() < System.currentTimeMillis())
                    toRemove.add(bo);

            for(GameBooster booster : toRemove)
                CoreAPI.getBoosterManager().removeGameBooster(booster);

            Bukkit.getPluginManager().callEvent(new BoosterUpdateEvent());
        }
    }

}
