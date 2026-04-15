# Myulib 遊戲框架架構設計文檔 (v0.2.5)

## 1. 架構概述
Myulib 遊戲框架採用「狀態驅動」與「資料解耦」的核心設計，旨在解決大規模 RPG 或戰棋遊戲開發中邏輯混亂與 ID 碰撞問題。框架將職責劃分為四個核心維度：藍圖 (Definition)、上下文 (Instance)、邏輯大腦 (State) 與記憶體 (Data)。

---

## 2. 核心組件職責定義

### 2.1 GameDefinition (靜態藍圖與工廠)
- **職責**：定義遊戲的靜態屬性，如遊戲 ID、預設配置類別。
- **角色**：作為工廠模式的入口，負責初始化 `GameInstance` 並註冊初始的狀態映射關係。
- **建議方向**：不再承擔任何執行期邏輯，僅作為「狀態分配者」。

### 2.2 GameInstance (執行上下文)
- **職責**：作為一個遊戲房間的容器。
- **持有對象**：`GameData`、`EventBus` 以及當前的 `GameState`。
- **角色**：負責跨狀態的資源調度與生命週期管理。

### 2.3 GameState (邏輯大腦) - *重構核心*
- **職責**：管理具體階段的業務邏輯。
- **鈎子函數**：
    - `onEnter`: 進入狀態時初始化資源（如播放特效、啟動 Timer）。
    - `onTick`: 每幀執行的邏輯檢查（如判定勝利條件）。
    - `onExit`: 離開前清理資源（如停止未完成的計時器）。
- **架構轉向**：所有原本在 Definition 中的動態行為，應全面下放到對應的 State 類別中。

### 2.4 GameData (資料載體/記憶體)
- **職責**：僅存放純粹的資料狀態。
- **主要內容**：
    - 持久化實體清單。
    - 運行中的 Timer ID 與標籤映射。
    - 計分板數值快照。

---

## 3. Timer 系統深度實作指南

### 3.1 註冊與綁定機制
Timer 的啟動不應透過靜態 Definition 調用，而應在 `GameState` 邏輯中觸發，並將狀態同步至 `GameData`。

#### 實作流程：
1. **觸發**：在 `GameState.onEnter()` 中呼叫啟動方法。
2. **註冊**：呼叫全域 `TimerManager.start()` 並傳入 Tick 長度與回調 Lambda。
3. **綁定**：將回傳的 `instanceId` 寫入 `GameData` 的 `timerInstanceIds()` 集合中。
4. **追蹤**：在 `timerTags()` 中為該 ID 綁定自定義標籤（如 `"round_limit"`），方便檢索。

#### 程式碼示範 (在 GameData 子類中)：
```java
public void startNewTimer(String tag, long ticks, Consumer<Integer> onExpire) {
    int id = TimerManager.start(ticks, onExpire);
    this.timerInstanceIds().add(id);
    this.timerTags().put(id, tag);
}
```

### 3.2 事件綁定與狀態切換
Timer 到期時的回調應避免執行複雜邏輯，建議僅執行「狀態切換」或「資料變更」：
```java
// 在 State 邏輯中
data.startNewTimer("phase_timer", 200, (id) -> {
    instance.transition(GameState.NEXT_PHASE);
});
```

---

## 4. 進階架構演進：ECS 整合

為了提升效能，建議將特定邏輯從 `GameState` 轉移至 ECS 系統：
- **TimerSystem**：自動掃描 `GameData` 中註冊的 Timer ID 並進行冷卻計算。
- **ScoreboardSystem**：每秒監控 `GameData` 的 `scoreboardValues`，自動向玩家發送零閃爍的虛擬計分板封包。

---

## 5. 最佳實踐建議
1. **強類型轉換**：開發者應透過 `GameManager.getInstance(id)` 獲取實例後進行操作，避免直接在 Definition 中寫入修改 Data 的方法。
2. **防錯機制**：在 `GameConfig` 中實作 `validate()`，確保遊戲實例建立前參數正確。
3. **ID 管理**：短 ID 權限應下放至各 Manager，`GameData` 的 ID 應由 `GameManager` 在 `createInstance` 時統一注入，而非自定義生成。

---

## 6. 實作進度對齊 (v0.2.5)

### 6.1 已落地方向
- `GameData` 已採用 `setupId(Identifier, shortId)` 注入，避免資料層自行產生 ID。
- `GameManager` 已保有 `ShortIdRegistry`，並提供 `resolveShortId(...)` / `getShortIdOf(...)`。
- `field` / `permission` / `rolegroup` / `team` 已朝 Manager 內部短 ID 註冊模式靠攏，並保留 full id 作為資料主鍵。

### 6.2 本階段原則
- 以 `Identifier` 作為持久化與核心查詢主鍵。
- 以短 ID 作為外部輸入友善層（指令、UI、除錯顯示）。
- 由 Manager 統一維護 short/full 綁定與生命週期（載入綁定、刪除解綁、clear 釋放）。

---

## 7. 舊版攝影機提案合併摘要

以下內容由舊檔整併為本檔附錄，避免架構文件分裂：

### 7.1 Camera 與視角控制
- 以 `CameraApi` 作為對外入口。
- 客戶端以 `ClientCameraManager` + modifier 模式（如 `ShakeModifier`、`PathAnimationModifier`）疊加效果。
- 使用封包 payload 在 server/client 間傳遞 camera action。

### 7.2 Control 系統
- 以 `ControlManager` 管理玩家與被控制實體的雙向映射。
- 輸入以 `ControlInputPayload` 傳輸，伺服器端維護最新輸入快取供實體讀取。

---

## 8. 下一步開發清單

1. 地形規劃生成輔助工具（與 `field` 整合）。
2. 完整收斂各 Manager 的短 ID 管理策略（統一 API 與測試）。
3. 嘗試做可控的實體動畫綁定。
4. 語音通話功能：
   - 距離衰減與方向性收音。
   - 依 `field` / `team` 隔離語音。
   - 由開發者切換語音模式策略。

