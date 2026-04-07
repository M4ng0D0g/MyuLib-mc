# ComponentSystem
## Role
This page is the canonical reference for `ComponentSystem` in the `ui` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
Component 系統使用說明（入口索引）

`components/` 是事件驅動的玩法綁定層，負責把 mixin / hook 轉成高階 signal，並在符合條件時執行動作。

## 系統入口
- [ComponentCore](../api/component/ComponentCore.md)
- [ComponentManager](../api/component/ComponentManager.md)

## 公開型別
- [`ComponentBindingTarget`](../api/component/ComponentCore.md)
- [`ComponentSignal`](../api/component/ComponentCore.md)
- [`ComponentEntitySpawnSignal`](../api/component/ComponentCore.md)
- [`ComponentEntityTickSignal`](../api/component/ComponentCore.md)
- [`ComponentBlockBreakSignal`](../api/component/ComponentCore.md)
- [`ComponentBlockUseSignal`](../api/component/ComponentCore.md)
- [`ComponentCustomSignal`](../api/component/ComponentCore.md)
- [`ComponentContext`](../api/component/ComponentCore.md)
- [`ComponentCondition`](../api/component/ComponentCore.md)
- [`ComponentAction`](../api/component/ComponentCore.md)
- [`ComponentRule`](../api/component/ComponentCore.md)
- [`ComponentBindingDefinition`](../api/component/ComponentCore.md)
- [`ComponentRuleSet`](../api/component/ComponentCore.md)
- [`ComponentManager`](../api/component/ComponentManager.md)

## 本系統適合做什麼
- 特殊方塊互動
- 特殊實體生成 / tick
- 地盤物件行為
- 與 region / logic / timer 串接的高階玩法

## 文件導覽
- 詳細 API：`docs/api/component/*.md`



