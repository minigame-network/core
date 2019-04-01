package me.chasertw123.minigames.core;

import me.chasertw123.minigames.core.api.entities.EntityHider;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.collectibles.CollectibleManager;
import me.chasertw123.minigames.core.collectibles.gadgets.Gadget_SheepBomb;
import me.chasertw123.minigames.core.commands.CommandManager;
import me.chasertw123.minigames.core.database.NoSQLDatabase;
import me.chasertw123.minigames.core.features.boosters.BoosterManager;
import me.chasertw123.minigames.core.features.boosters.Loop_BoosterCheck;
import me.chasertw123.minigames.core.features.serverdata.ServerDataManager;
import me.chasertw123.minigames.core.listeners.EventManager;
import me.chasertw123.minigames.core.user.UserManager;
import me.chasertw123.minigames.shared.config.ServerConfiguration;
import me.chasertw123.minigames.shared.database.Database;
import me.chasertw123.minigames.shared.framework.GeneralServerStatus;
import me.chasertw123.minigames.shared.framework.ServerType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static UserManager userManager;
    private static ServerDataManager serverDataManager;
    private static BoosterManager boosterManager;
    private static ServerConfiguration serverConfiguration;
    private static CollectibleManager collectibleManager;
    private static NoSQLDatabase database;
    private static EntityHider entityHider;
    private static Database mongoDatabase; // Main Database

    @Override
    public void onLoad() {
        serverConfiguration = new ServerConfiguration();
        // Set the CoreAPI prefix
        CoreAPI.PREFIX = serverConfiguration.getPrefix();

        mongoDatabase = new Database(serverConfiguration);
        collectibleManager = new CollectibleManager();
        database = new NoSQLDatabase();
        serverDataManager = new ServerDataManager();
        userManager = new UserManager();
        boosterManager = new BoosterManager();
    }

    @Override
    public void onEnable() {
        instance = this;

        entityHider = new EntityHider(this, EntityHider.Policy.BLACKLIST);
//        taskChainFactory = BukkitTaskChainFactory.create(this);

        new EventManager();
        new CommandManager();
        new Loop_BoosterCheck();
    }

    @Override
    public void onDisable() {
        Gadget_SheepBomb.SHEEP_BOMBS.forEach(Gadget_SheepBomb::onClear);
        database.closeConnection();

        if (serverDataManager.getServerType() == ServerType.MINIGAME)
            serverDataManager.updateServerState(GeneralServerStatus.DELETE, 0);

        instance = null;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    public static CollectibleManager getCollectibleManager() {
        return collectibleManager;
    }

    public static NoSQLDatabase getNoSQLDatabase() {
        return database;
    }

    public static Database getMongoDatabase() {
        return mongoDatabase;
    }

    public static EntityHider getEntityHider() {
        return entityHider;
    }

    public static ServerDataManager getServerDataManager() {
        return serverDataManager;
    }

    public static BoosterManager getBoosterManager() {
        return boosterManager;
    }

    public static Main getInstance(){
        return instance;
    }
}
