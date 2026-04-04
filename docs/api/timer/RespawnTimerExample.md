# RespawnTimerExample API 參考

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
