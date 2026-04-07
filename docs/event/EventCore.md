# EventCore
## Role
This page is the canonical reference for `EventCore` in the `event` docs area.
## Unique responsibility
It documents the public API, intent, and practical usage of this class without mixing in unrelated systems.
## Practical writing
Use this page when you need the class-level contract, then follow the field and method sections below.
## Fields
- Fields are listed in the existing API content below.
## Methods
- Methods are listed in the existing API content below.
Event API
Public event-layer Java types:
- `com.myudog.myulib.api.event.Event`
- `com.myudog.myulib.api.event.FailableEvent`
- `com.myudog.myulib.api.event.EventPriority`
- `com.myudog.myulib.api.event.ProcessResult`
- `com.myudog.myulib.api.event.listener.EventListener`
- `com.myudog.myulib.internal.event.EventDispatcherImpl`
- `com.myudog.myulib.api.event.ServerEventBus`
- `com.myudog.myulib.api.event.events.EntitySpawnEvent`
- `com.myudog.myulib.api.event.events.ServerTickEvent`
- `com.myudog.myulib.api.ecs.event.ComponentAddedEvent`
## Quick example
```java
EventDispatcherImpl dispatcher = new EventDispatcherImpl();
dispatcher.subscribe(ServerTickEvent.class, event -> ProcessResult.PASS);
```