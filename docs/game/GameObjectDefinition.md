# GameObjectDefinition

## Role
This page is the canonical reference for `GameObjectDefinition` in the `game` docs area.

## Unique responsibility
It stores the normalized definition of a game object after registration: id, kind, type, display name, requirement flag, and custom properties.

## Practical writing
Use this record when you need a stable object definition that can be stored in features or exposed to logic and hooks.

## Fields
- `id`: unique identifier of the game object.
- `kind`: gameplay classification used by hooks and interaction dispatch.
- `type`: optional lower-level type id, such as a block or entity type.
- `name`: display name or friendly label.
- `required`: whether the object is required by the game definition.
- `properties`: immutable custom property map.

## Methods
- Canonical record accessors are generated automatically by Java.
- Constructors: Java canonical and convenience constructors normalize `kind` and `properties`.

