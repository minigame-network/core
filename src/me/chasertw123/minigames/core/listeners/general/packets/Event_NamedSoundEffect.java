package me.chasertw123.minigames.core.listeners.general.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.chasertw123.minigames.core.Main;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.sounds.SoundEffect;

/**
 * Created by Chase on 1/19/2018.
 */
public class Event_NamedSoundEffect {

    public Event_NamedSoundEffect() {

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {

            @Override
            public void onPacketSending(PacketEvent event) {

                SoundEffect soundEffect = SoundEffect.getByName(event.getPacket().getStrings().read(0));
                Location location = new Location(event.getPlayer().getWorld(),
                        event.getPacket().getIntegers().read(0) / 8.0D,
                        event.getPacket().getIntegers().read(1) / 8.0D,
                        event.getPacket().getIntegers().read(2) / 8.0D);

                if (soundEffect == null)
                    return;

                LivingEntity soundMaker = null;
                for (Entity entity : event.getPlayer().getWorld().getNearbyEntities(location, 1, 1, 1))
                    if (entity != null)
                        if (entity instanceof LivingEntity && soundEffect.toString().contains(entity.getType().toString())) {
                            soundMaker = (LivingEntity) entity;
                            break;
                        }

                if (soundMaker != null && !Main.getEntityHider().canSee(event.getPlayer(), soundMaker))
                    event.setCancelled(true);
            }
        });
    }

}
