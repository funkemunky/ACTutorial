package cc.funkemunky.tutorial.checks.movement;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.PlayerUtils;
import cc.funkemunky.tutorial.utilities.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class SpeedB extends Check {
    public SpeedB(String name, CheckType type, boolean enabled, boolean punishable, int max) {
        super(name, type, enabled, punishable, max);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        //Player movement characteristics
        double deltaXZ = Math.hypot(event.getTo().getX() - event.getFrom().getX(),
                event.getTo().getZ() - event.getFrom().getZ()),
                deltaY = event.getTo().getY() - event.getFrom().getY();
        long now = System.currentTimeMillis();

        float drag;
        double moveFactor = data.lastWalkSpeed / 2f;
        //Account for sprinting
        moveFactor+= moveFactor * 0.3f;

        //Account for potion effects
        float speedLevel = PlayerUtils.getPotionEffectLevel(event.getPlayer(), PotionEffectType.SPEED),
                slowLevel = PlayerUtils.getPotionEffectLevel(event.getPlayer(), PotionEffectType.SLOW);

        //It is important to do double conversation from float to double in multiplication as this is the real value
        //from vanilla minecraft. 0.2 float converted to double is not exactly 0.2, but rather 0.2000001234123blah blah
        if(speedLevel > 0) {
            moveFactor+= (speedLevel * (double)0.2f) * moveFactor;
        }
        if(slowLevel > 0) {
            moveFactor+= (slowLevel * (double)-0.15f) * moveFactor;
        }

        if(data.lastClientGround) {
            drag = ReflectionUtils.getFriction(event.getFrom().clone().subtract(0, 1, 0).getBlock());
            drag*= 0.91f;
            moveFactor *= 0.16277136 / (drag * drag * drag);

            //This means the player jumped
            if(!data.clientGround && deltaY > 0) {
                moveFactor+= 0.2;
            }
        } else {
            drag = 0.91f;
            moveFactor = 0.026f; //0.02f if the player is not sprinting
        }

        check: {
            if(data.inLiquid || now - data.lastVelocityTaken < 200L) break check;

            double ratio = (deltaXZ - data.lastDeltaXZ) / moveFactor;

            if(ratio > 1.008) {
                flag(event.getPlayer(), String.format("dxz=%.3f r=%.1f>-1.008", deltaXZ, ratio));
            }
        }
        data.lastDeltaXZ = deltaXZ * drag;
    }
}
