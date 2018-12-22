package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Friction extends Check {
    public Friction(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        DataPlayer player = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        float deltaXZ = (float) Math.hypot(event.getTo().getX() - event.getFrom().getX(), event.getTo().getZ() - event.getFrom().getZ()),
                friction = event.getPlayer().isOnGround() ? ReflectionUtils.getFriction(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock()) * 0.91f : 0.91f,
                predicted = deltaXZ * friction;

        Bukkit.broadcastMessage(Math.abs(deltaXZ - predicted) + ", " + event.getPlayer().isOnGround());

        if(Math.abs(deltaXZ - predicted) > (event.getPlayer().isOnGround() ? 0.3 : 0.033)) {
            flag(event.getPlayer(), Math.abs(deltaXZ - predicted) + ">-" + (event.getPlayer().isOnGround() ? 0.3 : 0.033));
        }
        player.lastDeltaXZ = deltaXZ;
    }
}
