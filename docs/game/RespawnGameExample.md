# RespawnGameExample
## Role
This page is the canonical reference for `RespawnGameExample` in the `game` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
RespawnGameExample API 參考

## 類別
`class RespawnGameExample extends GameDefinition<RespawnGameExample.RespawnGameState>`

## 內嵌 enum
`RespawnGameState`
- `WAITING`
- `COUNTDOWN`
- `ACTIVE`
- `FINISHED`

## 主要行為
- `getInitialState()`：回傳 `WAITING`
- `getAllowedTransitions()`：定義 WAITING / COUNTDOWN / ACTIVE / FINISHED 的轉移表
- `getRequiredSpecialObjectIds()`：要求 `myulib:respawn_anchor`
- `createFeatures(config)`：建立 `GameScoreboardFeature` 與 `GameTimerFeature`
- `onCreate(instance)`：初始化 scoreboard
- `onEnterState(instance, context)`：更新目前 state 顯示
- `onTick(instance)`：更新 tick 數值
- `onDestroy(instance)`：清空 scoreboard 與 timers

## 用法
```java
GameManager.register(new RespawnGameExample());
```

