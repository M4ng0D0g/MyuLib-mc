# Placeholder

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/Placeholder.kt`

Description

A debug placeholder widget that draws a colored box and optional border; useful during layout prototyping.

Public API

- Constructor:
  - `Placeholder(width: Float = 20f, height: Float = 20f, isDebug: Boolean = true)`
- Behavior:
  - Chooses a color from a debug palette and renders a color block.

Usage example

```kotlin
val ph = Placeholder(40f, 20f)
```

