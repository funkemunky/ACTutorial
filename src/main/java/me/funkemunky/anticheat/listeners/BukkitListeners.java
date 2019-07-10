package me.funkemunky.anticheat.listeners;

import cc.funkemunky.api.utils.Init;
import me.funkemunky.anticheat.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Init
public class BukkitListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.INSTANCE.dataManager.createPlayerData(event.getPlayer().getUniqueId());
    }
}
