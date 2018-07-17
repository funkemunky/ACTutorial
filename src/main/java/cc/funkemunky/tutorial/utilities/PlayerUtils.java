package cc.funkemunky.tutorial.utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtils {

    public static boolean isOnGround(Player player) {
        return isOnGround(player, 0.25);
    }
    public static boolean isOnGround(Player player, double yExpanded) {
        Object box = ReflectionUtils.modifyBoundingBox(ReflectionUtils.getBoundingBox(player), 0, -yExpanded, 0,0,0,0);

        return ReflectionUtils.getCollidingBlocks(player, box).size() > 0;
    }

    public static boolean isOnGroundVanilla(Player player) {
        return player.isOnGround() && player.getLocation().getBlock().getLocation().subtract(0, 1, 0).getBlock().getType().isSolid();
    }
    
    public static boolean isInLiquid(Player player) {
        Object box = ReflectionUtils.getBoundingBox(player);

        double minX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "a"), box);
        double minY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "b"), box);
        double minZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "c"), box);
        double maxX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "d"), box);
        double maxY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "e"), box);
        double maxZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(BlockUtils.isLiquid(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isInStairs(Player player) {
        Object box = ReflectionUtils.modifyBoundingBox(ReflectionUtils.getBoundingBox(player), 0, -0.5,0,0,0,0);

        double minX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "a"), box);
        double minY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "b"), box);
        double minZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "c"), box);
        double maxX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "d"), box);
        double maxY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "e"), box);
        double maxZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(BlockUtils.isStair(block)
                            || BlockUtils.isSlab(block)
                            || block.getType().equals(Material.SKULL)
                            || block.getType().equals(Material.CAKE_BLOCK)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isOnClimbable(Player player) {
        return BlockUtils.isClimbableBlock(player.getLocation().getBlock())
                || BlockUtils.isClimbableBlock(player.getLocation().add(0, 1,0).getBlock());
    }

    public static float getPotionEffectLevel(Player player, PotionEffectType pet) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            if (!pe.getType().getName().equals(pet.getName())) continue;
            return pe.getAmplifier() + 1;
        }
        return 0;
    }

    public static boolean inUnderBlock(Player player) {
        Object box = ReflectionUtils.modifyBoundingBox(ReflectionUtils.getBoundingBox(player), 0, 0,0,0,1,0);

        double minX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "a"), box);
        double minY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "b"), box);
        double minZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "c"), box);
        double maxX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "d"), box);
        double maxY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "e"), box);
        double maxZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(block.getType().isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isOnIce(Player player) {
        Object box = ReflectionUtils.modifyBoundingBox(ReflectionUtils.getBoundingBox(player), 0, -0.5,0,0,0,0);

        double minX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "a"), box);
        double minY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "b"), box);
        double minZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "c"), box);
        double maxX = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "d"), box);
        double maxY = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "e"), box);
        double maxZ = (double) ReflectionUtils.getInvokedField(ReflectionUtils.getField(box.getClass(), "f"), box);

        for(double x = minX ; x < maxX ; x++) {
            for(double y = minY ; y < maxY ; y++) {
                for(double z = minZ ; z < maxZ ; z++) {
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();

                    if(block.getType().equals(Material.ICE)
                            || block.getType().equals(Material.PACKED_ICE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
