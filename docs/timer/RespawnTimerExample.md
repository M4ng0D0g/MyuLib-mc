# RespawnTimerExample
## Role
This page is the canonical reference for `RespawnTimerExample` in the `timer` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
RespawnTimerExample API 參考

## 類別
`class RespawnTimerExample`

## 公開方法
- `timer()`：回傳一個預設的 `TimerModels.Timer`
- `payload(playerId, allowSkip)`：建立 `TimerModels.RespawnTimerPayload`

## 用法
```java
TimerModels.Timer timer = RespawnTimerExample.timer();
TimerModels.RespawnTimerPayload payload = RespawnTimerExample.payload(UUID.randomUUID(), false);
```
