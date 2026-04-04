package com.myudog.myulib.api.game.bootstrap;

import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Objects;

public record GameObjectConfig(Identifier id, Identifier type, String name, boolean required, Map<String, String> properties) {
    public GameObjectConfig {
        Objects.requireNonNull(id, "id");
        properties = properties == null ? Map.of() : Map.copyOf(properties);
    }

    public GameObjectConfig(Identifier id) {
        this(id, null, null, true, Map.of());
    }

    public GameObjectConfig(Identifier id, Identifier type, String name, boolean required) {
        this(id, type, name, required, Map.of());
    }
}
