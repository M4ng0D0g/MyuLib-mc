# LogicConditions
## Role
This page is the canonical reference for `LogicConditions` in the `logic` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
LogicConditions API 參考

## 類別
`class LogicConditions`

## 公開方法
- `always()`
- `stateIs(state)` / `stateIn(vararg states)`
- `hasFeature(type)`
- `specialObjectExists(id)`
- `timerStatusIs(status)` / `timerModeIs(mode)`
- `timerRemainingEquals(ticks)` / `timerRemainingAtMost(ticks)` / `timerElapsedAtLeast(ticks)`
- `playerCountAtLeast(min)` / `playerCountExactly(count)`
- `gameTimeAtLeast(ticks)` / `gameTimeEquals(ticks)`
- `custom(predicate)`

## 用法
```java
LogicContracts.LogicCondition<MyState> condition = LogicConditions.timerRemainingAtMost(20);
```

