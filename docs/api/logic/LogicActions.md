# LogicActions API 參考

## 類別
`class LogicActions`

## 公開方法
- `run(block)`：包裝自訂行為
- `transitionTo(state)`：切換遊戲狀態
- `resetState()`：重置 state
- `publish(signal)`：再送一個 logic signal
- `startCurrentTimer()` / `pauseCurrentTimer()` / `resumeCurrentTimer()` / `stopCurrentTimer()` / `resetCurrentTimer(clearPayload)`
- `setScoreboardLine(index, value)` / `setScoreboardValue(key, value)`
- `attachGameObject(id, runtimeObject)`

## 用法
```java
LogicContracts.LogicAction<MyState> action = LogicActions.transitionTo(MyState.ACTIVE);
```

