# Canvas

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/Canvas.kt`

Description

Canvas is a `Box` configured for absolute positioning (anchors/offsets) and is typically used for HUD or overlay elements that need precise placement.

Public API

- Constructor:
  - `Canvas()`
- Behavior:
  - Sets the container's `flexContainer.direction` to `FlexDirection.ABSOLUTE` in init.

Usage example

```kotlin
val hudCanvas = Canvas()
val badge = Label("HP").apply { transform.offsetX = 10f; transform.offsetY = 20f }
hudCanvas.addChild(badge)
```

