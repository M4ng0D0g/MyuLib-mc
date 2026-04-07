# LogicActions
## Role
This page is the canonical reference for `LogicActions` in the `logic` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
LogicActions API 參考

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

