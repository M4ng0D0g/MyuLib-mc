package com.myudog.myulib.api.game.feature;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GameTimerFeature implements GameFeature {
    public final Set<Integer> timerInstanceIds = new LinkedHashSet<>();
    public final Map<Integer, String> tags = new LinkedHashMap<>();

    public boolean add(int timerInstanceId) {
        return add(timerInstanceId, null);
    }

    public boolean add(int timerInstanceId, String tag) {
        boolean added = timerInstanceIds.add(timerInstanceId);
        if (tag != null) {
            tags.put(timerInstanceId, tag);
        }
        return added;
    }

    public boolean remove(int timerInstanceId) {
        tags.remove(timerInstanceId);
        return timerInstanceIds.remove(timerInstanceId);
    }

    public void clear() {
        timerInstanceIds.clear();
        tags.clear();
    }
}
