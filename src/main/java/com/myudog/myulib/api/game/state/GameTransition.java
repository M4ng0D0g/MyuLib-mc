package com.myudog.myulib.api.game.state;

public record GameTransition<S extends Enum<S>>(S from, S to, boolean allowed) {
    public GameTransition(S from, S to) {
        this(from, to, true);
    }
}

