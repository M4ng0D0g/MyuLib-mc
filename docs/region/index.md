# Region System

The Region system handles area partitioning, lookup, and region lifecycle management.

## 類別架構關係
- `RegionCore` 描述 region 的資料模型與操作接口。
- `RegionManager` 是 region 的建立、綁定與更新中心。
- Region 會被 Game 系統用來把 instance 內的地圖區域、邏輯區段或玩法區間分開管理。
- 與 ECS / Game / Logic / Component 的聯動主要發生在 instance 更新與事件處理流程中。

## 目前進度
- ✅ Region 文件已整理到 canonical `docs/region/`。
- ✅ 與 GameInstance 的關係已在 game 章節的進度註記中交代。
- ⏳ 後續若擴充 region definition / runtime hook，會在此補充。

## Public class navigation list
- [RegionCore](RegionCore.md)
- [RegionManager](RegionManager.md)

## Large demo
```java
RegionManager regions = new RegionManager();
var region = regions.create("spawn");
region.addEntity(entityId);
regions.tickAll();
```

## Reading order
1. `RegionCore.md`
2. `RegionManager.md`
