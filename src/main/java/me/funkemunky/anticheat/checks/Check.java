package me.funkemunky.anticheat.checks;

import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.JsonMessage;
import lombok.NoArgsConstructor;
import me.funkemunky.anticheat.Main;
import me.funkemunky.anticheat.data.PlayerData;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public abstract class Check {
    public String name;
    public CheckType type;
    public boolean enabled, bannable, cancellable;
    public int maxVL, vlToAlert, vl;
    public PlayerData data;
    public List<String> packets = new ArrayList<>();
    public List<Class<? extends Event>> events = new ArrayList<>();

    public void flag(String information, int vlToAdd) {
        if((vl+= vlToAdd) > vlToAlert) {
            Main.INSTANCE.executorService.execute(() -> {
                JsonMessage message = new JsonMessage();

                message
                        .addText(Color.translate("&8[&6Anticheat&8] &f" + data.player.getName() + " &7has failed &f" + name + " &ex" + vl))
                        .addHoverText(Color.translate("&7" + information)).setClickEvent(JsonMessage.ClickableType.RunCommand, "/teleport " + data.player.getName());

                Main.INSTANCE.checkManager.alertsEnabled.forEach(data -> message.sendToPlayer(data.player));
            });
        }
    }

    public void flag(String information) {
        flag(information, 1);
    }

    public abstract void onPacket(Object packet, String packetType, long timeStamp);

    public abstract void onBukkit(Event event);

}
