# GameObjectRuntime

## Role
This page is the canonical reference for `GameObjectRuntime` in the `game` docs area.

## Unique responsibility
It defines the lifecycle contract for a runtime object that can be attached to a registered game object.

## Practical writing
Implement this interface on a module object when you want attach, tick, interact, and detach callbacks.

## Fields
- This interface defines no fields.

## Methods
- `onAttach(GameObjectContext context)`: called when the runtime is attached to a registered game object.
- `onDetach(GameObjectContext context)`: called when the runtime is detached or the game instance is destroyed.
- `onTick(GameObjectContext context)`: called during game-object tick dispatch.
- `onInteract(GameObjectContext context)`: called when an interaction is forwarded to the runtime; return `true` if the runtime consumed the event.

