package com.myudog.myulib.api.ecs;

import com.myudog.myulib.api.ecs.event.ComponentAddedEvent;
import com.myudog.myulib.api.ecs.lifecycle.ComponentLifecycle;
import com.myudog.myulib.api.ecs.lifecycle.DimensionChangePolicy;
import com.myudog.myulib.api.ecs.lifecycle.DimensionAware;
import com.myudog.myulib.api.ecs.lifecycle.Resettable;
import com.myudog.myulib.internal.ecs.ComponentStorage;
import com.myudog.myulib.internal.event.EventDispatcherImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcsWorld {
    public final EventDispatcherImpl eventBus = new EventDispatcherImpl();

    private int nextEntityId = 0;
    private final Map<Class<? extends Component>, ComponentStorage<? extends Component>> storages = new HashMap<>();

    public int createEntity() {
        return nextEntityId++;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> ComponentStorage<T> getStorage(Class<T> type) {
        return (ComponentStorage<T>) storages.computeIfAbsent(type, key -> new ComponentStorage<>());
    }

    public List<Integer> query(Class<? extends Component> type) {
        ComponentStorage<? extends Component> storage = storages.get(type);
        if (storage == null || storage.size() == 0) {
            return List.of();
        }
        int[] dense = storage.getRawDense();
        List<Integer> entities = new ArrayList<>(storage.size());
        for (int i = 0; i < storage.size(); i++) {
            entities.add(dense[i]);
        }
        return entities;
    }

    public void destroyEntity(int entityId) {
        for (ComponentStorage<? extends Component> storage : storages.values()) {
            storage.remove(entityId);
        }
    }

    public void resetEntity(int entityId) {
        for (ComponentStorage<? extends Component> storage : storages.values()) {
            Component component = storage.get(entityId);
            if (component instanceof Resettable resettable) {
                resettable.reset();
            }
        }
    }

    public void processDimensionChange(int entityId) {
        for (ComponentStorage<? extends Component> storage : storages.values()) {
            Component component = storage.get(entityId);
            if (component == null) {
                continue;
            }

            DimensionChangePolicy policy = ComponentLifecycle.getDimensionPolicy(component);
            if (component instanceof DimensionAware) {
                switch (policy) {
                    case REMOVE -> storage.remove(entityId);
                    case RESET -> {
                        if (component instanceof Resettable resettable) {
                            resettable.reset();
                        }
                    }
                    case KEEP -> {
                    }
                }
            }
        }
    }

    public <T extends Component> void addComponent(int entityId, T component) {
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) component.getClass();
        addComponent(entityId, type, component);
    }

    public <T extends Component> void addComponent(int entityId, Class<T> type, T component) {
        getStorage(type).add(entityId, component);
        eventBus.dispatch(new ComponentAddedEvent(entityId, component));
    }

    public <T extends Component> T getComponent(int entityId, Class<T> type) {
        @SuppressWarnings("unchecked")
        ComponentStorage<T> storage = (ComponentStorage<T>) storages.get(type);
        return storage == null ? null : storage.get(entityId);
    }

    public <T extends Component> boolean hasComponent(int entityId, Class<T> type) {
        @SuppressWarnings("unchecked")
        ComponentStorage<T> storage = (ComponentStorage<T>) storages.get(type);
        return storage != null && storage.has(entityId);
    }
}
