package me.funkemunky.anticheat.data;

import me.funkemunky.anticheat.Main;
import me.funkemunky.anticheat.checks.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    public UUID uuid;
    public Player player;
    public boolean alertsEnabled;
    public List<Check> checks = new ArrayList<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
        Main.INSTANCE.checkManager.putChecksIntoData(this);

        if(player.hasPermission("anticheat.alerts")) {
            alertsEnabled = true;
        }
    }

    public void fireChecks(Object packet, String packetType, long timeStamp) {
        checks.stream().filter(check -> check.enabled && check.packets.contains(packetType)).forEach(check -> check.onPacket(packet, packetType, timeStamp));
    }

    public void fireChecks(Event event) {
        checks.stream().filter(check -> check.enabled && check.events.contains(event.getClass())).forEach(check -> check.onBukkit(event));
    }
}
