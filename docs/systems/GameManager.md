# Game 系統使用說明（入口索引）

`game/` 是整個玩法主流程的中樞：負責遊戲定義、實例、feature 容器、區域綁定、component 綁定與 logic 規則。

## 系統入口
- [GameCore](../api/game/GameCore.md)
- [GameFeatures](../api/game/GameFeatures.md)
- [GameStateContracts](../api/game/GameStateContracts.md)
- [GameInstance](../api/game/GameInstance.md)
- [GameBootstrapConfig](../api/game/GameBootstrapConfig.md)
- [GameObjectConfig](../api/game/GameObjectConfig.md)
- [RespawnGameExample](../api/game/RespawnGameExample.md)

## 公開型別
- `Game`
- `GameManager`
- `GameStateContext<S>` / `GameTransition<S>` / `GameStateMachine<S>`
- `GameDefinition<S>`
- `GameInstance<S>`
- `GameBootstrapConfig` / `GameObjectConfig`
- `GameFeatureStore` / `GameFeature`
- `GameTimerFeature` / `GameScoreboardFeature` / `GameRegionFeature` / `GameComponentFeature` / `GameObjectBindingFeature` / `GameLogicFeature<S>`
- `RespawnGameExample` / `RespawnGameExample.RespawnGameState`

## 相關能力
- 遊戲主流程與狀態切換
- timer / region / component / logic 統一調度

## 文件導覽
- 詳細 API：`docs/api/game/*.md`
