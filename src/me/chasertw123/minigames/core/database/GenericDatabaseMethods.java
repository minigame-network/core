package me.chasertw123.minigames.core.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.features.chests.ChestType;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.VoteSite;
import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.user.data.settings.Setting;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.shared.database.Database;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GenericDatabaseMethods {

    public static boolean saveUserData(User user) {
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

        UpdateResult result = Main.getMongoDatabase().getMongoCollection(Database.Collection.CORE_USER)
                .replaceOne(Filters.eq("uuid", user.getUUID().toString()), userData, Database.upsert());


        return result.getModifiedCount() >= result.getMatchedCount();
    }

    public static boolean containsUser(MongoCollection<Document> collection, UUID uuid) {
        return collection.find(Filters.eq("uuid", uuid.toString())).first() != null;
    }
}
