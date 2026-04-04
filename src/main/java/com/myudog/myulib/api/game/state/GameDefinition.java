package com.myudog.myulib.api.game.state;

import com.myudog.myulib.api.game.bootstrap.GameBootstrapConfig;
import com.myudog.myulib.api.game.components.ComponentModels.ComponentBindingDefinition;
import com.myudog.myulib.api.game.feature.GameFeature;
import com.myudog.myulib.api.game.instance.GameInstance;
import com.myudog.myulib.api.game.logic.LogicContracts.LogicRule;
import com.myudog.myulib.api.game.logic.facts.LogicFactsResolver;
import com.myudog.myulib.api.game.region.RegionModels.RegionDefinition;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class GameDefinition<S extends Enum<S>> {
    private final Identifier id;

    protected GameDefinition(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }

    public abstract S getInitialState();

    public abstract Map<S, Set<S>> getAllowedTransitions();

    public Set<Identifier> getRequiredSpecialObjectIds() {
        return Collections.emptySet();
    }

    public List<GameFeature> createFeatures(GameBootstrapConfig config) {
        return List.of();
    }

    public List<LogicRule<S>> createLogicRules(GameBootstrapConfig config) {
        return List.of();
    }

    public List<RegionDefinition> createRegions(GameBootstrapConfig config) {
        return List.of();
    }

    public List<ComponentBindingDefinition> createComponentBindings(GameBootstrapConfig config) {
        return List.of();
    }

    public LogicFactsResolver createLogicFactsResolver(GameBootstrapConfig config) {
        return LogicFactsResolver.DEFAULT;
    }

    public boolean isTransitionAllowed(S from, S to) {
        Set<S> allowed = getAllowedTransitions().get(from);
        return allowed != null && allowed.contains(to);
    }

    public void onCreate(GameInstance<S> instance) {
    }

    public void onExitState(GameInstance<S> instance, GameStateContext<S> context) {
    }

    public void onEnterState(GameInstance<S> instance, GameStateContext<S> context) {
    }

    public void onTick(GameInstance<S> instance) {
    }

    public void onDestroy(GameInstance<S> instance) {
    }

    public void validateBootstrap(GameBootstrapConfig config) {
        for (Identifier required : getRequiredSpecialObjectIds()) {
            if (!config.specialObjects().containsKey(required)) {
                throw new IllegalStateException("Missing required special object: " + required);
            }
        }
    }
}
