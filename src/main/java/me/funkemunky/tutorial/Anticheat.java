package me.funkemunky.tutorial;

import cc.funkemunky.api.Atlas;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Anticheat extends JavaPlugin {

    public static Anticheat INSTANCE;

    public void onEnable() {
        System.out.println("Starting Anticheat instance v" + getDescription().getVersion() + "...");
        INSTANCE = this;

        System.out.println("Running scanner...");
        Atlas.getInstance().initializeScanner(this, true, true);

        System.out.println("Enabled Anticheat!");
    }

    public void onDisable() {
        INSTANCE = null;
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
    }
}
