# Stack

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/Stack.kt`

Description

A simple stacking container which overlays children on top of each other (Z-order follows child order).

Public API

- Constructor:
  - `Stack()`

Usage example

```kotlin
val s = Stack()
s.addChild(Image(...))
s.addChild(Label("On top"))
```

