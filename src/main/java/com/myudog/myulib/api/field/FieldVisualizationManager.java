package com.myudog.myulib.api.field;

import com.myudog.myulib.api.projection.ProjectionDefinition;
import com.myudog.myulib.api.projection.ProjectionFeature;
import com.myudog.myulib.api.projection.ProjectionManager;
import com.myudog.myulib.api.projection.ProjectionRenderStyle;
import com.myudog.myulib.api.projection.network.ProjectionNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class FieldVisualizationManager {
    private static final Set<UUID> ENABLED = ConcurrentHashMap.newKeySet();
    private static final ConcurrentHashMap<UUID, Integer> LAST_SYNC_TICK = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Integer> PLAYER_RADIUS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, ProjectionRenderStyle> PLAYER_STYLE = new ConcurrentHashMap<>();
    private static volatile boolean installed;
    private static final int SYNC_INTERVAL_TICKS = 5;

    public enum DisplayMode {
        EDGES_ONLY,
        FULL,
        LABELS_ONLY;

        public String token() {
            return name().toLowerCase(Locale.ROOT).replace('_', '-');
        }

        public static DisplayMode parse(String raw) {
            return DisplayMode.valueOf(raw.toUpperCase(Locale.ROOT).replace('-', '_'));
        }
    }

    private FieldVisualizationManager() {
    }

    public static void install() {
        if (installed) {
            return;
        }
        installed = true;
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (ENABLED.isEmpty()) {
                return;
            }
            int tick = (int) (server.getTickCount() & Integer.MAX_VALUE);
            for (UUID playerId : Set.copyOf(ENABLED)) {
                ServerPlayer player = server.getPlayerList().getPlayer(playerId);
                if (player == null || !player.isAlive()) {
                    ENABLED.remove(playerId);
                    LAST_SYNC_TICK.remove(playerId);
                    continue;
                }
                if (!(player.level() instanceof ServerLevel serverLevel)) {
                    continue;
                }
                renderForPlayer(serverLevel, player, tick);
            }
        });
    }

    public static void enable(UUID playerId) {
        if (playerId == null) {
            return;
        }
        ENABLED.add(playerId);
        if (!PLAYER_RADIUS.containsKey(playerId)) {
            PLAYER_RADIUS.put(playerId, Integer.valueOf(64));
        }
        if (!PLAYER_STYLE.containsKey(playerId)) {
            PLAYER_STYLE.put(playerId, ProjectionRenderStyle.defaults());
        }
    }

    public static void disable(UUID playerId) {
        if (playerId == null) {
            return;
        }
        ENABLED.remove(playerId);
        LAST_SYNC_TICK.remove(playerId);
        PLAYER_RADIUS.remove(playerId);
        PLAYER_STYLE.remove(playerId);
    }

    public static boolean isEnabled(UUID playerId) {
        return playerId != null && ENABLED.contains(playerId);
    }

    public static void setRadius(UUID playerId, int radius) {
        if (playerId == null) {
            return;
        }
        PLAYER_RADIUS.put(playerId, Math.max(8, Math.min(256, radius)));
    }

    public static int getRadius(UUID playerId) {
        Integer value = PLAYER_RADIUS.get(playerId);
        return value == null ? 64 : value;
    }

    public static void setMode(UUID playerId, DisplayMode mode) {
        if (playerId == null || mode == null) {
            return;
        }
        switch (mode) {
            case EDGES_ONLY -> PLAYER_STYLE.put(playerId, ProjectionRenderStyle.defaults().withFeature(ProjectionFeature.AXES, false));
            case FULL -> PLAYER_STYLE.put(playerId, ProjectionRenderStyle.full());
            case LABELS_ONLY -> PLAYER_STYLE.put(playerId, ProjectionRenderStyle.labelsOnly());
        }
    }

    public static DisplayMode getMode(UUID playerId) {
        ProjectionRenderStyle style = getStyle(playerId);
        if (style.showName() && !style.showLines() && !style.showPoints() && !style.showAxes() && !style.showFaces()) {
            return DisplayMode.LABELS_ONLY;
        }
        if (style.showLines() && style.showAxes() && style.showPoints()) {
            return DisplayMode.FULL;
        }
        return DisplayMode.EDGES_ONLY;
    }

    public static ProjectionRenderStyle getStyle(UUID playerId) {
        ProjectionRenderStyle style = PLAYER_STYLE.get(playerId);
        return style == null ? ProjectionRenderStyle.defaults() : style;
    }

    public static void setFeature(UUID playerId, ProjectionFeature feature, boolean enabled) {
        if (playerId == null || feature == null) {
            return;
        }
        ProjectionRenderStyle current = getStyle(playerId);
        PLAYER_STYLE.put(playerId, current.withFeature(feature, enabled));
    }

    private static void renderForPlayer(ServerLevel level, ServerPlayer player, int tick) {
        Integer last = LAST_SYNC_TICK.get(player.getUUID());
        int lastSyncTick = last == null ? Integer.MIN_VALUE / 2 : last;
        if (tick - lastSyncTick < SYNC_INTERVAL_TICKS) {
            return;
        }
        LAST_SYNC_TICK.put(player.getUUID(), tick);

        Vec3 viewer = player.position();
        int radius = getRadius(player.getUUID());
        ProjectionRenderStyle style = getStyle(player.getUUID());
        byte flags = style.toFlags();
        List<ProjectionNetworking.ProjectionEntry> visible = new ArrayList<>();

        for (FieldDefinition field : FieldManager.all().values()) {
            if (!field.dimensionId().equals(level.dimension().identifier())) {
                continue;
            }
            AABB box = field.bounds();
            if (distanceToAabb(viewer, box) > radius) {
                continue;
            }
            visible.add(new ProjectionNetworking.ProjectionEntry(field.id().toString(), box, flags));
        }

        for (ProjectionDefinition projection : ProjectionManager.all().values()) {
            if (!projection.dimensionId().equals(level.dimension().identifier())) {
                continue;
            }
            AABB box = projection.bounds();
            if (distanceToAabb(viewer, box) > radius) {
                continue;
            }
            // Projection name display is always bound to id.
            visible.add(new ProjectionNetworking.ProjectionEntry(projection.id().toString(), box, flags));
        }
        ProjectionNetworking.syncToPlayer(player, visible);
    }

    private static double distanceToAabb(Vec3 pos, AABB box) {
        double dx = Math.max(Math.max(box.minX - pos.x, 0.0), pos.x - box.maxX);
        double dy = Math.max(Math.max(box.minY - pos.y, 0.0), pos.y - box.maxY);
        double dz = Math.max(Math.max(box.minZ - pos.z, 0.0), pos.z - box.maxZ);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}

