package com.myudog.myulib.api.timer;

public record TimerBinding(int id, long tick, TimerTickBasis basis, TimerAction action) {
}
