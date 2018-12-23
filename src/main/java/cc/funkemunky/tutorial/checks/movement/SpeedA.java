package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class SpeedA extends Check {
    public SpeedA(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        //1.9 check for player.isGliding and in 1.13 check for player.isRiptiding().
        if(event.getPlayer().getAllowFlight()
                || event.getPlayer().isInsideVehicle()
                || data.isVelocityTaken()) {
            return;
        }
        float threshold = event.getPlayer().isOnGround() ? 0.31f : .341f;

        float deltaXZ = (float) Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2) + Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2)), deltaY = (float) (event.getTo().getY() - event.getFrom().getY());
        float speedLevel = PlayerUtils.getPotionEffectLevel(event.getPlayer(), PotionEffectType.SPEED);

        threshold+= data.slimeTicks > 0 ? 0.07f : 0;
        threshold+= data.groundTicks < 5 ? speedLevel * 0.06f : speedLevel * 0.04f;
        threshold*= data.onStairSlab ? 1.8f : 1.0;
        threshold*= data.iceTicks > 0 && data.groundTicks < 5 ? 1.7f : 1.0;
        threshold*= data.blockTicks > 0 && deltaY != 0 ? 2.0f : 1.0;

        if(deltaXZ > threshold) {
            if((data.speedThreshold+= 2) > 25) {
                flag(event.getPlayer(), deltaXZ + ">-" + threshold);
                data.speedThreshold = 0;
            }
        } else {
            data.speedThreshold = Math.max(0, data.speedThreshold - 1);
        }

        Bukkit.broadcastMessage(data.speedThreshold + ": " + deltaXZ + ">" + threshold + ", " + event.getPlayer().isOnGround());
    }
}
