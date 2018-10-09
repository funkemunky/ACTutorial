package cc.funkemunky.tutorial.checks;

import cc.funkemunky.tutorial.checks.combat.Pattern;
import cc.funkemunky.tutorial.checks.movement.Speed;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {

    private List<Check> checks = new ArrayList<>();

    public CheckManager() {
        //rchecks.add(new Speed());
        checks.add(new Pattern("Pattern", CheckType.COMBAT, true, true, 10));
    }
    
    public List<Check> getChecks() {
        return checks;
    }
}