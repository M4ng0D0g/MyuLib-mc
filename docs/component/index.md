# Component System

The Component system describes data binding and rule-driven attachment flows.

## 類別架構關係
- `ComponentCore` 描述 component 綁定模型與規則入口。
- `ComponentManager` 是實際註冊 / 追蹤 / 套用 component 的管理器。
- Component 系統會被 `GameInstance.components()`、ECS 與 Region/Game 流程共同使用。
- 這一層是資料掛載與規則處理的中介，不直接負責 render 或 input。

## 目前進度
- ✅ Component 文件已整理到 canonical `docs/component/`。
- ✅ 與 Game 系統的綁定關係已在 `GameInstance` 文件註記。
- ⏳ 後續若新增 component rule / binding 類型，會再補 class 頁。

## Public class navigation list
- [ComponentCore](ComponentCore.md)
- [ComponentManager](ComponentManager.md)

## Large demo
```java
ComponentManager manager = new ComponentManager();
manager.bind(entityId, someComponent);
manager.tickAll();
```

## Reading order
1. `ComponentCore.md`
2. `ComponentManager.md`
