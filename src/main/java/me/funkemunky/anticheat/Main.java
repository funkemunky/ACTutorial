package me.funkemunky.anticheat;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.utils.*;
import me.funkemunky.anticheat.checks.Check;
import me.funkemunky.anticheat.checks.CheckManager;
import me.funkemunky.anticheat.data.DataManager;
import me.funkemunky.anticheat.utils.annotations.CheckInfo;
import one.util.streamex.StreamEx;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends JavaPlugin {

    public static Main INSTANCE;

    //Managers
    public DataManager dataManager;
    public CheckManager checkManager;

    public ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void onEnable() {
        INSTANCE = this;

        //Registering Managers
        dataManager = new DataManager();
        checkManager = new CheckManager();
        dataManager.reloadUsers();

        //Running scanner
        initializeScanner(getClass(), this, true, true);
    }

    public void initializeScanner(Class<?> mainClass, JavaPlugin plugin, boolean loadListeners, boolean loadCommands) {
        StreamEx.of(ClassScanner.scanFile(null, mainClass)).filter(c -> {
            try {
                Class clazz = Class.forName(c);

                return clazz.isAnnotationPresent(Init.class);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }).sorted(Comparator.comparingInt(c -> {
            try {
                Class clazz = Class.forName(c);

                Init annotation = (Init) clazz.getAnnotation(Init.class);

                return annotation.priority().getPriority();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return 3;
        })).forEachOrdered(c -> {
            try {
                Class clazz = Class.forName(c);

                if(clazz.isAnnotationPresent(Init.class)) {
                    Object obj = clazz.equals(mainClass) ? plugin : clazz.newInstance();
                    Init annotation = (Init) clazz.getAnnotation(Init.class);

                    if (loadListeners && obj instanceof Listener) {
                        MiscUtils.printToConsole("&eFound " + clazz.getSimpleName() + " Bukkit listener. Registering...");
                        plugin.getServer().getPluginManager().registerEvents((Listener) obj, plugin);
                    }
                    if(loadListeners && obj instanceof cc.funkemunky.api.event.system.Listener) {
                        MiscUtils.printToConsole("&eFound " + clazz.getSimpleName() + " (deprecated) Atlas listener. Registering...");
                        cc.funkemunky.api.event.system.EventManager.register(plugin, (cc.funkemunky.api.event.system.Listener) obj);
                    }
                    if(loadListeners && obj instanceof AtlasListener) {
                        MiscUtils.printToConsole("&eFound " + clazz.getSimpleName() + " Atlas listener. Registering...");
                        Atlas.getInstance().getEventManager().registerListeners((AtlasListener) obj, plugin);
                    }
                    if(loadCommands && obj instanceof CommandExecutor && clazz.isAnnotationPresent(Commands.class)) {
                        Commands commands = (Commands) clazz.getAnnotation(Commands.class);

                        Arrays.stream(commands.commands()).forEach(label -> {
                            if(label.length() > 0) {
                                plugin.getCommand(label).setExecutor((CommandExecutor) obj);
                                MiscUtils.printToConsole("&eRegistered ancmd " + label + " from Command Executor: " + clazz.getSimpleName());
                            }
                        });
                    }

                    if(clazz.isAnnotationPresent(CheckInfo.class) && obj instanceof Check) {
                        Check check = (Check) obj;
                        checkManager.checks.add(check.getClass());

                        MiscUtils.printToConsole("&eFound check: " + check.name);
                    }

                    if(loadCommands && annotation.commands()) Atlas.getInstance().getCommandManager().registerCommands(obj);

                    Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(ConfigSetting.class)).forEach(field -> {
                        String name = field.getAnnotation(ConfigSetting.class).name();
                        String path = field.getAnnotation(ConfigSetting.class).path() + "." + (name.length() > 0 ? name : field.getName());
                        try {
                            field.setAccessible(true);
                            MiscUtils.printToConsole("&eFound " + field.getName() + " ConfigSetting (default=" + field.get(obj) + ").");
                            if(plugin.getConfig().get(path) == null) {
                                MiscUtils.printToConsole("&eValue not found in configuration! Setting default into config...");
                                plugin.getConfig().set(path, field.get(obj));
                                plugin.saveConfig();
                            } else {
                                field.set(obj, plugin.getConfig().get(path));

                                MiscUtils.printToConsole("&eValue found in configuration! Set value to &a" + plugin.getConfig().get(path));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
