package com.myudog.myulib.api.debug;

import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class DebugTraceManager {
    private static final Set<UUID> ENABLED = ConcurrentHashMap.newKeySet();
    private static final Map<UUID, StringBuilder> ACTIVE = new ConcurrentHashMap<>();

    private DebugTraceManager() {
    }

    public static void enable(UUID playerId) {
        if (playerId != null) {
            ENABLED.add(playerId);
        }
    }

    public static void disable(UUID playerId) {
        if (playerId != null) {
            ENABLED.remove(playerId);
            ACTIVE.remove(playerId);
        }
    }

    public static boolean isEnabled(UUID playerId) {
        return playerId != null && ENABLED.contains(playerId);
    }

    public static void begin(ServerPlayer player, String title) {
        if (player == null || !isEnabled(player.getUUID())) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[Trace] ").append(title == null ? "interaction" : title).append('\n');
        ACTIVE.put(player.getUUID(), builder);
    }

    public static void step(ServerPlayer player, String line) {
        if (player == null || !isEnabled(player.getUUID())) {
            return;
        }
        StringBuilder builder = ACTIVE.computeIfAbsent(player.getUUID(), ignored -> new StringBuilder("[Trace] interaction\n"));
        builder.append("- ").append(line == null ? "-" : line).append('\n');
    }

    public static void end(ServerPlayer player, String result) {
        if (player == null || !isEnabled(player.getUUID())) {
            return;
        }
        StringBuilder builder = ACTIVE.remove(player.getUUID());
        if (builder == null) {
            builder = new StringBuilder("[Trace] interaction\n");
        }
        builder.append("=> ").append(result == null ? "done" : result);
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal(builder.toString()));
    }
}

