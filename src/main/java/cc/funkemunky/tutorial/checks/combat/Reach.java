package cc.funkemunky.tutorial.checks.combat;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.CustomLocation;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import javafx.geometry.BoundingBox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reach extends Check {
    public Reach(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.getInstance(), PacketType.Play.Client.KEEP_ALIVE, PacketType.Play.Server.KEEP_ALIVE) {
            DataPlayer data;
            @Override
            public void onPacketReceiving(PacketEvent event) {
                data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

                data.ping = System.currentTimeMillis() - data.lastServerKP;
            }
            @Override
            public void onPacketSending(PacketEvent event) {
                data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

                data.lastServerKP = System.currentTimeMillis();
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.getInstance(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Optional<Entity> entityOp = event.getPlayer().getWorld().getEntities().stream().filter(entity -> entity.getEntityId() == event.getPacket().getIntegers().read(0)).findFirst();

                if (entityOp.isPresent()) {
                    Entity entity = entityOp.get();

                    EnumWrappers.EntityUseAction action = event.getPacket().getEntityUseActions().read(0);

                    if (action.equals(EnumWrappers.EntityUseAction.ATTACK) && entity instanceof LivingEntity) {
                        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

                        if (data != null) {
                            data.lastHitEntity = (LivingEntity) entity;
                            Location origin = data.player.getLocation();

                            List<Vector> pastLocation = data.entityPastLocations.getEstimatedLocation(data.ping, 150).stream().map(CustomLocation::toVector).collect(Collectors.toList());

                            float distance = (float) pastLocation.stream().mapToDouble(vec -> vec.clone().setY(0).distance(origin.toVector().clone().setY(0)) - 0.3f).min().orElse(0);

                            if(distance > 3.1f) {
                                if(data.reachThreshold++ > 10) {
                                    flag(data.player, "distance=" + distance);
                                }
                            } else data.reachThreshold-= data.reachThreshold > 0 ? 1 : 0;

                            Bukkit.broadcastMessage("distance=" + distance + " threshold=" +  data.reachThreshold);
                        }
                    }
                }
            }
        });
    }
}
