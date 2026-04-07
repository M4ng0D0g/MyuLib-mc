# ComponentCore
## Role
This page is the canonical reference for `ComponentCore` in the `component` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
Component 系統核心 API 參考

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

