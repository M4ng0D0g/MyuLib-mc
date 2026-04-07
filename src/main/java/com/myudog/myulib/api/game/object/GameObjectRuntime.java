package com.myudog.myulib.api.game.object;

public interface GameObjectRuntime {
    default void onAttach(GameObjectContext context) {
    }

    default void onDetach(GameObjectContext context) {
    }

    default void onTick(GameObjectContext context) {
    }

    default boolean onInteract(GameObjectContext context) {
        return false;
    }
}

