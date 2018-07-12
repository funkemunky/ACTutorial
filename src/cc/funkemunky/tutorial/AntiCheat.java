package cc.funkemunky.tutorial;

import cc.funkemunky.tutorial.checks.CheckManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCheat extends JavaPlugin {

    private static AntiCheat instance;
    private CheckManager manager;

    public void onEnable() {
        instance = this;
        manager = new CheckManager();
    }

    public static AntiCheat getInstance() {
        return instance;
    }

}
