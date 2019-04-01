package me.chasertw123.minigames.core.user.data;

import java.util.Random;

/**
 * Created by Chase on 8/7/2017.
 */
public enum VoteSite {

    PLANET_MINECRAFT("https://www.planetminecraft.com/server/mcparadise-3999417/vote/", "PlanetMinecraft.com"),
    MINECRAFT_SERVER_LIST("http://minecraft-server-list.com/server/405313/vote/", "MCSL"),
    MINECRAFT_SERVERS("http://minecraftservers.org/vote/454951", "MinecraftServers.org"),
    MINECRAFT_SERVER("https://minecraft-server.net/vote/mcparadise", "Minecraft-Server.net"),
    MINECRAFT_MP("http://minecraft-mp.com/server/170913/vote/", "Minecraft-MP.com");

    private String voteURL, serviceName;

    VoteSite(String voteURL, String serviceName) {
        this.voteURL = voteURL;
        this.serviceName = serviceName;
    }

    public String getVoteURL() {
        return voteURL;
    }

    public String getServiceName() {
        return serviceName;
    }

    public static VoteSite getRandomVoteSite() {
        return VoteSite.values()[new Random().nextInt(VoteSite.values().length)];
    }
}
