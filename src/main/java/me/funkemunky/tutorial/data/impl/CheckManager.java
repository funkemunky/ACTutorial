package me.funkemunky.tutorial.data.impl;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import lombok.RequiredArgsConstructor;
import me.funkemunky.tutorial.Anticheat;
import me.funkemunky.tutorial.check.Check;
import me.funkemunky.tutorial.check.Packets;
import me.funkemunky.tutorial.check.impl.Killaura;
import me.funkemunky.tutorial.data.UserData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CheckManager {

    public final UserData data;

    public Set<Check> checksList = new HashSet<>();

    public void initializeChecks() {
        initCheck(new Killaura(data));
    }

    private void initCheck(Check check) {
        if (check.getClass().isAnnotationPresent(Packets.class)) {
            Atlas.getInstance().getPacketProcessor().process(Anticheat.INSTANCE,
                    listener -> {
                        if (listener.getPlayer().getUniqueId().equals(data.uuid)) {
                            check.packetEvent(listener);
                        }
                    },
                    check.getClass().getAnnotation(Packets.class).value());
        }
        checksList.add(check);
    }
}
