# DataBoundGrid

Path: `src/client/kotlin/com/myudog/myulib/client/api/ui/node/DataBoundGrid.kt`

Description

A grid variant that is data-bound: it uses a `ListProvider` to produce children dynamically.

Public API

- Constructor:
  - `DataBoundGrid(provider: ListProvider<T>, spacing: Float = 2f, mapper: (T) -> Box)`
- Behavior:
  - Automatically populates children from the provider and updates on changes if provider is observable.

Usage example

```kotlin
val grid = DataBoundGrid(myProvider) { item -> ItemSlot(item.stack) }
```

