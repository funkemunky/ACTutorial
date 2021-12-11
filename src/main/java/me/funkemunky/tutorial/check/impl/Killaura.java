package me.funkemunky.tutorial.check.impl;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.listener.PacketInfo;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import me.funkemunky.tutorial.check.Check;
import me.funkemunky.tutorial.check.CheckType;
import me.funkemunky.tutorial.check.Packets;
import me.funkemunky.tutorial.data.UserData;

@Packets({Packet.Client.ARM_ANIMATION, Packet.Client.USE_ENTITY})
public class Killaura extends Check {

    public Killaura(UserData data) {
        super("Killaura (HM)", CheckType.COMBAT, data);
    }

    private int arm, use;

    @Override
    public void packetEvent(PacketInfo listener) {
        switch (listener.getType()) {
            case Packet.Client.USE_ENTITY: {
                use++;
                break;
            }
            case Packet.Client.ARM_ANIMATION: {
                if(++arm >= 20) {
                    float ratio =
                }
                break;
            }
        }
    }
}
