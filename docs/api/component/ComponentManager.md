# ComponentManager API 參考

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

