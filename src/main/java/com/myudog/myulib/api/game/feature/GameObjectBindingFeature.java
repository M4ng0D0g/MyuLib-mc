package com.myudog.myulib.api.game.feature;

import com.myudog.myulib.api.game.bootstrap.GameObjectConfig;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class GameObjectBindingFeature implements GameFeature {
    public final Map<Identifier, GameObjectConfig> requiredConfigs = new LinkedHashMap<>();
    public final Map<Identifier, Object> runtimeBindings = new LinkedHashMap<>();

    public void bind(GameObjectConfig config, Object runtimeObject) {
        requiredConfigs.put(config.id(), config);
        if (runtimeObject != null) {
            runtimeBindings.put(config.id(), runtimeObject);
        }
    }

    public void attachRuntime(Identifier id, Object runtimeObject) {
        runtimeBindings.put(id, runtimeObject);
    }

    public Optional<Object> getRuntime(Identifier id) {
        return Optional.ofNullable(runtimeBindings.get(id));
    }

    public void clear() {
        requiredConfigs.clear();
        runtimeBindings.clear();
    }
}
