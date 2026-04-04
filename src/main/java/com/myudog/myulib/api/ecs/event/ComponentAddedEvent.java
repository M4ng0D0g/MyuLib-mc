package com.myudog.myulib.api.ecs.event;

import com.myudog.myulib.api.ecs.Component;
import com.myudog.myulib.api.event.Event;

public class ComponentAddedEvent implements Event {
    private final int entityId;
    private final Component component;

    public ComponentAddedEvent(int entityId, Component component) {
        this.entityId = entityId;
        this.component = component;
    }

    public int getEntityId() {
        return entityId;
    }

    public Component getComponent() {
        return component;
    }
}
