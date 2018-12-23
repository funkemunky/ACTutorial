package cc.funkemunky.tutorial.data;

import com.google.common.collect.Lists;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class DataPlayer {

    public Player player;
    public boolean onGround, inLiquid, onStairSlab, onIce, onClimbable, underBlock, onSlime;
    public int airTicks, groundTicks, iceTicks, liquidTicks, blockTicks, slimeTicks, velXTicks, velYTicks, velZTicks;
    public long lastVelocityTaken, lastAttack;
    public LivingEntity lastHitEntity;

    /** Killaura **/
    public int killauraAVerbose;
    public long lastFlying;

    /** Pattern **/
    public List<Float> patterns = Lists.newArrayList();
    public float lastRange;
    /**
     * Thresholds
     **/
    public int speedThreshold;

    public DataPlayer(Player player) {
        this.player = player;
    }

    public boolean isVelocityTaken() {
        return velXTicks > 0 || velYTicks > 0 || velZTicks > 0;
    }

    public void reduceVelocity() {
        velXTicks = Math.max(0, velXTicks - 1);
        velYTicks = Math.max(0,velYTicks - 1);
        velZTicks = Math.max(0, velZTicks - 1);
    }

}