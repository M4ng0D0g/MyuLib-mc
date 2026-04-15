package com.myudog.myulib.api.projection;

import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ProjectionManager {
    private static final ConcurrentHashMap<Identifier, ProjectionDefinition> PROJECTIONS = new ConcurrentHashMap<>();

    private ProjectionManager() {
    }

    public static ProjectionDefinition register(ProjectionDefinition definition) {
        PROJECTIONS.put(definition.id(), definition);
        return definition;
    }

    public static void unregister(Identifier id) {
        PROJECTIONS.remove(id);
    }

    public static ProjectionDefinition get(Identifier id) {
        return PROJECTIONS.get(id);
    }

    public static Map<Identifier, ProjectionDefinition> all() {
        return Map.copyOf(PROJECTIONS);
    }

    public static void clear() {
        PROJECTIONS.clear();
    }

    public static AABB cuboidFromCorners(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new AABB(
                Math.min(x1, x2),
                Math.min(y1, y2),
                Math.min(z1, z2),
                Math.max(x1, x2),
                Math.max(y1, y2),
                Math.max(z1, z2)
        );
    }
}

