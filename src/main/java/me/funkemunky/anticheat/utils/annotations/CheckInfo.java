package me.funkemunky.anticheat.utils.annotations;

import me.funkemunky.anticheat.checks.CheckType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CheckInfo {
    String name() default "Check (Type Blah Blah)";
    CheckType type() default CheckType.MOVEMENT;
    boolean enabled() default true;
    boolean bannable() default true;
    boolean cancellable() default true;
    int maxVl() default 100;
    int vlToAlert() default 10;
}
