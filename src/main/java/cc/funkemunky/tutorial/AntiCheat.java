package cc.funkemunky.tutorial;

import cc.funkemunky.tutorial.checks.CheckManager;
import cc.funkemunky.tutorial.data.DataManager;
import cc.funkemunky.tutorial.events.JoinQuitEvents;
import cc.funkemunky.tutorial.events.MoveEvents;
import cc.funkemunky.tutorial.utilities.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCheat extends JavaPlugin {

    private static AntiCheat instance;
    private CheckManager checkManager;
    private DataManager dataManager;
    public boolean enabled;

    @Override
    public void onEnable() {
        instance = this;
        checkManager = new CheckManager();
        dataManager = new DataManager();

        new ReflectionUtils();

        Bukkit.getPluginManager().registerEvents(new MoveEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvents(), this);
        enabled = true;
    }

    public void onDisable() {
        enabled = false;
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static AntiCheat getInstance() {
        return instance;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}