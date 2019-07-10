package me.funkemunky.anticheat.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    public Map<UUID, PlayerData> dataMap = new ConcurrentHashMap<>();

    public PlayerData getPlayerData(UUID uuid) {
        if(!dataMap.containsKey(uuid)) {
            createPlayerData(uuid);
        }
        return dataMap.get(uuid);
    }

    public void createPlayerData(UUID uuid) {
        dataMap.put(uuid, new PlayerData(uuid));
    }

    public void reloadUsers() {
        dataMap.clear();
        for(Player player : Bukkit.getOnlinePlayers()) {
            createPlayerData(player.getUniqueId());
        }
    }
}
