# GameObjectHooks

## Role
This page is the canonical reference for `GameObjectHooks` in the `game` docs area.

## Unique responsibility
It is the facade used by mixins or other low-level hooks to register, attach, tick, interact with, and detach game objects.

## Practical writing
Use this class from mixin entry points so hook code stays tiny and all actual logic remains in `GameObjectBindingFeature`.

## Fields
- This class defines no fields.

## Methods
- `register(GameInstance<?> instance, GameObjectConfig config, Object runtimeObject)`: registers a game object and optionally attaches a runtime.
- `attach(GameInstance<?> instance, Identifier objectId, Object runtimeObject)`: attaches a runtime object to an existing registered game object.
- `detach(GameInstance<?> instance)`: detaches all runtime objects from the instance.
- `tick(GameInstance<?> instance)`: forwards tick to all runtime objects.
- `interact(GameInstance<?> instance, Identifier objectId, Identifier sourceEntityId, GameObjectKind kind, Map<String, String> payload)`: forwards an interaction to the matching runtime.

