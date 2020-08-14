package cc.funkemunky.tutorial.checks.combat;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class KillauraA extends Check {
    public KillauraA(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.getInstance(),
                PacketType.Play.Client.POSITION,
                PacketType.Play.Client.LOOK,
                PacketType.Play.Client.POSITION_LOOK,
                PacketType.Play.Client.FLYING,
                PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());
                if(event.getPacketType().equals(PacketType.Play.Client.USE_ENTITY)) {
                    if(System.currentTimeMillis() - data.lastFlying < 5) {
                        if(data.killauraAVerbose++ > 10) {
                            flag(data.player, "sent flying packet too late.");
                        }
                    } else {
                        data.killauraAVerbose = 0;
                    }
                } else {
                    data.lastFlying = System.currentTimeMillis();
                }
            }
        });
    }
}
