# TimerEvents
## Role
This page is the canonical reference for `TimerEvents` in the `timer` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
TimerEvents API 參考

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

