package cc.funkemunky.tutorial;

import cc.funkemunky.tutorial.checks.CheckManager;
import cc.funkemunky.tutorial.data.DataManager;
import cc.funkemunky.tutorial.events.JoinQuitEvents;
import cc.funkemunky.tutorial.events.MoveEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCheat extends JavaPlugin {

    public static AntiCheat instance;
    public CheckManager checkManager;
    public DataManager dataManager;

    public void onEnable() {
        instance = this;
        checkManager = new CheckManager();
        dataManager = new DataManager();

        Bukkit.getPluginManager().registerEvents(new MoveEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvents(), this);
    }
}
