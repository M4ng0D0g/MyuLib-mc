package com.myudog.myulib.api.game.core;

import com.myudog.myulib.Myulib;
import com.myudog.myulib.api.debug.DebugFeature;
import com.myudog.myulib.api.debug.DebugLogManager;
import com.myudog.myulib.api.game.state.GameState;
import com.myudog.myulib.api.util.ShortIdRegistry;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class GameManager {
    private static final Map<Identifier, GameDefinition<?, ?, ?>> DEFINITIONS = new LinkedHashMap<>();
    private static final Map<Integer, GameInstance<?, ?, ?>> INSTANCES = new ConcurrentHashMap<>();
    private static final AtomicInteger NEXT_INSTANCE_ID = new AtomicInteger(1);

    // 🌟 GameManager 專屬的短 ID 註冊表 (長度 6)
    private static final ShortIdRegistry ID_REGISTRY = new ShortIdRegistry(6);

    private GameManager() {}

    public static void install() {}

    // --- Definition 管理 ---

    public static void register(GameDefinition<?, ?, ?> definition) {
        Objects.requireNonNull(definition, "GameDefinition 不能為空");
        DEFINITIONS.put(definition.getId(), definition);
        DebugLogManager.log(DebugFeature.GAME, "register definition id=" + definition.getId());
    }

    public static GameDefinition<?, ?, ?> unregister(Identifier gameId) {
        DebugLogManager.log(DebugFeature.GAME, "unregister definition id=" + gameId);
        return DEFINITIONS.remove(gameId);
    }

    public static boolean hasDefinition(Identifier gameId) {
        return DEFINITIONS.containsKey(gameId);
    }

    @SuppressWarnings("unchecked")
    public static <C extends GameConfig, D extends GameData, S extends GameState> GameDefinition<C, D, S> definition(Identifier gameId) {
        return (GameDefinition<C, D, S>) DEFINITIONS.get(gameId);
    }

    // --- Instance 管理 ---

    @SuppressWarnings("unchecked")
    public static <C extends GameConfig, D extends GameData, S extends GameState> GameInstance<C, D, S> createInstance(Identifier gameId, C config) {
        GameDefinition<C, D, S> definition = definition(gameId);
        if (definition == null) {
            throw new IllegalArgumentException("找不到該遊戲藍圖 (Unknown game definition): " + gameId);
        }

        Objects.requireNonNull(config, "建立遊戲必須提供 GameConfig 實例");
        config.validate();

        int instanceId = NEXT_INSTANCE_ID.getAndIncrement();
        GameInstance<C, D, S> instance = definition.createInstance(instanceId, config);

        // 🌟 核心：由 GameManager 分配 Identifier 與 ShortId 給資料層
        D data = instance.getData();
        if (data.getId() == null) {
            // 使用 UUID 確保即使伺服器重啟也不會衝突
            Identifier fullId = Identifier.fromNamespaceAndPath(Myulib.MOD_ID, "game_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10));
            String shortId = ID_REGISTRY.generateAndBind(fullId);
            data.setupId(fullId, shortId);
        } else if (data.getShortId() != null) {
            // 若未來實作從 Storage 讀檔，需將舊有的 ID 重新綁定進註冊表
            ID_REGISTRY.bind(data.getShortId(), data.getId());
        }

        INSTANCES.put(instanceId, instance);
        DebugLogManager.log(DebugFeature.GAME,
                "create instance id=" + instanceId + ",definition=" + gameId + ",dataId=" + instance.getData().getId() + ",shortId=" + instance.getData().getShortId());
        return instance;
    }

    public static GameInstance<?, ?, ?> getInstance(int instanceId) {
        return INSTANCES.get(instanceId);
    }

    public static List<GameInstance<?, ?, ?>> getInstances() {
        return List.copyOf(INSTANCES.values());
    }

    public static List<GameInstance<?, ?, ?>> getInstances(Identifier gameId) {
        return INSTANCES.values().stream()
                .filter(instance -> instance.getDefinition().getId().equals(gameId))
                .toList();
    }

    public static boolean destroyInstance(int instanceId) {
        GameInstance<?, ?, ?> instance = INSTANCES.remove(instanceId);
        if (instance != null) {
            // 🌟 釋放短 ID
            if (instance.getData() != null && instance.getData().getId() != null) {
                ID_REGISTRY.unbind(instance.getData().getId());
            }
            instance.destroy();
            DebugLogManager.log(DebugFeature.GAME, "destroy instance id=" + instanceId);
            return true;
        }
        DebugLogManager.log(DebugFeature.GAME, "destroy miss instance id=" + instanceId);
        return false;
    }

    // --- 🌟 開放給指令使用的短 ID 查詢 API ---

    public static Identifier resolveShortId(String shortId) {
        return ID_REGISTRY.getFullId(shortId);
    }

    public static String getShortIdOf(Identifier fullId) {
        return ID_REGISTRY.getShortId(fullId);
    }

    // --- 生命週期 ---

    public static void tickAll() {
        for (Map.Entry<Integer, GameInstance<?, ?, ?>> entry : INSTANCES.entrySet()) {
            GameInstance<?, ?, ?> instance = entry.getValue();
            if (!instance.isEnabled()) {
                // 如果房間自行銷毀，順便拔除並釋放 ID
                if (instance.getData() != null && instance.getData().getId() != null) {
                    ID_REGISTRY.unbind(instance.getData().getId());
                }
                INSTANCES.remove(entry.getKey());
                DebugLogManager.log(DebugFeature.GAME, "auto-prune disabled instance id=" + entry.getKey());
                continue;
            }
            instance.tick();
        }
    }
}