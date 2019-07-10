package me.funkemunky.anticheat.listeners;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.Init;
import me.funkemunky.anticheat.Main;
import me.funkemunky.anticheat.data.PlayerData;
import org.bukkit.event.EventHandler;

@Init
public class PacketListeners implements AtlasListener {

    @EventHandler
    public void onPacketSend(PacketSendEvent event) {
        if(event.getType().equals(Packet.Server.CHAT)) return; //To save performance on a packet that spams but we'll never use.
        PlayerData data = Main.INSTANCE.dataManager.getPlayerData(event.getPlayer().getUniqueId());

        Main.INSTANCE.executorService.execute(() -> data.fireChecks(event.getPacket(), event.getType(), event.getTimeStamp()));
    }

    @EventHandler
    public void onPacketReceive(PacketReceiveEvent event) {
        PlayerData data = Main.INSTANCE.dataManager.getPlayerData(event.getPlayer().getUniqueId());

        Main.INSTANCE.executorService.execute(() -> data.fireChecks(event.getPacket(), event.getType(), event.getTimeStamp()));
    }
}
