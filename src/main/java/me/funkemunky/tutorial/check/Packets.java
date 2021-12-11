package me.funkemunky.tutorial.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Packets {
    String[] value();
}
