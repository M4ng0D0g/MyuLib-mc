package com.myudog.myulib.api.event;

import com.myudog.myulib.api.event.listener.EventListener;
import com.myudog.myulib.internal.event.EventDispatcherImpl;

public final class ServerEventBus {
    private static final EventDispatcherImpl DISPATCHER = new EventDispatcherImpl();

    private ServerEventBus() {
    }

    public static EventDispatcherImpl dispatcher() {
        return DISPATCHER;
    }

    public static <T extends Event> EventListener<T> subscribe(Class<T> eventType, EventListener<T> listener) {
        return DISPATCHER.subscribe(eventType, listener);
    }

    public static <T extends Event> EventListener<T> subscribe(Class<T> eventType, int priority, EventListener<T> listener) {
        return DISPATCHER.subscribe(eventType, priority, listener);
    }

    public static <T extends Event> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
        DISPATCHER.unsubscribe(eventType, listener);
    }

    public static ProcessResult dispatch(Event event) {
        return DISPATCHER.dispatch(event);
    }
}
