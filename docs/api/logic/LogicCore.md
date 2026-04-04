# Logic 系統核心 API 參考

本頁集中說明 `LogicContracts` 與 `LogicEngine` 的核心 Java API。

## LogicContracts
`LogicContracts` 是容器類，實際型別包含：
- `LogicSignal`：marker interface
- `LogicContext<S>`：`instance`, `signal`, `facts`, `previousState`, `currentState`, `timerSnapshot`
- `LogicCondition<S>`：`test(context)`
- `LogicAction<S>`：`execute(context)`
- `LogicRule<S>`：`id`, `signalType`, `conditions`, `actions`, `priority`, `matches(signal)`
- `LogicRuleSet<S>`：`rules`, `install(engine)`
- `LogicEventBus<S>`：`subscribe(...)`, `unsubscribe(...)`, `dispatch(signal)`

## LogicEngine<S>
- `bind(instance)`
- `setFactsResolver(resolver)`
- `register(rule)` / `registerAll(rules)`
- `clearRules()`
- `publish(signal)`
- `publishStateChanged(...)`
- `publishTimerStarted/Paused/Resumed/Reset/Stopped/Tick/Checkpoint/Completed(...)`
- `publishGameCreated(...)`
- `publishGameDestroyed(...)`

## 用法
```java
LogicEngine<RespawnGameExample.RespawnGameState> engine = new LogicEngine<>();
engine.register(new LogicContracts.LogicRule<>(
    "countdown-end",
    LogicSignals.TimerCompletedSignal.class,
    java.util.List.of(LogicConditions.always()),
    java.util.List.of(LogicActions.transitionTo(RespawnGameExample.RespawnGameState.ACTIVE)),
    0
));
```

