# TimerSystem
## Role
This page is the canonical reference for `TimerSystem` in the `ui` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
Timer 系統使用說明（入口索引）

`timer/` 是遊戲時間能力層，負責正計時、倒數計時、tick 事件、暫停、重設、完成判定，以及 payload 傳遞。

## 系統入口
- [TimerCore](../api/timer/TimerCore.md)
- [TimerManager](../api/timer/TimerManager.md)
- [TimerEvents](../api/timer/TimerEvents.md)
- [TimerPayloads](../api/timer/TimerPayloads.md)
- [RespawnTimerExample](../api/timer/RespawnTimerExample.md)

## 公開型別
- `TimerModels.TimerMode` / `TimerModels.TimerStatus` / `TimerModels.TimerTickBasis`
- `TimerModels.TimerAction` / `TimerModels.TimerBinding` / `TimerModels.TimerSnapshot`
- `TimerModels.Timer` / `TimerModels.TimerInstance`
- `TimerManager`
- `TimerEvents.Timer*Event`
- `TimerModels.TimerPayload` / `TimerModels.RespawnTimerPayload` / `TimerModels.SoundTimerPayload`
- `RespawnTimerExample`

## 本系統適合做什麼
- 倒數復活
- 技能冷卻
- 音效提醒
- 階段節奏控制

## 文件導覽
- 詳細 API：`docs/api/timer/*.md`
