package cc.funkemunky.tutorial.events;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.data.DataPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        AntiCheat.getInstance().getDataManager().add(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        AntiCheat.getInstance().getDataManager().remove(e.getPlayer());
    }
}