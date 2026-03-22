# Panel

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/Panel.kt`

Description

A higher-level container based on `Box` with typical panel defaults (padding, background). Useful as a window or grouped area.

Public API

- Constructor:
  - `Panel()`
- Behavior:
  - Provides default padding and style; can host children like any container.

Usage example

```kotlin
val p = Panel()
p.addChild(Label("Settings"))
```

