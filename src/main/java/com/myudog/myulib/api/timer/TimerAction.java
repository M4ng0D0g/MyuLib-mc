package com.myudog.myulib.api.timer;

@FunctionalInterface
public interface TimerAction {
    void invoke(TimerSnapshot snapshot);
}
