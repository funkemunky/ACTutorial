package me.funkemunky.anticheat.utils.annotations;

import org.bukkit.event.Event;

public @interface BukkitEvents {
    Class<? extends Event>[] events();
}
