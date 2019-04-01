package me.chasertw123.minigames.core.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.features.chests.ChestType;
import me.chasertw123.minigames.core.user.OfflineUser;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.VoteSite;
import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.user.data.settings.Setting;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.*;

/**
 * Created by Scott Hiett on 6/27/2017.
 */
public class NoSQLDatabase {

    public static final String CORE_COLLECTION_ID = "Core";
    public static final String HUB_COLLECTION_ID = "Hub";

    private MongoClient client;
    private MongoDatabase database;
    private HashMap<String, MongoCollection<Document>> collections;

    public NoSQLDatabase() {

        collections = new HashMap<>();
        try {

            client = new MongoClient(Main.getServerConfiguration().getMongoHost(), Main.getServerConfiguration().getMongoPort());
            database = client.getDatabase(Main.getServerConfiguration().getMongoDatabase());

            collections.put("Core", database.getCollection("Core"));
            collections.put("Hub", database.getCollection("Hub"));

            for (ServerGameType gameType : ServerGameType.values())
                collections.put(gameType.getDisplay().replace(" ", ""), database.getCollection(gameType.getDisplay().replace(" ", "")));

        } catch (Exception e) {
            System.err.println("Unable to connect to one or more database(s)!");
            Bukkit.getServer().shutdown();
        }
    }

    public User getUser(OfflineUser offlineUser) {
        return new User(offlineUser);
    }

    public boolean saveUserData(User user) {

        Document stats = new Document(), settings = new Document(), boosters = new Document(), votes = new Document(), activity = new Document(), chests = new Document(), userData = new Document();
        List<String> reports = new ArrayList<>(), achievements = new ArrayList<>(), friends = new ArrayList<>(), ignored = new ArrayList<>();

        user.getReports().forEach(obj -> reports.add(obj.toString()));
        user.getFriends().forEach(obj -> friends.add(obj.toString()));
        user.getRecentActivity().keySet().forEach(obj -> activity.append(obj.toString(), user.getRecentActivity().get(obj)));

        Arrays.stream(Stat.values()).forEach(obj -> stats.append(obj.toString(), user.getStat(obj)));
        Arrays.stream(Setting.values()).forEach(obj -> settings.append(obj.toString(), user.getSetting(obj)));
        Arrays.stream(ServerGameType.values()).forEach(obj -> boosters.append(obj.toString(), user.getBoosters().get(obj)));
        Arrays.stream(VoteSite.values()).forEach(obj -> votes.append(obj.toString(), user.getLastVoted(obj)));
        Arrays.stream(ChestType.values()).forEach(obj -> chests.append(obj.toString(), user.getChests(obj)));
        Arrays.stream(Achievement.values()).filter(user::hasAchievement).forEach(obj -> achievements.add(obj.toString()));

        userData.append("uuid", user.getUUID().toString())
                .append("joined", user.getJoinDate())
                .append("leveled", user.hasLeveled())
                .append("friends", friends)
                .append("ignored", ignored)
                .append("reports", reports)
                .append("achievements", achievements)
                .append("stats", stats)
                .append("settings", settings)
                .append("boosters", boosters)
                .append("votes", votes)
                .append("activity", activity)
                .append("chests", chests);

        UpdateResult result = getCollection(CORE_COLLECTION_ID).replaceOne(Filters.eq("uuid", user.getUUID().toString()), userData, new UpdateOptions().upsert(true));
        return result.getModifiedCount() >= result.getMatchedCount();
    }

    // Contains User
    public boolean containsUser(MongoCollection<Document> collection, UUID uuid) {
        return collection.find(Filters.eq("uuid", uuid.toString())).first() != null;
    }

    public void closeConnection(){
        client.close();
    }

    public MongoCollection<Document> getCollection(String id) {
        return collections.get(id);
    }

    public MongoCollection<Document> getCollection(ServerGameType gameType) {
        return collections.get(gameType.getDisplay().replace(" ", ""));
    }

    // Static //
    public static NoSQLDatabase getNoSQLDatabase() {
        return Main.getNoSQLDatabase();
    }

}
