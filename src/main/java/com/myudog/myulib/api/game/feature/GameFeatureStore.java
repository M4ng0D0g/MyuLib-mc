package com.myudog.myulib.api.game.feature;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GameFeatureStore {
    private final Map<Class<? extends GameFeature>, GameFeature> features = new LinkedHashMap<>();

    public <T extends GameFeature> T put(T feature) {
        Objects.requireNonNull(feature, "feature");
        features.put(feature.getClass(), feature);
        return feature;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameFeature> T get(Class<T> type) {
        return (T) features.get(type);
    }

    public <T extends GameFeature> T require(Class<T> type) {
        T feature = get(type);
        if (feature == null) {
            throw new IllegalStateException("Missing feature: " + type.getName());
        }
        return feature;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameFeature> T remove(Class<T> type) {
        return (T) features.remove(type);
    }

    public void clear() {
        features.clear();
    }

    public Map<Class<? extends GameFeature>, GameFeature> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(features));
    }
}
