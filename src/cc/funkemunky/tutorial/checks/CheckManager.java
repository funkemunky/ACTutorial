package cc.funkemunky.tutorial.checks;

import cc.funkemunky.tutorial.checks.combat.Test;
import cc.funkemunky.tutorial.checks.movement.Speed;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {
    public List<Check> checks;

    public CheckManager() {
        checks = new ArrayList<>();
        init();
    }

    private void init() {
        addCheck(new Speed());
    }

    public void addCheck(Check check) {
        checks.add(check);
    }

    public void removeCheck(Check check) {
        checks.remove(check);
    }
}
