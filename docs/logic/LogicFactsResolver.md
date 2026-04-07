# LogicFactsResolver
## Role
This page is the canonical reference for `LogicFactsResolver` in the `logic` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
LogicFactsResolver API 參考

## 介面
`com.myudog.myulib.api.game.logic.facts.LogicFactsResolver`

## 方法
- `playerCount(instance)`
- `playerCountInTeam(instance, teamId)`
- `isOnTeam(instance, playerId, teamId)`
- `isRedTeam(instance, playerId)`
- `gameTimeTicks(instance)`
- `hasSpecialObject(instance, objectId)`

## 預設值
- `LogicFactsResolver.DEFAULT`

## 補充
- `gameTimeTicks(instance)` 會回傳 `instance.getTickCount()`
- `hasSpecialObject(instance, objectId)` 會直接使用 `GameInstance.hasSpecialObject(...)`

## 用法
```java
LogicFactsResolver resolver = LogicFactsResolver.DEFAULT;
int count = resolver.playerCount(instance);
```

