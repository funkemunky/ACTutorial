package me.funkemunky.tutorial.listener;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import cc.funkemunky.api.utils.Init;
import me.funkemunky.tutorial.data.UserData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Init
public class PacketListener implements AtlasListener {

    public static ExecutorService thread = Executors.newSingleThreadExecutor();

    @Listen
    public void onEvent(PacketReceiveEvent event) {
        UserData data = UserData.getData(event.getPlayer().getUniqueId());

        thread.execute(() -> data.checkManager.onReceive(event));
    }

    @Listen
    public void onEvent(PacketSendEvent event) {
        UserData data = UserData.getData(event.getPlayer().getUniqueId());

        thread.execute(() -> data.checkManager.onSend(event));
    }
}
