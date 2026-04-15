package com.myudog.myulib.api.timer;

import net.minecraft.resources.Identifier;

import java.util.*;

public final class TimerDefinition {
    public final Identifier id;
    public final long durationTicks;
    public final TimerMode mode;
    public final boolean autoStopOnComplete;

    public final Map<Integer, TimerBinding> elapsedBindings = new LinkedHashMap<>();
    public final Map<Integer, TimerBinding> remainingBindings = new LinkedHashMap<>();
    public final List<TimerAction> startedActions = new ArrayList<>();
    public final List<TimerAction> pausedActions = new ArrayList<>();
    public final List<TimerAction> resumedActions = new ArrayList<>();
    public final List<TimerAction> resetActions = new ArrayList<>();
    public final List<TimerAction> stoppedActions = new ArrayList<>();
    public final List<TimerAction> completedActions = new ArrayList<>();
    private int nextBindingId = 1;

    public TimerDefinition(net.minecraft.resources.Identifier id, long durationTicks) { this(id, durationTicks, TimerMode.COUNT_UP, true); }
    public TimerDefinition(net.minecraft.resources.Identifier id, long durationTicks, TimerMode mode, boolean autoStopOnComplete) {
        this.id = Objects.requireNonNull(id, "id");
        this.durationTicks = Math.max(0L, durationTicks);
        this.mode = mode == null ? TimerMode.COUNT_UP : mode;
        this.autoStopOnComplete = autoStopOnComplete;
    }

    public TimerDefinition onElapsedTick(long tick, TimerAction action) { return onElapsedTick(tick, action, false); }
    public TimerDefinition onRemainingTick(long tick, TimerAction action) { return onRemainingTick(tick, action, false); }
    public TimerDefinition onElapsedTick(long tick, TimerAction action, boolean replace) { return addBinding(elapsedBindings, tick, TimerTickBasis.ELAPSED, action, replace); }
    public TimerDefinition onRemainingTick(long tick, TimerAction action, boolean replace) { return addBinding(remainingBindings, tick, TimerTickBasis.REMAINING, action, replace); }
    public TimerDefinition onStarted(TimerAction action) { startedActions.add(action); return this; }
    public TimerDefinition onPaused(TimerAction action) { pausedActions.add(action); return this; }
    public TimerDefinition onResumed(TimerAction action) { resumedActions.add(action); return this; }
    public TimerDefinition onReset(TimerAction action) { resetActions.add(action); return this; }
    public TimerDefinition onStopped(TimerAction action) { stoppedActions.add(action); return this; }
    public TimerDefinition onCompleted(TimerAction action) { completedActions.add(action); return this; }
    public boolean removeBinding(int bindingId) { return elapsedBindings.remove(bindingId) != null || remainingBindings.remove(bindingId) != null; }

    private TimerDefinition addBinding(Map<Integer, TimerBinding> bindings, long tick, TimerTickBasis basis, TimerAction action, boolean replace) {
        int id = nextBindingId++;
        if (!replace && bindings.values().stream().anyMatch(binding -> binding.tick() == tick && binding.basis() == basis)) {
            return this;
        }
        bindings.put(id, new TimerBinding(id, tick, basis, action));
        return this;
    }
}
