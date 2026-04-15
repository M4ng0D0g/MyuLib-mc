package com.myudog.myulib.api.control;

import com.myudog.myulib.api.control.network.ControlInputPayload;
import com.myudog.myulib.api.debug.DebugFeature;
import com.myudog.myulib.api.debug.DebugLogManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 實體操控權管理器 (Possession System)
 * 負責處理玩家對其他實體的 1 對 1 控制權綁定與輸入轉發。
 */
public final class ControlManager {

    // 雙向映射表：確保 1 對 1 關係
    // 玩家 UUID -> 被控制的實體 UUID
    private static final Map<UUID, UUID> PLAYER_TO_ENTITY = new ConcurrentHashMap<>();
    // 被控制的實體 UUID -> 玩家 UUID
    private static final Map<UUID, UUID> ENTITY_TO_PLAYER = new ConcurrentHashMap<>();
    // 實體 UUID -> 最新的按鍵指令
    private static final Map<UUID, ControlInputPayload> ENTITY_INPUTS = new ConcurrentHashMap<>();

    private ControlManager() {}

    public static void install() {

    }

    /**
     * 讓玩家綁定並控制一個實體
     * @return 綁定是否成功
     */
    public static boolean bind(ServerPlayer player, Entity target) {
        if (player == null || target == null) return false;

        UUID playerId = player.getUUID();
        UUID targetId = target.getUUID();

        // 🛡️ 規則 1：如果玩家已經在控制別的東西，先讓他解除綁定
        if (PLAYER_TO_ENTITY.containsKey(playerId)) {
            unbind(player);
        }

        // 🛡️ 規則 2：擠出機制 (Kick-out)
        // 如果這個生物已經被別人控制了，把那個人踢掉！
        if (ENTITY_TO_PLAYER.containsKey(targetId)) {
            UUID oldControllerId = ENTITY_TO_PLAYER.get(targetId);
            // 優先維持映射一致性；舊控制者的玩家實例若不可得，仍先移除其控制關係。
            PLAYER_TO_ENTITY.remove(oldControllerId);
            ENTITY_TO_PLAYER.remove(targetId);
        }

        // 🌟 建立雙向綁定
        PLAYER_TO_ENTITY.put(playerId, targetId);
        ENTITY_TO_PLAYER.put(targetId, playerId);

        // --- 狀態切換邏輯 ---

        // 1. 剝奪生物原有的 AI (如果是 Mob 的話)
        if (target instanceof Mob mob) {
            // 提示：setNoAi(true) 會讓生物連重力都失去，變成木頭。
            // 實務上我們通常會寫一個 Mixin，當生物在 ENTITY_TO_PLAYER 裡時，清空它的 GoalSelector，只接受網路封包的移動指令。
            // 這裡暫時用自訂 Tag 或屬性標記
            mob.addTag("myulib_controlled");
        }

        // 2. 玩家肉體進入「觀察者/發呆」狀態
        // 建議：給予玩家極高強度的緩慢、跳躍提升(無法跳躍)、以及無敵狀態，並透過 Mixin 鎖死 WASD
        player.addTag("myulib_controlling");
        player.setInvulnerable(true);

        // 3. 觸發 Camera 系統 (將視角綁定過去)
        // CameraApi.moveTo(player, CameraTrackingTarget.of(target), ...);

        DebugLogManager.log(DebugFeature.CONTROL,
                "bind player=" + player.getName().getString() + "(" + playerId + ") -> entity=" + target.getType().toString() + "(" + targetId + ")");
        return true;
    }

    /**
     * 解除玩家的控制狀態
     */
    public static void unbind(ServerPlayer player) {
        if (player == null) return;
        UUID playerId = player.getUUID();

        UUID targetId = PLAYER_TO_ENTITY.remove(playerId);
        if (targetId != null) {
            ENTITY_TO_PLAYER.remove(targetId);
        }

        // 恢復玩家肉體狀態
        player.removeTag("myulib_controlling");
        player.setInvulnerable(false);
        DebugLogManager.log(DebugFeature.CONTROL,
                "unbind player=" + player.getName().getString() + "(" + playerId + ") from entity=" + targetId);

        // 視角歸位
        // CameraApi.reset(player);
    }

    // --- 查詢工具 ---

    public static boolean isControlling(ServerPlayer player) {
        return PLAYER_TO_ENTITY.containsKey(player.getUUID());
    }

    public static UUID getControlledEntity(ServerPlayer player) {
        return PLAYER_TO_ENTITY.get(player.getUUID());
    }

    public static boolean isControlledByPlayer(Entity entity) {
        return ENTITY_TO_PLAYER.containsKey(entity.getUUID());
    }

    /**
     * 伺服器收到封包時呼叫：更新目標實體的輸入狀態
     */
    public static void updateInput(ServerPlayer player, ControlInputPayload input) {
        UUID targetId = PLAYER_TO_ENTITY.get(player.getUUID());
        if (targetId != null) {
            ENTITY_INPUTS.put(targetId, input);
            DebugLogManager.log(DebugFeature.CONTROL,
                    "input player=" + player.getName().getString() + " -> entity=" + targetId
                            + " [u=" + input.up() + ",d=" + input.down() + ",l=" + input.left() + ",r=" + input.right()
                            + ",j=" + input.jumping() + ",s=" + input.sneaking() + ",yaw=" + input.yaw() + ",pitch=" + input.pitch() + "]");
        }
    }

    /**
     * 供實體每一幀讀取自己的輸入
     */
    public static ControlInputPayload getInput(Entity entity) {
        return ENTITY_INPUTS.get(entity.getUUID());
    }

    public static int controlledCount() {
        return PLAYER_TO_ENTITY.size();
    }

    public static int bufferedInputCount() {
        return ENTITY_INPUTS.size();
    }
}