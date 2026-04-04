# ECS 系統使用說明（完整參考）
本文檔提供專案內 ECS（Entity-Component-System）核心的 Java 版參考：`EcsWorld`、`Component`、`ComponentStorage`、`ComponentAddedEvent`，以及生命週期輔助型別 `Resettable`、`DimensionAware`、`DimensionChangePolicy`、`ComponentLifecycle`。
## 概要
ECS 以資料與行為分離為原則。在本專案中，`EcsWorld` 負責：
- 建立 entity
- 存取 component storage
- 派發 `ComponentAddedEvent`
- 處理 reset / dimension change 生命週期
## 主要類別
### `Component`
Marker interface，所有 ECS component 都應實作它。
### `ComponentStorage<T extends Component>`
內部稠密集合結構，提供：
- `add(int, T)`
- `get(int)`
- `remove(int)`
- `has(int)`
- `clear()`
- `size()`
- `getRawDense()`
### `EcsWorld`
公開 API：
- `int createEntity()`
- `void addComponent(int entityId, T component)`
- `void addComponent(int entityId, Class<T> type, T component)`
- `T getComponent(int entityId, Class<T> type)`
- `boolean hasComponent(int entityId, Class<T> type)`
- `List<Integer> query(Class<? extends Component> type)`
- `void destroyEntity(int entityId)`
- `void resetEntity(int entityId)`
- `void processDimensionChange(int entityId)`
並且公開一個 `eventBus` 欄位，型別為 `EventDispatcherImpl`。
### `ComponentAddedEvent`
當 `addComponent(...)` 成功後自動發佈。
### `Resettable`
實作 `reset()`，可在 `resetEntity(...)` 或某些維度策略下被呼叫。
### `DimensionAware`
實作 `getDimensionPolicy()`，決定維度變更時的處理方式。
### `DimensionChangePolicy`
- `KEEP`
- `REMOVE`
- `RESET`
### `ComponentLifecycle`
提供：
- `isResettable(Component)`
- `getDimensionPolicy(Component)`
## 使用範例
### 建立 entity 並掛載 component
```java
EcsWorld world = new EcsWorld();
int entity = world.createEntity();
world.addComponent(entity, new TransformComponent());
world.addComponent(entity, new WidgetStateComponent());
```
### 取得 component
```java
TransformComponent transform = world.getComponent(entity, TransformComponent.class);
if (transform != null) {
    transform.setX(10f);
}
```
### 查詢所有擁有某 component 的 entity
```java
List<Integer> entities = world.query(TransformComponent.class);
```
### 監聽 component 新增
```java
world.eventBus.subscribe(ComponentAddedEvent.class, event -> {
    System.out.println("component added: " + event.getEntityId());
    return com.myudog.myulib.api.event.ProcessResult.PASS;
});
```
## 生命週期
- `resetEntity(entityId)`：對所有實作 `Resettable` 的 component 呼叫 `reset()`。
- `processDimensionChange(entityId)`：根據 `DimensionAware` 的 policy 執行 `KEEP` / `REMOVE` / `RESET`。
## 最佳實務
- component 只放資料。
- system 負責邏輯。
- 熱路徑避免頻繁配置新物件。
- 大量短生命物件請考慮物件池。