package com.myudog.myulib.api.ecs;

import com.myudog.myulib.api.ecs.event.ComponentAddedEvent;
import com.myudog.myulib.api.ecs.lifecycle.*;
import com.myudog.myulib.api.ecs.storage.ComponentSerializer;
import com.myudog.myulib.api.storage.DataStorage;
import com.myudog.myulib.api.util.NbtIoHelper;
import com.myudog.myulib.internal.ecs.ComponentStorage;
import com.myudog.myulib.internal.event.EventDispatcherImpl;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EcsWorld {
    public final EventDispatcherImpl eventBus = new EventDispatcherImpl();

    private int nextEntityId = 0;
    private final Map<Class<? extends Component>, ComponentStorage<? extends Component>> storages = new HashMap<>();

    // 🌟 儲存庫注入與序列化註冊表
    private static DataStorage<Integer, CompoundTag> storage;
    private static final Map<Class<? extends Component>, ComponentSerializer<?>> SERIALIZERS = new ConcurrentHashMap<>();

    /**
     * 🌟 注入儲存實作並自動掛載伺服器生命週期。
     */
    public void install(DataStorage<Integer, CompoundTag> storageProvider) {
        storage = storageProvider;

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            if (storage != null) {
                storage.initialize(server);
                // 載入持久化實體數據
                Map<Integer, CompoundTag> loadedData = storage.loadAll();
                if (loadedData != null) {
                    loadedData.forEach(this::restoreEntity);
                    // 確保 ID 不會與載入的實體衝突
                    this.nextEntityId = loadedData.keySet().stream().max(Integer::compare).orElse(-1) + 1;
                }
                System.out.println("[Myulib-ECS] 已成功恢復 " + (loadedData != null ? loadedData.size() : 0) + " 個持久化實體。");
            }
        });

        // 伺服器關閉時自動存檔
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> savePersistentEntities());
    }

    /**
     * 註冊組件的序列化方式。
     */
    public static <T extends Component> void registerSerializer(Class<T> type, ComponentSerializer<T> serializer) {
        SERIALIZERS.put(type, serializer);
    }

    public int createEntity() {
        return nextEntityId++;
    }

    public void destroyEntity(int entityId) {
        for (ComponentStorage<? extends Component> s : storages.values()) {
            s.remove(entityId);
        }
        if (storage != null) {
            storage.delete(entityId); // 同步刪除持久化資料
        }
    }

    /**
     * 🌟 持久化存檔邏輯。
     * 遍歷所有實體，僅將註冊了序列化器的組件存入 NBT 快照。
     */
    public void savePersistentEntities() {
        if (storage == null) return;

        Set<Integer> allEntities = getAllActiveEntities();
        for (int entityId : allEntities) {
            CompoundTag snapshot = serializeEntity(entityId);
            if (snapshot != null && !snapshot.isEmpty()) {
                storage.save(entityId, snapshot);
            }
        }
    }

    private CompoundTag serializeEntity(int entityId) {
        CompoundTag entityTag = new CompoundTag();
        boolean hasData = false;

        for (Map.Entry<Class<? extends Component>, ComponentStorage<? extends Component>> entry : storages.entrySet()) {
            Component component = entry.getValue().get(entityId);
            if (component == null) continue;

            @SuppressWarnings("unchecked")
            ComponentSerializer<Component> serializer = (ComponentSerializer<Component>) SERIALIZERS.get(entry.getKey());
            if (serializer != null) {
                entityTag.put(entry.getKey().getName(), serializer.serialize(component));
                hasData = true;
            }
        }
        return hasData ? entityTag : null;
    }

    @SuppressWarnings("unchecked")
    private void restoreEntity(int entityId, CompoundTag tag) {
        for (String className : NbtIoHelper.keysOf(tag)) {
            try {
                Class<? extends Component> type = (Class<? extends Component>) Class.forName(className);
                ComponentSerializer<?> serializer = SERIALIZERS.get(type);
                if (serializer != null) {
                    Tag componentData = tag.get(className);
                    Component component = (Component) ((ComponentSerializer<Object>) serializer).deserialize(componentData);
                    addComponent(entityId, (Class) type, component);
                }
            } catch (Exception e) {
                System.err.println("[Myulib-ECS] 還原實體 " + entityId + " 的組件失敗: " + className);
            }
        }
    }

    private Set<Integer> getAllActiveEntities() {
        Set<Integer> entities = new HashSet<>();
        for (ComponentStorage<? extends Component> s : storages.values()) {
            int[] dense = s.getRawDense();
            for (int i = 0; i < s.size(); i++) entities.add(dense[i]);
        }
        return entities;
    }

    // --- 保留原本功能 ---

    @SuppressWarnings("unchecked")
    public <T extends Component> ComponentStorage<T> getStorage(Class<T> type) {
        return (ComponentStorage<T>) storages.computeIfAbsent(type, key -> new ComponentStorage<>());
    }

    public <T extends Component> void addComponent(int entityId, Class<T> type, T component) {
        getStorage(type).add(entityId, component);
        eventBus.dispatch(new ComponentAddedEvent(entityId, component));
    }

    public <T extends Component> T getComponent(int entityId, Class<T> type) {
        ComponentStorage<T> storage = (ComponentStorage<T>) storages.get(type);
        return storage == null ? null : storage.get(entityId);
    }
}