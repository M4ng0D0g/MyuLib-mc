# RegionSystem
## Role
This page is the canonical reference for `RegionSystem` in the `ui` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
Region 系統使用說明（入口索引）

`region/` 是獨立於遊戲主狀態的長期空間系統，負責 AABB 區域、主區域包裹、跨遊戲碰撞檢查，以及區域規則。

## 系統入口
- [RegionCore](../api/region/RegionCore.md)
- [RegionManager](../api/region/RegionManager.md)

## 公開型別
- [`RegionBounds`](../api/region/RegionCore.md)
- [`RegionRole`](../api/region/RegionCore.md)
- [`RegionDefinition`](../api/region/RegionCore.md)
- [`RegionSignal`](../api/region/RegionCore.md)
- [`RegionRegisteredSignal`](../api/region/RegionCore.md)
- [`RegionUnregisteredSignal`](../api/region/RegionCore.md)
- [`RegionEnteredSignal`](../api/region/RegionCore.md)
- [`RegionExitedSignal`](../api/region/RegionCore.md)
- [`RegionBoundarySignal`](../api/region/RegionCore.md)
- [`RegionContext`](../api/region/RegionCore.md)
- [`RegionCondition`](../api/region/RegionCore.md)
- [`RegionAction`](../api/region/RegionCore.md)
- [`RegionRule`](../api/region/RegionCore.md)
- [`RegionRuleSet`](../api/region/RegionCore.md)
- [`RegionManager`](../api/region/RegionManager.md)

## 本系統適合做什麼
- 戰場邊界
- 準備區 / 出生區
- 區域限制行為
- 長期存在的系統空間管理

## 文件導覽
- 詳細 API：`docs/api/region/*.md`


