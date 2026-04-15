package com.myudog.myulib.api.projection;

import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public record ProjectionDefinition(
        @NotNull Identifier id,
        @NotNull Identifier dimensionId,
        @NotNull AABB bounds,
        String label
) {
}

