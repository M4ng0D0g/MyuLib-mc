package com.myudog.myulib.api.permission;

import com.myudog.myulib.api.field.FieldDefinition;
import com.myudog.myulib.api.field.FieldManager;
import com.myudog.myulib.api.debug.DebugFeature;
import com.myudog.myulib.api.debug.DebugLogManager;
import com.myudog.myulib.api.rolegroup.RoleGroupManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public final class PermissionGate {
    private PermissionGate() {
    }

    public static boolean isDenied(Player player, PermissionAction action, Vec3 targetPosition) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }
        return isDenied(serverPlayer, action, targetPosition);
    }

    public static boolean isDenied(ServerPlayer player, PermissionAction action, Vec3 targetPosition) {
        return evaluateDecision(player, action, targetPosition) == PermissionDecision.DENY;
    }

    public static PermissionDecision evaluateDecision(ServerPlayer player, PermissionAction action, Vec3 targetPosition) {
        if (player == null || action == null) {
            return PermissionDecision.UNSET;
        }

        Vec3 resolvedPosition = targetPosition == null ? player.position() : targetPosition;
        Identifier dimId = player.level().dimension().identifier();
        Optional<FieldDefinition> field = FieldManager.findAt(dimId, resolvedPosition);
        var groups = RoleGroupManager.getSortedGroupIdsOf(player.getUUID());

        PermissionDecision decision = PermissionManager.evaluate(
                player.getUUID(),
                groups,
                action,
                field.map(FieldDefinition::id).orElse(null),
                dimId
        );

        DebugLogManager.log(DebugFeature.PERMISSION,
                "player=" + player.getName().getString()
                        + ",action=" + action
                        + ",decision=" + decision
                        + ",groups=" + String.join("|", groups)
                        + ",field=" + field.map(FieldDefinition::id).map(Identifier::toString).orElse("-")
                        + ",dim=" + dimId
                        + ",pos=(" + String.format("%.2f", resolvedPosition.x) + ","
                        + String.format("%.2f", resolvedPosition.y) + ","
                        + String.format("%.2f", resolvedPosition.z) + ")");

        return decision;
    }
}

