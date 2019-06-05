package me.chasertw123.minigames.core.user;

import com.mongodb.client.model.Filters;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.database.GenericDatabaseMethods;
import me.chasertw123.minigames.shared.database.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private List<User> onlinePlayers = new ArrayList<>();

    public List<User> getOnlinePlayers() {
        return onlinePlayers;
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
    public User getUser(Player player) {
        return getOnlineUser(player);
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
    public OfflineUser getUser(UUID uuid) {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if (player.getUniqueId() == uuid)
                return getOnlineUser(player);

        return getOfflineUser(uuid);
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
    public OfflineUser getUser(String username) {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if (player.getName().equalsIgnoreCase(username))
                return getOnlineUser(player);

        return getOfflineUser(username);
    }

    public OfflineUser getOfflineUser(String username) {

        for (User pp : onlinePlayers)
            if (pp.getUsername().equals(username))
                return pp;

        Document userData = Main.getMongoDatabase().getMongoCollection(Database.Collection.USERS).find(Filters.eq("lastknownusername", username)).first();

        if (userData == null)
            return null;

        UUID uuid = UUID.fromString(userData.getString("uuid"));

        Document userInfractionsData = Main.getMongoDatabase().getMongoCollection(Database.Collection.INFRACTIONS).find(Filters.eq("uuid", uuid)).first();
        Document userPunishmentsData = Main.getMongoDatabase().getMongoCollection(Database.Collection.PUNISHMENTS).find(Filters.eq("uuid", uuid)).first();

        return new OfflineUser(userData, userInfractionsData, userPunishmentsData);
    }

    public OfflineUser getOfflineUser(UUID uuid) {

        for (User pp : onlinePlayers)
            if (pp.getUUID().equals(uuid))
                return pp;

        Document userData = Main.getMongoDatabase().getMongoCollection(Database.Collection.USERS).find(Filters.eq("uuid", uuid + "")).first();

        if (userData == null)
            return new OfflineUser(uuid); // Create a new user instance. WELCOME TO MCPARADISE <3

        Document userInfractionsData = Main.getMongoDatabase().getMongoCollection(Database.Collection.INFRACTIONS).find(Filters.eq("uuid", uuid)).first();
        Document userPunishmentsData = Main.getMongoDatabase().getMongoCollection(Database.Collection.PUNISHMENTS).find(Filters.eq("uuid", uuid)).first();

        System.out.println(userData.toJson());

        return new OfflineUser(userData, userInfractionsData, userPunishmentsData);
    }

    public User getOnlineUser(Player player) {

        for (User pp : onlinePlayers)
            if (pp.getUsername().equals(player.getName()))
                return pp;

        User pp = new User(this.getOfflineUser(player.getUniqueId()));
        onlinePlayers.add(pp);

        return pp;
    }

    public void removeUser(User pp){
        onlinePlayers.remove(pp);
    }

}
