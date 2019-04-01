package me.chasertw123.minigames.core.features.boosters;

import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.shared.booster.EventBooster;
import me.chasertw123.minigames.shared.booster.GameBooster;
import me.chasertw123.minigames.shared.database.Database;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoosterManager {

    private EventBooster currentEventBooster = null;
    private List<GameBooster> currentGameBoosters = new ArrayList<>();

    public BoosterManager() {
        refreshBoosters();
    }

    public void refreshBoosters() {

        currentEventBooster = null;
        currentGameBoosters.clear();

        for (Document eventBooster : Main.getMongoDatabase().getMongoCollection(Database.Collection.EVENT_BOOSTERS).find())
            if (eventBooster.getLong("endtime") > System.currentTimeMillis())
                setCurrentEventBooster(new EventBooster(eventBooster.getLong("starttime"), eventBooster.getLong("endtime"),
                        eventBooster.getInteger("multiplier"), eventBooster.getString("reason")));

        for (Document gameBooster : Main.getMongoDatabase().getMongoCollection(Database.Collection.GAME_BOOSTERS).find())
            if (gameBooster.getLong("endtime") > System.currentTimeMillis())
                addGameBooster(new GameBooster(gameBooster.getLong("starttime"), gameBooster.getLong("endtime"),
                        gameBooster.getInteger("multiplier"), UUID.fromString(gameBooster.getString("activatoruuid")),
                        gameBooster.getString("activatorname"), gameBooster.getString("gamemode")));
    }

    public void setCurrentEventBooster(EventBooster currentEventBooster) {
        this.currentEventBooster = currentEventBooster;
    }

    public boolean eventBoosterActivated(){
        return currentEventBooster != null;
    }

    public EventBooster getCurrentEventBooster() {
        return currentEventBooster;
    }

    public void addGameBooster(GameBooster booster){
        this.currentGameBoosters.add(booster);
    }

    public List<GameBooster> getCurrentGameBoosters(){
        return currentGameBoosters;
    }

    public boolean boosterActive(ServerGameType gameType){
        return currentGameBoosters.parallelStream().anyMatch(gameBooster -> ServerGameType.valueOf(gameBooster.getGameMode()) == gameType);
    }

    public void removeGameBooster(GameBooster gameBooster){
        this.currentGameBoosters.remove(gameBooster);
    }

}
