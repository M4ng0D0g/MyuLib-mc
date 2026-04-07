# TimerPayloads
## Role
This page is the canonical reference for `TimerPayloads` in the `timer` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
TimerPayloads API 參考

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

