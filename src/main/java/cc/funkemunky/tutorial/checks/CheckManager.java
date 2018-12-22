package cc.funkemunky.tutorial.checks;

import cc.funkemunky.tutorial.checks.combat.KillauraA;
import cc.funkemunky.tutorial.checks.combat.Pattern;
<<<<<<< HEAD
=======
import cc.funkemunky.tutorial.checks.movement.Friction;
import cc.funkemunky.tutorial.checks.movement.Speed;
>>>>>>> 8eac785230e2bc1751d3096173d8aa5ac343c3e5

import java.util.ArrayList;
import java.util.List;

public class CheckManager {

    private List<Check> checks = new ArrayList<>();

    public CheckManager() {
        //rchecks.add(new Speed());
        checks.add(new Friction("Friction", CheckType.MOVEMENT, true, true, 69));
        checks.add(new Pattern("Pattern", CheckType.COMBAT, true, true, 10));
        checks.add(new KillauraA("Killaura (A)", CheckType.COMBAT, true, true, 20));
    }
    
    public List<Check> getChecks() {
        return checks;
    }
}