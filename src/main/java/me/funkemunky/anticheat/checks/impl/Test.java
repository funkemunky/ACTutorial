package me.funkemunky.anticheat.checks.impl;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.Init;
import me.funkemunky.anticheat.checks.Check;
import me.funkemunky.anticheat.utils.annotations.CheckInfo;
import me.funkemunky.anticheat.utils.annotations.Packets;
import org.bukkit.event.Event;

@Init
@CheckInfo(name = "Test")
@Packets(packets = {Packet.Client.POSITION_LOOK, Packet.Client.POSITION})
public class Test extends Check {

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {
        flag("moved");
    }

    @Override
    public void onBukkit(Event event) {

    }
}
