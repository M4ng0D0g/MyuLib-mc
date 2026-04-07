# TimerManager
## Role
This page is the canonical reference for `TimerManager` in the `timer` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
TimerManager API 參考

## 類別
`class TimerManager`

## 公開方法
- `install()`
- `register(timer)` / `unregister(timerId)` / `has(timerId)`
- `createInstance(timerId, ownerEntityId, payload, autoStart, world)`
- `getInstance(timerEntityId, world)` / `getSnapshot(timerEntityId)` / `findInstances(ownerEntityId, world)`
- `isRunning/isPaused/isStopped/isCompleted(timerEntityId, world)`
- `start/pause/resume/stop/reset(timerEntityId, world)`
- `setElapsedTicks/setRemainingTicks(timerEntityId, world)`
- `setPayload(timerEntityId, payload, world)`
- `update(world)`

## 用法
```java
TimerManager.register(new TimerModels.Timer(Identifier.of("myulib", "respawn"), 100));
int timerEntityId = TimerManager.createInstance(Identifier.of("myulib", "respawn"), 12L, null, true, null);
```

