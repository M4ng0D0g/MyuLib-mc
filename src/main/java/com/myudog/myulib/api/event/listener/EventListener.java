package com.myudog.myulib.api.event.listener;

import com.myudog.myulib.api.event.Event;
import com.myudog.myulib.api.event.ProcessResult;

@FunctionalInterface
public interface EventListener<T extends Event> {
    ProcessResult handle(T event);
}
