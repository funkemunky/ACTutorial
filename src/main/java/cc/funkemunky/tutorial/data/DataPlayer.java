package cc.funkemunky.tutorial.data;

import cc.funkemunky.tutorial.AntiCheat;
import cc.funkemunky.tutorial.utilities.PastLocation;
import com.google.common.collect.Lists;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DataPlayer {

    public Player player;
    public Object boundingBox;
    public boolean onGround, clientGround, lastClientGround,
            inLiquid, onStairSlab, onIce, onClimbable, underBlock, onSlime, nearGround;
    public int airTicks, groundTicks, iceTicks, liquidTicks, blockTicks, slimeTicks, velXTicks, velYTicks, velZTicks;
    public long lastVelocityTaken, lastAttack, lastServerKP, ping;
    public double walkSpeed = 0.2, lastWalkSpeed = 0.2, lastDeltaXZ;
    public LivingEntity lastHitEntity;
    public PastLocation entityPastLocations = new PastLocation();

    /** Killaura **/
    public int killauraAVerbose;
    public long lastFlying;

    /** Pattern **/
    public List<Float> patterns = Lists.newArrayList();
    public float lastRange;

    /** Fly **/
    public float lastDeltaY, lastAccel;

    /** NoFall **/
    public boolean lServerGround = true; //player will most likely be on ground by default

    /**
     * Thresholds
     **/
    public int speedThreshold;
    public int reachThreshold;
    public float flyThreshold;

    public DataPlayer(Player player) {
        this.player = player;

        new BukkitRunnable() {
            public void run() {
                if(lastHitEntity != null) {
                    entityPastLocations.addLocation(lastHitEntity.getLocation());
                }
            }
        }.runTaskTimer(AntiCheat.getInstance(), 0L, 1L);
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