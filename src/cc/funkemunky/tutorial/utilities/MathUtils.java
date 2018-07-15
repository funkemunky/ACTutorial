package cc.funkemunky.tutorial.utilities;

import org.bukkit.Location;

public class MathUtils {

    public static float getHorizontalDistance(Location from, Location to) {
        Location fromClone = from.clone();
        Location toClone = to.clone();
        fromClone.setY(0);
        toClone.setY(0);
        return (float) fromClone.distance(toClone);
    }
}
