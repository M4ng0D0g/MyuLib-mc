# ComponentManager
## Role
This page is the canonical reference for `ComponentManager` in the `component` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
ComponentManager API 參考

## 類別
`class ComponentManager`

## 公開方法
- `install()`
- `register(binding)` / `registerAll(bindings)` / `unregister(bindingId)`
- `get(bindingId)` / `getByOwner(ownerId)` / `getByGameInstance(instanceId)`
- `publish(signal)`
- `registerRule(rule)` / `clearRules()`
- `bindInstance(instance, bindings)` / `unbindInstance(instanceId)`

## 用法
```java
ComponentManager.register(binding);
ComponentManager.bindInstance(gameInstance, java.util.List.of(binding));
```

