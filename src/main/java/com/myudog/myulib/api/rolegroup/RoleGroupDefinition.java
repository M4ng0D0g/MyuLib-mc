package com.myudog.myulib.api.rolegroup;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record RoleGroupDefinition(
        @NotNull Identifier id,
        @NotNull MutableComponent translationKey,
        int priority, // 數值越大，覆蓋權限的優先級越高
        Map<String, String> metadata,
        Set<UUID> members
) {
    public RoleGroupDefinition {
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        members = members == null ? Set.of() : Set.copyOf(members);
    }

    public boolean hasMember(UUID playerId) {
        return members != null && members.contains(playerId);
    }
}