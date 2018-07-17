package cc.funkemunky.tutorial.data;

import org.bukkit.entity.Player;

public class DataPlayer {

    public Player player;
    public boolean onGround, inLiquid, onStairSlab, onIce, onClimbable, underBlock;
    public int airTicks, groundTicks;

    /**
     * Thresholds
     **/
    public int speedThreshold;

    public DataPlayer(Player player) {
        this.player = player;
    }

}