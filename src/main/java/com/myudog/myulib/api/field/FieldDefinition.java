package com.myudog.myulib.api.field;

import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 定義一個獨立的遊戲/保護區域。
 * 權限相關設定交由外部 Permission 系統處理，可將資料存放於 fieldData 中。
 */
public record FieldDefinition(
        @NotNull Identifier id,
        @NotNull Identifier dimensionId, // 例如: minecraft:overworld
        @NotNull AABB bounds,            // 核心：原版碰撞箱
        Map<String, Object> fieldData // 未來放置權限表 (RoleGroup, Enum 狀態) 的擴充槽
) {
    public FieldDefinition {
        fieldData = fieldData == null ? new HashMap<>() : new HashMap<>(fieldData);
    }
}