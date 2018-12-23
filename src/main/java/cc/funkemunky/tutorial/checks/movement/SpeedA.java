package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpeedA extends Check {
    public SpeedA(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());
        float deltaXZ = (float) Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2) + Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2));

        float threshold = data.onGround ? 0.31f : 0.345f;

        threshold += data.slimeTicks > 0 ? 0.08f : 0;
        threshold *= data.blockTicks > 0 ? 2.5f : 1.0f;
        threshold *= data.onStairSlab ? 2.0f : 1.0;
        threshold *= data.iceTicks > 0 && data.groundTicks < 5 ? 2.5f : 1.0f;

        if(deltaXZ > threshold) {
            if((data.speedThreshold+= 2) > 25) {
                flag(event.getPlayer(), deltaXZ + ">-" + threshold);
                data.speedThreshold = 0;
            }
        } else {
            data.speedThreshold = Math.max(0, data.speedThreshold - 1);
        }

        //Bukkit.broadcastMessage(data.speedThreshold + ": " + deltaXZ + ">" + threshold + ", " + data.onGround);
    }
}
