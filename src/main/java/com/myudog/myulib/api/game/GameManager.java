package com.myudog.myulib.api.game;

import com.myudog.myulib.api.game.bootstrap.GameBootstrapConfig;
import com.myudog.myulib.api.game.bootstrap.GameObjectConfig;
import com.myudog.myulib.api.game.components.ComponentManager;
import com.myudog.myulib.api.game.feature.GameFeature;
import com.myudog.myulib.api.game.feature.GameLogicFeature;
import com.myudog.myulib.api.game.instance.GameInstance;
import com.myudog.myulib.api.game.region.RegionManager;
import com.myudog.myulib.api.game.team.GameTeamDefinition;
import com.myudog.myulib.api.game.timer.TimerManager;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class GameManager {
    private static final Map<Identifier, GameDefinition<?>> DEFINITIONS = new LinkedHashMap<>();
    private static final Map<Integer, GameInstance<?>> INSTANCES = new ConcurrentHashMap<>();
    private static final AtomicInteger NEXT_INSTANCE_ID = new AtomicInteger(1);

    private GameManager() {
    }

    public static void install() {
    }

    public static void register(GameDefinition<?> definition) {
        DEFINITIONS.put(definition.getId(), definition);
    }

    public static GameDefinition<?> unregister(Identifier gameId) {
        return DEFINITIONS.remove(gameId);
    }

    public static boolean hasDefinition(Identifier gameId) {
        return DEFINITIONS.containsKey(gameId);
    }

    @SuppressWarnings("unchecked")
    public static <S extends Enum<S>> GameDefinition<S> definition(Identifier gameId) {
        return (GameDefinition<S>) DEFINITIONS.get(gameId);
    }

    @SuppressWarnings("unchecked")
    public static <S extends Enum<S>> GameInstance<S> createInstance(Identifier gameId, GameBootstrapConfig config) {
        GameDefinition<S> definition = (GameDefinition<S>) Objects.requireNonNull(DEFINITIONS.get(gameId), "Unknown game definition: " + gameId);
        GameBootstrapConfig bootstrap = config == null ? new GameBootstrapConfig() : config;
        definition.validateBootstrap(bootstrap);
        int instanceId = NEXT_INSTANCE_ID.getAndIncrement();
        GameInstance<S> instance = new GameInstance<>(instanceId, definition, bootstrap);
        for (GameFeature feature : definition.createFeatures(bootstrap)) {
            instance.putFeature(feature);
        }
        instance.teams().clear();
        for (GameTeamDefinition teamDefinition : definition.createTeams(bootstrap)) {
            instance.teams().register(teamDefinition);
        }
        for (GameObjectConfig objectConfig : definition.createGameObjects(bootstrap)) {
            instance.registerSpecialObject(objectConfig);
        }
        instance.getFeatureOrCreate(GameLogicFeature.class).engine.setFactsResolver(definition.createLogicFactsResolver(bootstrap));
        instance.getFeatureOrCreate(GameLogicFeature.class).bind(instance);
        instance.getFeatureOrCreate(GameLogicFeature.class).engine.registerAll(definition.createLogicRules(bootstrap));
        RegionManager.bindInstance(instance, definition.createRegions(bootstrap));
        ComponentManager.bindInstance(instance, definition.createComponentBindings(bootstrap));
        bootstrap.specialObjects().values().forEach(instance::registerSpecialObject);
        INSTANCES.put(instanceId, instance);
        definition.onCreate(instance);
        instance.logicOrNull().publishGameCreated(instance);
        return instance;
    }

    public static GameInstance<?> getInstance(int instanceId) {
        return INSTANCES.get(instanceId);
    }

    public static List<GameInstance<?>> getInstances() {
        return List.copyOf(INSTANCES.values());
    }

    public static List<GameInstance<?>> getInstances(Identifier gameId) {
        return INSTANCES.values().stream().filter(instance -> instance.getDefinition().getId().equals(gameId)).toList();
    }

    public static boolean destroyInstance(int instanceId) {
        GameInstance<?> instance = INSTANCES.remove(instanceId);
        if (instance == null) {
            return false;
        }
        instance.destroy();
        return true;
    }

    @SuppressWarnings("unchecked")
    public static <S extends Enum<S>> boolean transition(int instanceId, S to) {
        GameInstance<S> instance = (GameInstance<S>) INSTANCES.get(instanceId);
        return instance != null && instance.transition(to);
    }

    public static void tickAll() {
        for (GameInstance<?> instance : new ArrayList<>(INSTANCES.values())) {
            instance.tick();
        }
        TimerManager.update(null);
    }
}
