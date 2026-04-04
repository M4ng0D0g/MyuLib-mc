package com.myudog.myulib.api.game.state;

public interface GameStateMachine<S extends Enum<S>> {
    S getCurrentState();

    boolean canTransition(S to);

    boolean transition(S to);

    void reset();
}

