package com.myudog.myulib.api.event.events;

import com.myudog.myulib.api.event.FailableEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntitySpawnEvent implements FailableEvent {
    private final Entity entity;
    private final World world;
    private String errorMessage;

    public EntitySpawnEvent(Entity entity, World world) {
        this(entity, world, null);
    }

    public EntitySpawnEvent(Entity entity, World world, String errorMessage) {
        this.entity = entity;
        this.world = world;
        this.errorMessage = errorMessage;
    }

    public Entity getEntity() {
        return entity;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
