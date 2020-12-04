package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFall extends Check {

    public NoFall(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);
    }

    private static double groundY = 1 / 64.;
    private int buffer = 0;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        boolean clientGround = event.getPlayer().isOnGround(),
                //We use the value < 0.0001 instead of == 0 since 1.9+ does not clamp motion values like older versions.
                //This also helps when some information is missing
                serverGround = event.getTo().getY() % groundY < 0.0001;

        //We have to use lServerGround since event.getPlayer().isOnGround() will be a tick behind in the moveEvent.
        //It's never updated until after the PlayerMoveEvent is ran.
        if(clientGround != data.lServerGround) {
            if(++buffer > 1 || !data.nearGround || !data.lServerGround) { //false positives will occurr without the buffer.
                //except in those scenarios. teseted on slabs/stairs for this. buffer is also for block place jumping
                flag(event.getPlayer(), "c=" + clientGround, "s=" + data.lServerGround,
                        "y=" + event.getFrom().getY(), "gy=" + event.getFrom().getY() % groundY);
            }
        } else if(buffer > 0) buffer--;

        data.lServerGround = serverGround;
    }
}
