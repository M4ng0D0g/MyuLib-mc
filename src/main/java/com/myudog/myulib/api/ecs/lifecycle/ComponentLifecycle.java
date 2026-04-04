package com.myudog.myulib.api.ecs.lifecycle;

import com.myudog.myulib.api.ecs.Component;

public final class ComponentLifecycle {
    private ComponentLifecycle() {
    }

    public static boolean isResettable(Component component) {
        return component instanceof Resettable;
    }

    public static DimensionChangePolicy getDimensionPolicy(Component component) {
        if (component instanceof DimensionAware aware) {
            return aware.getDimensionPolicy();
        }
        return DimensionChangePolicy.KEEP;
    }
}
