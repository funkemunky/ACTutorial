package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.MathUtils;
import cc.funkemunky.tutorial.utilities.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class Speed extends Check {

    public Speed() {
        super("Speed", CheckType.MOVEMENT, true, true, 10);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(e.getPlayer());

        if (data == null) {
            return;
        }

        float xDelta = MathUtils.getHorizontalDistance(e.getFrom(), e.getTo());
        float threshold = (float) (data.airTicks > 0 ? data.airTicks > 6 ? 0.318 : 0.338 : data.groundTicks > 5 ? 0.287 : 0.32);

        threshold += PlayerUtils.getPotionEffectLevel(data.player, PotionEffectType.SPEED) * 0.06;
        threshold += data.onIce ? data.underBlock ? 0.5f : .2f : 0f;
        threshold += data.underBlock ? 0.4f : 0f;
        threshold += data.onStairSlab ? 0.2f : 0f;

        if (xDelta > threshold) {
            if ((data.speedThreshold += 2) > 40) {
                flag(data.player, xDelta + " > " + threshold);
            }
        } else if (data.speedThreshold > 0) {
            data.speedThreshold--;
        }

        Bukkit.broadcastMessage(xDelta + ", " + threshold + ", " + data.speedThreshold + " (" + data.airTicks + ", " + data.groundTicks + ")");

    }
}
