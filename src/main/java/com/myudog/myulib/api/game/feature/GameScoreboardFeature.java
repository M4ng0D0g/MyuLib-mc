package com.myudog.myulib.api.game.feature;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameScoreboardFeature implements GameFeature {
    public String objectiveId;
    public String displayName;
    public final List<String> lines = new ArrayList<>();
    public final Map<String, Integer> values = new LinkedHashMap<>();

    public void setLine(int index, String value) {
        while (lines.size() <= index) {
            lines.add("");
        }
        lines.set(index, value);
    }

    public void setValue(String key, int value) {
        values.put(key, value);
    }

    public void clear() {
        objectiveId = null;
        displayName = null;
        lines.clear();
        values.clear();
    }
}
