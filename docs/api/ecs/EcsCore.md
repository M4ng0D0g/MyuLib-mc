# ECS API
Public ECS Java types:
- `com.myudog.myulib.api.ecs.Component`
- `com.myudog.myulib.api.ecs.EcsWorld`
- `com.myudog.myulib.internal.ecs.ComponentStorage`
- `com.myudog.myulib.api.ecs.event.ComponentAddedEvent`
- `com.myudog.myulib.api.ecs.lifecycle.Resettable`
- `com.myudog.myulib.api.ecs.lifecycle.DimensionAware`
- `com.myudog.myulib.api.ecs.lifecycle.DimensionChangePolicy`
- `com.myudog.myulib.api.ecs.lifecycle.ComponentLifecycle`
## Quick example
```java
EcsWorld world = new EcsWorld();
int entity = world.createEntity();
world.addComponent(entity, new TransformComponent());
```