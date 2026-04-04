# TimerEvents API 參考

## 事件型別
`TimerEvents` 內含下列 record：
- `TimerStartedEvent(snapshot)`
- `TimerPausedEvent(snapshot)`
- `TimerResumedEvent(snapshot)`
- `TimerResetEvent(snapshot)`
- `TimerStoppedEvent(snapshot)`
- `TimerTickEvent(snapshot)`
- `TimerCheckpointEvent(snapshot, bindingId, basis, tick)`
- `TimerCompletedEvent(snapshot)`

## 說明
- 這些是 `TimerModels.TimerSnapshot` 的輕量 wrapper
- logic 層的事件橋接則使用 `LogicSignals.Timer*Signal`

## 用法
```java
TimerModels.TimerSnapshot snapshot = TimerManager.getSnapshot(timerId);
TimerEvents.TimerCompletedEvent event = new TimerEvents.TimerCompletedEvent(snapshot);
```

