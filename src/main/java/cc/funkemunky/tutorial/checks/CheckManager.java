package cc.funkemunky.tutorial.checks;

import cc.funkemunky.tutorial.checks.movement.Speed;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {

    private List<Check> checks = new ArrayList<>();

    public CheckManager() {
        checks.add(new Speed());
    }
    
    public List<Check> getChecks() {
        return checks;
    }
}