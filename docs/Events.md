# 事件系統使用說明（完整參考）
本文檔說明專案內的 Java 事件匯流排核心：`Event`、`FailableEvent`、`ProcessResult`、`EventPriority`、`EventListener`、`EventDispatcherImpl`、`ServerEventBus`，以及 `EcsWorld.eventBus` 的使用方式。
## 概覽
本專案採用同步事件匯流排。事件會依照註冊優先權由高到低觸發；只要某個 listener 回傳非 `PASS` 的 `ProcessResult`，分發就會立即停止。
## 主要類別
### `Event`
所有事件的基礎 marker 介面。
### `FailableEvent`
可失敗事件介面。提供：
- `getErrorMessage()`
- `setErrorMessage(String)`
### `ProcessResult`
事件處理結果：
- `PASS`：繼續往下傳遞
- `CONSUME`：攔截並停止分發
- `SUCCESS`：成功處理並停止分發
- `FAILED`：處理失敗並停止分發
### `EventPriority`
預設優先權常數：
- `HIGHEST = 0`
- `HIGH = 1000`
- `NORMAL = 2000`
- `LOW = 3000`
- `LOWEST = 4000`
### `EventListener<T extends Event>`
Java functional interface：
```java
ProcessResult handle(T event);
```
### `EventDispatcherImpl`
內部 dispatcher，提供：
- `subscribe(Class<T>, EventListener<T>)`
- `subscribe(Class<T>, int, EventListener<T>)`
- `unsubscribe(Class<T>, EventListener<T>)`
- `dispatch(Event)`
### `ServerEventBus`
共用的 server/common bus helper。可直接使用 static 方法：
- `ServerEventBus.subscribe(...)`
- `ServerEventBus.unsubscribe(...)`
- `ServerEventBus.dispatch(...)`
## 使用範例
### 註冊 listener
```java
EventDispatcherImpl dispatcher = new EventDispatcherImpl();
dispatcher.subscribe(ServerTickEvent.class, EventPriority.HIGH, event -> {
    System.out.println("tick: " + event.getServer());
    return ProcessResult.PASS;
});
```
### 發佈事件
```java
dispatcher.dispatch(new ServerTickEvent(server));
```
### 取消事件或拦截原邏輯
```java
dispatcher.subscribe(EntitySpawnEvent.class, event -> {
    if (event.getEntity() == null) {
        event.setErrorMessage("entity missing");
        return ProcessResult.FAILED;
    }
    return ProcessResult.PASS;
});
```
## 常見事件
- `EntitySpawnEvent`：實體準備生成時觸發，可攔截並附帶錯誤訊息。
- `ServerTickEvent`：伺服器 tick 事件。
- `ComponentAddedEvent`：`EcsWorld.addComponent(...)` 後自動派發。
- `TimerStartedEvent` / `TimerPausedEvent` / `TimerResumedEvent` / `TimerResetEvent` / `TimerStoppedEvent` / `TimerTickEvent` / `TimerCheckpointEvent` / `TimerCompletedEvent`：請參考 `docs/systems/TimerSystem.md`。
## `EcsWorld.eventBus`
每個 `EcsWorld` 都有自己的 `eventBus`：
```java
EcsWorld world = new EcsWorld();
world.eventBus.subscribe(ComponentAddedEvent.class, event -> {
    System.out.println("added entity " + event.getEntityId());
    return ProcessResult.PASS;
});
```
## 注意事項
- 目前 dispatcher 是同步執行。
- 預設是依 priority 由小到大執行。
- `dispatch` 以事件實際 class 作為 key；若要擴充成繼承鏈派發，可在 dispatcher 之後再包一層。