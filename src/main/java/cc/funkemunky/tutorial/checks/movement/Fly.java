package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePoolListener;
import com.sun.media.sound.SF2Layer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class Fly extends Check {
    public Fly(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        if(data == null
                || event.getPlayer().getAllowFlight()
                || event.getPlayer().getVehicle() != null
                || data.inLiquid
                || data.onClimbable
                || System.currentTimeMillis() - data.lastVelocityTaken < 200L) return;

        float deltaY = (float) (event.getTo().getY() - event.getFrom().getY());

        if(data.airTicks > 2 && !data.onStairSlab && !data.nearGround && deltaY > data.lastDeltaY) {
            if(data.flyThreshold++ > 2) {
                flag(event.getPlayer(), "to=" + deltaY, "from=" + data.lastDeltaY);
            }
        } else data.flyThreshold-= data.flyThreshold > 0 ? 0.1f : 0;

        float accel = (deltaY - data.lastDeltaY);

        if(data.airTicks > 1 && Math.abs(accel) < 0.01) {
            if(data.flyThreshold++ > 3) {
                flag(event.getPlayer(), "accel=" + accel);
            }
        } else data.flyThreshold-= data.flyThreshold > 0 ? 0.25f : 0;
        data.lastAccel = accel;
        data.lastDeltaY = deltaY;
    }
}
