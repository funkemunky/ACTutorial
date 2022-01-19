package cc.funkemunky.tutorial.checks;

import cc.funkemunky.tutorial.checks.combat.KillauraA;
import cc.funkemunky.tutorial.checks.combat.Pattern;
import cc.funkemunky.tutorial.checks.combat.Reach;
import cc.funkemunky.tutorial.checks.movement.Fly;
import cc.funkemunky.tutorial.checks.movement.NoFall;
import cc.funkemunky.tutorial.checks.movement.SpeedA;
import cc.funkemunky.tutorial.checks.movement.SpeedB;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {

    private List<Check> checks = new ArrayList<>();

    public CheckManager() {
        //rchecks.add(new Speed());
        //checks.add(new Pattern("Pattern", CheckType.COMBAT, true, true, 10));
        checks.add(new KillauraA("Killaura (A)", CheckType.COMBAT, true, true, 20));
        checks.add(new SpeedA("Speed (A)", CheckType.MOVEMENT, true, true, 20));
        checks.add(new SpeedB("Speed (B)", CheckType.MOVEMENT, true, true, 20));
        checks.add(new Reach("Reach", CheckType.COMBAT, true, true, 50));
        checks.add(new Fly("Fly", CheckType.MOVEMENT, true, true, 50));
        checks.add(new NoFall("NoFall", CheckType.MOVEMENT, true, true, 20));
    }
    
    public List<Check> getChecks() {
        return checks;
    }
}