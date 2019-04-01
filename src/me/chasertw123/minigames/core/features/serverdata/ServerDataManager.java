package me.chasertw123.minigames.core.features.serverdata;

import com.mongodb.client.model.Filters;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.utils.ServerStateUpdate;
import me.chasertw123.minigames.shared.database.Database;
import me.chasertw123.minigames.shared.framework.GeneralServerStatus;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.framework.ServerType;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ServerDataManager {

    private String serverName = null;

    private GameMode defaultGamemode = GameMode.ADVENTURE;

    private List<ServerStateUpdate> updateQueues = new ArrayList<>();

    private ServerGameType serverGameType = null;
    private ServerType serverType = ServerType.HUB;

    public ServerDataManager() {
        requestServerName();
    }

    private void requestServerName() {
        try {
            File f = new File("servername.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            setServerName(br.readLine());

            br.close();
            fr.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setServerName(String serverName) {
        this.serverName = serverName;

        Document statusDocument = new Document();
        statusDocument.put("servername", serverName);
        statusDocument.put("status", GeneralServerStatus.RESTARTING.getId()); // Unjoinable
        statusDocument.put("maxplayers", 0);
        statusDocument.put("currentplayers", 0);

        Main.getMongoDatabase().getMongoCollection(Database.Collection.SERVER_STATUS)
                .replaceOne(Filters.eq("servername", serverName), statusDocument, Database.upsert());

        updateQueues.forEach(serverStateUpdate -> updateServerState(serverStateUpdate.getServerStatus(), serverStateUpdate.getMaxPlayers()));
        updateQueues.clear();
    }

    /**
     * The server name defined in the bungee config
     * @return the server name defined in the bungee config.
     */
    public String getServerName(){
        return serverName;
    }

    public void updateServerState(GeneralServerStatus serverStatus, int maxPlayers) {

        if(serverName == null) {
            updateQueues.add(new ServerStateUpdate(serverStatus, maxPlayers));
            return;
        }

        Document statusDocument = new Document();
        statusDocument.put("servername", serverName);
        statusDocument.put("status", serverStatus.getId());
        statusDocument.put("maxplayers", maxPlayers);
        statusDocument.put("currentplayers", Bukkit.getServer().getOnlinePlayers().size());

        Main.getMongoDatabase().getMongoCollection(Database.Collection.SERVER_STATUS)
                .replaceOne(Filters.eq("servername", serverName), statusDocument, Database.upsert());
    }

    /**
     * Get the type of minigame the server is running if it is
     * a minigame server. If it is not a minigame server this will
     * return null
     *
     * @return ServerGameType of this server
     */
    public ServerGameType getServerGameType() {
        return serverGameType;
    }

    /**
     * Set the minigame of this server
     *
     * @param serverGameType of the server
     */
    public void setServerGameType(ServerGameType serverGameType) {
        this.serverGameType = serverGameType;
    }

    /**
     * Get the gametype of the server. This determines if the server
     * is a hub, a minigame hub, or a minigame server
     *
     * @return ServerType of this server
     */
    public ServerType getServerType(){
        return serverType;
    }

    /**
     * Set the gametype of the server. This determines if the server
     * is a hub, a minigame hub, or a minigame server
     *
     * @param gameType of this server
     */
    public void setServerType(ServerType gameType) {
        this.serverType = gameType;
    }

    /**
     * Gets the default gamemode the player is set to when
     * they join the server
     *
     * @return GameMode of player's on join
     */
    public GameMode getDefaultGamemode() {
        return defaultGamemode;
    }

    /**
     * Sets the default gamemode the player is set to when
     * they join the server
     *
     * @param defaultGamemode player is set to on join
     */
    public void setDefaultGamemode(GameMode defaultGamemode) {
        this.defaultGamemode = defaultGamemode;
    }
}
