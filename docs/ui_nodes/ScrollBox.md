# ScrollBox

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/ScrollBox.kt`

Description

A scrollable container that uses scissoring to clip child rendering and manages a `ScrollComponent` for scroll state. Extends `Box`.

Public API

- Constructor:
  - `ScrollBox()`
- Properties:
  - `val scrollData: ScrollComponent`
- Behavior:
  - Adds `ScrollComponent` to the entity on init.
  - Renders children within a scissored region and optionally draws a scrollbar when content overflows.

Usage example

```kotlin
val listBox = ScrollBox().apply {
    // add children (rows) to this container
}
```

