package me.funkemunky.tutorial.data;

import me.funkemunky.tutorial.data.impl.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserData {
    public static Map<Integer, UserData> dataMap = Collections.synchronizedMap(new HashMap<>());

    public final UUID uuid;
    private Player player;
    public CheckManager checkManager;

    public UserData(UUID uuid) {
        this.uuid = uuid;

        checkManager = new CheckManager(this);
        checkManager.initializeChecks();
    }

    public static UserData getData(UUID uuid) {
        return dataMap.computeIfAbsent(uuid.hashCode(), key -> new UserData(uuid));
    }

    public Player getPlayer() {
        if(player == null) this.player = Bukkit.getPlayer(uuid);
        if(player == null) dataMap.remove(uuid.hashCode());

        return player;
    }
}
