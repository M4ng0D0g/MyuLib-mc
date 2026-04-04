package com.myudog.myulib.api.event.events;

import com.myudog.myulib.api.event.Event;
import net.minecraft.server.MinecraftServer;

public class ServerTickEvent implements Event {
    private final MinecraftServer server;

    public ServerTickEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return server;
    }
}
