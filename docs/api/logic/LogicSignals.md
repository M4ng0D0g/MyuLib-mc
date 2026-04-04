# LogicSignals API 參考

## 內容
`LogicSignals` 內含下列 record 型別：
- `GameInstanceCreatedSignal<S>`
- `GameInstanceDestroyedSignal<S>`
- `GameStateChangedSignal<S>`
- `TimerStartedSignal`
- `TimerPausedSignal`
- `TimerResumedSignal`
- `TimerResetSignal`
- `TimerStoppedSignal`
- `TimerTickSignal`
- `TimerCheckpointSignal`
- `TimerCompletedSignal`

## 說明
這些都實作 `LogicContracts.LogicSignal`，由 `GameManager`、`GameInstance` 或 `TimerManager` 發送，供 `LogicEngine` 規則引擎使用。

## 用法
```java
LogicContracts.LogicSignal signal = new LogicSignals.TimerCompletedSignal(snapshot);
engine.publish(signal);
```

