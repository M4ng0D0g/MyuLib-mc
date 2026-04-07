# GameObjectConfig
## Role
This page is the canonical reference for `GameObjectConfig` in the `game` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
GameObjectConfig API 參考

## 類別
`record GameObjectConfig`

## 欄位
- `id: Identifier`：物件唯一 id
- `type: Identifier?`：物件類型識別
- `name: String?`：顯示名稱
- `required: Boolean`：是否為必填物件
- `properties: Map<String, String>`：附加屬性，永遠非 `null` 且以不可變快照保存

## 建構
```java
new GameObjectConfig(id);
new GameObjectConfig(id, type, name, required);
new GameObjectConfig(id, type, name, required, properties);
```

## 行為
- `id` 不可為 `null`
- `properties` 會被轉成不可變 map
- `new GameObjectConfig(id)` 預設 `required = true`

## 用法
```java
GameObjectConfig config = new GameObjectConfig(
    Identifier.of("myulib", "respawn_anchor"),
    null,
    "Respawn Anchor",
    true,
    Map.of("facing", "north")
);
```

