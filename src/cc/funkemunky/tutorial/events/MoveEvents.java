package cc.funkemunky.tutorial.events;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.data.DataPlayer;
import cc.funkemunky.tutorial.utilities.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvents implements Listener {

    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(event.getFrom().getX() != event.getTo().getX()
                || event.getFrom().getY() != event.getTo().getY()
                || event.getFrom().getZ() != event.getTo().getZ()) {
            DataPlayer data = AntiCheat.instance.dataManager.getDataPlayer(player);

            if(data != null) {
                data.onGround = PlayerUtils.isOnGround(player);
                data.onStairSlab = PlayerUtils.isInStairs(player);
                data.inLiquid = PlayerUtils.isInLiquid(player);
                data.onIce = PlayerUtils.isOnIce(player);
                data.onClimbable = PlayerUtils.isOnClimbable(player);
                data.underBlock = PlayerUtils.inUnderBlock(player);

                if(data.onGround) {
                    data.groundTicks++;
                    data.airTicks = 0;
                } else {
                    data.airTicks++;
                    data.groundTicks = 0;
                }
            }
        }
        Bukkit.broadcastMessage("test");
    }
}
