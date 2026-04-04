# TimerManager API 參考

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

