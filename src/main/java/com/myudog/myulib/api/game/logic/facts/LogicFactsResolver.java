package com.myudog.myulib.api.game.logic.facts;

import com.myudog.myulib.api.game.instance.GameInstance;
import net.minecraft.util.Identifier;

public interface LogicFactsResolver {
    LogicFactsResolver DEFAULT = new LogicFactsResolver() {
    };

    default int playerCount(GameInstance<?> instance) {
        return 0;
    }

    default int playerCountInTeam(GameInstance<?> instance, Identifier teamId) {
        return 0;
    }

    default boolean isOnTeam(GameInstance<?> instance, Identifier playerId, Identifier teamId) {
        return false;
    }

    default boolean isRedTeam(GameInstance<?> instance, Identifier playerId) {
        return false;
    }

    default int gameTimeTicks(GameInstance<?> instance) {
        return instance == null ? 0 : (int) instance.getTickCount();
    }

    default boolean hasSpecialObject(GameInstance<?> instance, Identifier objectId) {
        return instance != null && instance.hasSpecialObject(objectId);
    }
}
