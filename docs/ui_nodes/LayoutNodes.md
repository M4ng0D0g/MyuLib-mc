# LayoutNodes

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/LayoutNodes.kt`

Description

Collection of helper nodes (Column, Row, etc.) used by the layout system. Check `LayoutSystem` for measurement & distribution algorithm.

Public API

- Includes helper node types such as `Column`, `Row`, etc., that are used to structure layouts.

Usage example

```kotlin
val col = Column()
col.addChild(Label("A"))
col.addChild(Label("B"))
```

