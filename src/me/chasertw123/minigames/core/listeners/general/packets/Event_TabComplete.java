package me.chasertw123.minigames.core.listeners.general.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.chasertw123.minigames.core.Main;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.shared.rank.RankType;

/**
 * Created by Chase on 9/4/2017.
 */
public class Event_TabComplete {

    public Event_TabComplete() {

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE ) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                if (CoreAPI.getUser(e.getPlayer()).getRank().getRankType() != RankType.UPPERSTAFF)
                    e.setCancelled(true);
            }
        });
    }
}
