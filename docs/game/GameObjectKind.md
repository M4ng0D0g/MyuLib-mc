# GameObjectKind

## Role
This page is the canonical reference for `GameObjectKind` in the `game` docs area.

## Unique responsibility
It classifies a game object's gameplay role, so the runtime and hooks can decide what kind of interaction is being processed.

## Practical writing
Use this enum when registering special objects or when dispatching an interaction from mixin/hook code.

## Fields
- `RESPAWN_POINT`: object used as a respawn anchor or spawn point.
- `MINEABLE`: object that can be mined or broken.
- `USABLE`: object that can be activated or used.
- `ATTACKABLE`: object that can receive attack interactions.
- `INTERACTABLE`: general interaction target.
- `CUSTOM`: fallback kind for module-defined behavior.

## Methods
- `isRed()`: not part of this type.
- No instance methods; this enum is a pure classification value.

