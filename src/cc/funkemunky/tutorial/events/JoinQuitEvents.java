package cc.funkemunky.tutorial.events;

import cc.funkemunky.tutorial.AntiCheat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        AntiCheat.instance.dataManager.addDataPlayer(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        AntiCheat.instance.dataManager.addDataPlayer(e.getPlayer());
    }
}
