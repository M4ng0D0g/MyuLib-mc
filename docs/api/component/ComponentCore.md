# Component 系統核心 API 參考

本頁集中說明 `ComponentModels` 內的 component 型別。

## ComponentModels.ComponentBindingTarget
- `Entity(entityId)`
- `Block(pos, worldId)`
- `Game(gameInstanceId)`

## ComponentModels.ComponentSignal
- marker interface

## 內建信號
- `ComponentEntitySpawnSignal(entityId, worldId, target)`
- `ComponentEntityTickSignal(entityId, worldId)`
- `ComponentBlockBreakSignal(pos, worldId, playerEntityId)`
- `ComponentBlockUseSignal(pos, worldId, playerEntityId)`
- `ComponentCustomSignal(name, payload)`

## ComponentModels.ComponentContext
- 欄位：`binding`, `signal`, `gameInstance`, `region`, `metadata`

## ComponentModels.ComponentCondition / ComponentAction
- `test(context)` / `execute(context)`

## ComponentModels.ComponentRule
- 欄位：`id`, `signalType`, `conditions`, `actions`, `priority`
- `matches(signal)`

## ComponentModels.ComponentBindingDefinition
- 欄位：`id`, `ownerId`, `target`, `gameInstanceId`, `signalTypes`, `conditions`, `actions`, `metadata`

## 用法
```java
ComponentModels.ComponentBindingDefinition binding = new ComponentModels.ComponentBindingDefinition(
    Identifier.of("myulib", "arena_guard"),
    Identifier.of("myulib", "arena_game"),
    new ComponentModels.ComponentBindingTarget.Block(new Object(), null),
    null,
    java.util.Set.of(),
    java.util.List.of(),
    java.util.List.of(),
    java.util.Map.of()
);
```

