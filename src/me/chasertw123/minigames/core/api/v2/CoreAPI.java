package me.chasertw123.minigames.core.api.v2;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.features.boosters.BoosterManager;
import me.chasertw123.minigames.core.features.serverdata.ServerDataManager;
import me.chasertw123.minigames.core.user.OfflineUser;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.shared.config.ServerConfiguration;
import me.chasertw123.minigames.shared.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CoreAPI {

    /**
     * The prefix that is shown before some general server messages.
     * "Minigames > " is only a placeholder until the {@link ServerConfiguration} loads in the correct name.
     */
    public static String PREFIX = "Minigames > ";

    /**
     * This class cannot be instantiated.
     */
    private CoreAPI() {}

    /**
     * Gets all online {@link User} instances
     *
     * @return All online {@link User} instances
     */
    public static List<User> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(Main.getUserManager()::getUser).collect(Collectors.toList());
    }

    /**
     * Gets a {@link User} instance from a {@link Player} instance. If
     * one exists already in the cache then that one is grabbed. If not it tries
     * and load from the database/Offline Player cache, and if that fails it
     * creates a whole new instance since the player is on the server.
     *
     * @param player instance to get {@link User} of
     * @return User instance of Player
     */
    public static User getUser(Player player) {
        return Main.getUserManager().getUser(player);
    }

    /**
     * Gets an {@link OfflineUser} from a {@link UUID}. First checks cache
     * to see if it has recently been accessed, if not there then checks database for
     * the data need and loads the player. If the database doesn't contain the player
     * then null is returned
     *
     * @param uuid of the {@link OfflineUser}
     * @return OfflineUser of uuid
     */
    public static OfflineUser getUser(UUID uuid) {
        return Main.getUserManager().getUser(uuid);
    }

    /**
     * Gets an {@link OfflineUser} from a {@link String}. First checks cache
     * to see if it has recently been accessed, if not there then checks database for
     * the data need and loads the player. If the database doesn't contain the player
     * then null is returned
     *
     * @param username of the {@link OfflineUser}
     * @return OfflineUser of username
     */
    public static OfflineUser getUser(String username) {
        return Main.getUserManager().getUser(username);
    }

    /**
     * Get the core Server Config.
     * Here because loads of Mains exist.
     *
     * @return the core Server Config instance
     */
    public static ServerConfiguration getServerConfiguration() {
        return Main.getServerConfiguration();
    }

    /**
     * Gets the entire mongo database
     * @return all mongo database accessors
     */
    public static Database getDatabase() {
        return Main.getMongoDatabase();
    }

    /**
     * Gets the server data manager that minigames access in order to change queue states.
     * @return The {@link ServerDataManager} instance
     */
    public static ServerDataManager getServerDataManager() {
        return Main.getServerDataManager();
    }

    /**
     * Gets the booster manager that minigames use to determine if changes need to be made to displays.
     * Core also uses this in order to calculate the boost on EXP and Coins.
     * @return The {@link BoosterManager} from the {@link Main} class
     */
    public static BoosterManager getBoosterManager() {
        return Main.getBoosterManager();
    }
}
