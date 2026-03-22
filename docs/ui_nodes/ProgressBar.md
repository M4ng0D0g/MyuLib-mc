# ProgressBar

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/ProgressBar.kt`

Description

A configurable progress bar supporting ghost progress (e.g., health drop shadow), text label, and configurable lerp speed and direction.

Public API

- Constructor:
  - `ProgressBar()`
- Fluent methods / configuration:
  - `fun withProgress(p: Float): ProgressBar`
  - `fun withSupplier(s: () -> Float): ProgressBar`
  - `fun withLerp(speed: Float): ProgressBar`
  - `fun withDirection(d: ProgressDirection): ProgressBar`
  - `fun showPercentage(): ProgressBar`

Usage example

```kotlin
val pb = ProgressBar().withProgress(0.5f).showPercentage()
```

