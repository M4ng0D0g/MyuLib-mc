# LogicConditions API 參考

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

