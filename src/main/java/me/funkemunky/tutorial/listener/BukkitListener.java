package me.funkemunky.tutorial.listener;

import cc.funkemunky.api.utils.Init;
import me.funkemunky.tutorial.data.UserData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Init
public class BukkitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UserData.dataMap.remove(event.getPlayer().getUniqueId().toString());
    }
}
