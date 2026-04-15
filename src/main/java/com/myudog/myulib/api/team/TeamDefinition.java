package com.myudog.myulib.api.team;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public record TeamDefinition(
        @NotNull Identifier id,
        @NotNull MutableComponent translationKey,
        @NotNull TeamColor color,
        Map<TeamFlag, Boolean> flags
) {
    public TeamDefinition {
        EnumMap<TeamFlag, Boolean> optimizedFlags = new EnumMap<>(TeamFlag.class);
        if (flags != null && !flags.isEmpty()) {
            optimizedFlags.putAll(flags);
        }
        flags = optimizedFlags;
    }
}

