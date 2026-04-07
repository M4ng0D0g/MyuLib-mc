# UI System

The UI system is responsible for layout, render, input, drag/drop, theme, and animation scheduling.

## 類別架構關係
- `LayoutSystem` 計算版面與尺寸，產生 `ComputedTransform`。
- `RenderSystem` 讀取 layout 結果並執行繪製。
- `InputSystem` 處理滑鼠 / 鍵盤 / 互動焦點。
- `DragDropSystem` 負責拖放中的暫時狀態與 top-layer 顯示。
- `ThemeLoader` / `UiTheme` / `TextureRegistry` 負責視覺主題與資源。
- `AnimationSystem` 與 `TransformComponent` / `WidgetStateComponent` 共同提供宣告式動畫能力。
- `UI Nodes` 是高階元件層，負責把宣告式狀態組合成真正的 screen tree。

## 目前進度
- ✅ UI docs 已重構為 canonical `docs/ui/` + `docs/ui/nodes/`。
- ✅ Animation target / declarative UI 的文檔已寫入大示範。
- 🟡 `AnimationSystem` 已實作；`LayoutSystem` / `RenderSystem` / `InputSystem` / `DragDropSystem` / `ThemeLoader` 目前仍以骨架或逐步補齊為主。
- ⏳ 下一步會把真正的 screen / node 寫法與 mixin-driven flow 補進 docs。

## Public class navigation list
- [AnimationSystem](AnimationSystem.md)
- [ComponentSystem](ComponentSystem.md)
- [DragDropSystem](DragDropSystem.md)
- [GameManager](GameManager.md)
- [InputSystem](InputSystem.md)
- [LayoutSystem](LayoutSystem.md)
- [LogicSystem](LogicSystem.md)
- [RegionSystem](RegionSystem.md)
- [RenderSystem](RenderSystem.md)
- [ThemeLoader](ThemeLoader.md)
- [TimerSystem](TimerSystem.md)
- [VFX](VFX.md)
- [UI Nodes navigation](nodes/index.md)

## Large demo
```java
public final class DemoUIScreen {
    private final AnimationSystem animationSystem = new AnimationSystem();
    public void init() {
        // build declarative UI tree
        // register animations, but do not play until ready
    }
    public void update(long deltaMillis) {
        animationSystem.tick(deltaMillis);
        LayoutSystem.update(world, screenWidth, screenHeight);
    }
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.render(world, context, mouseX, mouseY, delta);
    }
}
```

## Reading order
1. `LayoutSystem.md`
2. `RenderSystem.md`
3. `InputSystem.md`
4. `DragDropSystem.md`
5. `AnimationSystem.md`
6. `nodes/index.md`
