# LogicFactsResolver API 參考

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

