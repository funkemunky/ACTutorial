package me.funkemunky.anticheat.checks;

import cc.funkemunky.api.utils.ReflectionsUtil;
import me.funkemunky.anticheat.Main;
import me.funkemunky.anticheat.data.PlayerData;
import me.funkemunky.anticheat.utils.annotations.CheckInfo;
import me.funkemunky.anticheat.utils.annotations.Packets;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckManager {
    public List<Class<? extends Check>> checks = new ArrayList<>();
    public List<PlayerData> alertsEnabled = new ArrayList<>();

    public CheckManager() {
        new BukkitRunnable() {
            public void run() {
                alertsEnabled = Main.INSTANCE.dataManager.dataMap.keySet().stream()
                        .map(key -> Main.INSTANCE.dataManager.dataMap.get(key))
                        .filter(data -> data.alertsEnabled)
                        .collect(Collectors.toList());
            }
        }.runTaskTimerAsynchronously(Main.INSTANCE, 60L, 30L);
    }

    public Check registerCheckFromClass(Class<? extends Check> clazz) {
        Check check = (Check) ReflectionsUtil.newInstance(clazz);

        CheckInfo info = clazz.getAnnotation(CheckInfo.class);

        check.name = info.name();
        check.type = info.type();
        check.enabled = info.enabled();
        check.bannable = info.bannable();
        check.cancellable = info.cancellable();
        check.maxVL = info.maxVl();
        check.vlToAlert = info.vlToAlert();

        if(clazz.isAnnotationPresent(Packets.class)) {
            Packets packets = clazz.getAnnotation(Packets.class);

            check.packets.addAll(Arrays.asList(packets.packets()));
        }

        return check;
    }

    public void putChecksIntoData(PlayerData data) {
        data.checks.clear();
        checks.stream().map(this::registerCheckFromClass).forEach(check -> {
            check.data = data;
            Bukkit.broadcastMessage("Registered " + data.player.getName());
            data.checks.add(check);
        });
    }
}
