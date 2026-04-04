# TimerPayloads API 參考

## 型別
- `TimerModels.TimerPayload`：payload marker interface
- `TimerModels.RespawnTimerPayload`：玩家復活倒數示例資料
- `TimerModels.SoundTimerPayload`：音效提醒示例資料

## 方法
- `TimerModels.TimerSnapshot.payloadAs()`：安全轉型 payload
- `TimerModels.TimerSnapshot.requirePayload()`：強制取得 payload

## 用法
```java
TimerModels.TimerPayload payload = new TimerModels.RespawnTimerPayload(java.util.UUID.randomUUID(), false);
TimerModels.RespawnTimerPayload typed = snapshot.payloadAs();
```

