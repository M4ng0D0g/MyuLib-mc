# GameObjectContext

## Role
This page is the canonical reference for `GameObjectContext` in the `game` docs area.

## Unique responsibility
It carries the live runtime data needed by object hooks: which instance is involved, which object is targeted, what kind of interaction happened, and any extra payload.

## Practical writing
Use this record as the method parameter for object attach, tick, interact, and detach flows.

## Fields
- `instance`: the current `GameInstance`; may be null in low-level tests or bootstrap-only flows.
- `objectId`: the registered game object id.
- `config`: the object declaration data that describes the object.
- `runtimeObject`: the runtime backing object attached to this game object.
- `sourceEntityId`: the entity or actor that caused the interaction, when available.
- `interactionKind`: the current interaction category such as `RESPAWN_POINT` or `USABLE`.
- `payload`: string map carrying arbitrary extra data for hooks and mixins.

## Methods
- Canonical record accessors are generated automatically by Java.
- `payload()`: returns the immutable payload map.

