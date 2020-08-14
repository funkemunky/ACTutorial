package cc.funkemunky.tutorial.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DataManager {

    private Set<DataPlayer> dataSet = new HashSet<>();

    public DataManager() {
        Bukkit.getOnlinePlayers().forEach(this::add);
    }

    public DataPlayer getDataPlayer(Player player) {
        return dataSet.stream().filter(dataPlayer -> dataPlayer.player == player).findFirst().orElse(null);
    }

    public void add(Player player) {
        dataSet.add(new DataPlayer(player));
    }

    public void remove(Player player) {
        dataSet.removeIf(dataPlayer -> dataPlayer.player == player);
    }
}