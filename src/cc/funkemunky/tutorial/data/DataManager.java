package cc.funkemunky.tutorial.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class DataManager {
    public Set<DataPlayer> dataObjects;

    public DataManager() {
        dataObjects = new HashSet<>();
        createDataObjects();
    }

    private void createDataObjects() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            addDataPlayer(player);
        }
    }

    public void addDataPlayer(Player player) {
        dataObjects.add(new DataPlayer(player));
    }

    public void removeDataPlayer(DataPlayer data) {
        dataObjects.remove(data);
    }

    public DataPlayer getDataPlayer(Player player) {
        for(DataPlayer data : dataObjects) {
            if(data.player == player) {
                return data;
            }
        }
        return null;
    }




}
