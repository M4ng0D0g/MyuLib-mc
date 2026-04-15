# MyuLib-mc Docs

## 系統總覽

每個系統都有自己的資料夾與 `index.md`。建議閱讀順序：
1. 先看系統 `index.md`
2. 再看該系統底下的 class 詳細頁
3. UI 另外先從 `docs/ui/nodes/index.md` 開始

### 核心系統
- [ECS](docs/ecs/index.md)
- [Event](docs/event/index.md)
- [Component](docs/component/index.md)
- [Access Systems](docs/access/index.md)
- [Field](docs/field/index.md)
- [Game](docs/game/index.md)
- [Timer](docs/timer/index.md)
- [Logic](docs/logic/index.md)
- [Floating](docs/floating/index.md)
- [Object](docs/object/index.md)
- [Camera](docs/camera/index.md)
- [Animation](docs/animation/index.md)
- [UI](docs/ui/index.md)
- [UI Nodes](docs/ui/nodes/index.md)

### 指令介面入口
- [Access Command Interface](docs/access/index.md)
- 所有測試指令使用 `/myulib:<feature>` 命名規則。

### 舊版路徑
- `docs/api/`、`docs/systems/`、`docs/ui_nodes/` 保留作相容與過渡參考；新文件以 canonical 路徑為準。

### API 套件掃描文件
- [Main API Catalog](docs/main_api/index.md)
- [Client API Catalog](docs/client_api/index.md)
- 以上文件按 `main.api` / `client.api` 子資料夾拆分為獨立子 docs。

## 目前進度總覽

- **ECS**：核心文件已整理成 canonical index，作為整個專案資料流基底。
- **Event**：事件匯流排與跨系統訊號文件已收斂到 `docs/event/`。
- **Component**：Component / Manager / Binding 關係已集中到 `docs/component/`。
- **Access Systems**：`Field` / `Permission` / `RoleGroup` / `Team` / `Game` / `Timer` 已統一接到 `/myulib:<feature>` 指令介面。
- **Region**：舊 `region` 文件保留過渡參考，主線請改看 `docs/field/`。
- **Game**：遊戲流程核心由 `GameManager` + `GameDefinition` + `GameInstance` 架構維護。
- **Timer**：Timer core 與管理流程整理於 `docs/timer/`。
- **Logic**：條件 / 動作 / 信號 / facts resolver 文件已集中。
- **Floating**：懸浮物體與 VFX 文件已集中到 `docs/floating/`。
- **Animation**：手動播放動畫核心、`AnimationSystem` 與 UI target adapter 設計已整理。
- **UI**：docs 已重構；runtime layout/render/input 仍持續補齊。
- **UI Nodes**：已建立節點索引與示範入口，後續逐頁補齊 fields / methods 標準格式。
