# Logic System

The Logic system provides conditions, actions, signals, and a rule resolver.

## 類別架構關係
- `LogicCore` 是 logic 系統的對外核心入口。
- `LogicConditions` / `LogicActions` 分別定義條件與動作。
- `LogicSignals` 是跨系統通知與狀態變更的訊號集合。
- `LogicFactsResolver` 是 facts 查詢層，會讀 GameInstance、Team、Object、Timer 等資料。
- `LogicEngine` 負責規則註冊、條件評估與動作執行。
- Game / Timer / Team / GameObject 的資料會在這一層被轉成可判斷的 facts。

## 目前進度
- ✅ Logic 文件已整理到 canonical `docs/logic/`。
- ✅ `LogicFactsResolver` 已接到 team / object / timer 的實際查詢。
- ⏳ 後續若新增更多 rules / facts，會再補進 class 頁。

## Public class navigation list
- [LogicCore](LogicCore.md)
- [LogicConditions](LogicConditions.md)
- [LogicActions](LogicActions.md)
- [LogicSignals](LogicSignals.md)
- [LogicFactsResolver](LogicFactsResolver.md)

## Large demo
```java
LogicFactsResolver resolver = new LogicFactsResolver();
resolver.registerCondition("has_key", ctx -> true);
resolver.registerAction("open_door", ctx -> System.out.println("open"));
resolver.evaluate(signal);
```

## Reading order
1. `LogicCore.md`
2. `LogicConditions.md`
3. `LogicActions.md`
4. `LogicSignals.md`
5. `LogicFactsResolver.md`
