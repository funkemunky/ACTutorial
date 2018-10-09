package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.MathUtils;
import cc.funkemunky.tutorial.utilities.PlayerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class Speed extends Check {

    public Speed() {
        super("Speed", CheckType.MOVEMENT, true, true, 15);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        if (data.player.getAllowFlight()
                || data.player.isInsideVehicle()
                || data.onClimbable
                || (System.currentTimeMillis() - data.lastVelocityTaken) < 4000L) {
            return;
        }
        double horizontal = MathUtils.getHorizontalDistance(event.getFrom(), event.getTo()),
                threshold = data.airTicks > 0 ? 0.36 - (0.004 * Math.min(20, data.airTicks)) : 0.32 - (0.008 * Math.min(4, data.groundTicks));

        threshold = data.inLiquid ? 0.34 + (PlayerUtils.hasDepthStrider(data.player) * 0.02) : threshold;
        threshold += data.iceTicks > 0 ? !data.onGround ? 0.22 : 0.06 : 0;
        threshold += data.blockTicks > 0 ? 0.25 : 0;
        threshold += data.onStairSlab ? 0.12 : 0;
        threshold += PlayerUtils.getPotionEffectLevel(data.player, PotionEffectType.SPEED) * 0.035;
        threshold += (data.player.getWalkSpeed() - 0.2) * 0.45;

        if (horizontal > threshold) {
            if ((data.speedThreshold += (horizontal - threshold > 0.145 ? 5 : 2)) > 30) {
                flag(data.player, horizontal + ">-" + threshold);
                data.speedThreshold = 10;
            }
        } else data.speedThreshold = Math.max(0, data.speedThreshold - 1);
    }

}
