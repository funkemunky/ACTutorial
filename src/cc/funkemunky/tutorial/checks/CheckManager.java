package cc.funkemunky.tutorial.checks;

import cc.funkemunky.tutorial.checks.combat.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {
    public List<Check> checks;

    public CheckManager() {
        checks = new ArrayList<>();
        init();
    }

    private void init() {
        addCheck(new Test());
    }

    public void addCheck(Check check) {
        checks.add(check);
    }

    public void removeCheck(Check check) {
        checks.remove(check);
    }
}
