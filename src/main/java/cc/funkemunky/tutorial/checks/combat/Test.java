package cc.funkemunky.tutorial.checks.combat;

import cc.funkemunky.tutorial.checks.Check;
import cc.funkemunky.tutorial.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Test extends Check {

    public Test() {
        super("Test", CheckType.COMBAT, true, true, 5);
    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            flag(player, "Wiener", "Wurst");
        }
    }
}
