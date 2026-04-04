# Timer 系統核心 API 參考

本頁集中說明 `TimerModels` 內的核心型別。

## TimerModels.TimerMode
- `COUNT_UP`
- `COUNT_DOWN`

## TimerModels.TimerStatus
- `IDLE`, `RUNNING`, `PAUSED`, `STOPPED`, `COMPLETED`

## TimerModels.TimerTickBasis
- `ELAPSED`：以已經過 tick 為基準
- `REMAINING`：以剩餘 tick 為基準

## TimerModels.TimerPayload
- payload marker interface
- 內建實作：`TimerModels.RespawnTimerPayload`, `TimerModels.SoundTimerPayload`

## TimerModels.TimerAction
- `invoke(snapshot)`：在命中的 tick 執行

## TimerModels.TimerBinding
- 欄位：`id`, `tick`, `basis`, `action`

## TimerModels.TimerSnapshot
- 欄位：`timerEntityId`, `ownerEntityId`, `timer`, `status`, `elapsedTicks`, `remainingTicks`, `payload`, `currentTick`
- 方法：`durationTicks()`, `mode()`, `progress()`, `payloadAs()`, `requirePayload()`

## TimerModels.Timer
- 欄位：`id`, `durationTicks`, `mode`, `autoStopOnComplete`, `elapsedBindings`, `remainingBindings`, `startedActions`, `pausedActions`, `resumedActions`, `resetActions`, `stoppedActions`, `completedActions`
- 方法：`onElapsedTick`, `onRemainingTick`, `onStarted`, `onPaused`, `onResumed`, `onReset`, `onStopped`, `onCompleted`, `removeBinding`

## TimerModels.TimerInstance
- 欄位：`timerEntityId`, `timerId`, `ownerEntityId`, `payload`, `status`, `elapsedTicks`, `lastUpdatedTick`, `pausedTicks`
- 方法：`isRunning()`, `isPaused()`, `isStopped()`, `isCompleted()`

## 用法
```java
TimerModels.Timer timer = new TimerModels.Timer(Identifier.of("myulib", "respawn"), 100);
TimerModels.TimerInstance instance = new TimerModels.TimerInstance(1, Identifier.of("myulib", "respawn"), 12L, null);
```

