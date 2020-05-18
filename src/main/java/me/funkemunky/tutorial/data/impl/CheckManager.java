package me.funkemunky.tutorial.data.impl;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import lombok.RequiredArgsConstructor;
import me.funkemunky.tutorial.check.Check;
import me.funkemunky.tutorial.data.UserData;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CheckManager {

    public final UserData data;

    public List<Check> checksList = new ArrayList<>();

    public void onReceive(PacketReceiveEvent event) {

    }

    public void onSend(PacketSendEvent event) {

    }

    public void initializeChecks() {
        //TODO Inserts checks here
    }

    private void initCheck(Check check) {
        checksList.add(check);
    }
}
