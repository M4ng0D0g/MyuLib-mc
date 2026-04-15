package com.myudog.myulib.api.debug;

public enum DebugFeature {
    PERMISSION,
    FIELD,
    ROLEGROUP,
    TEAM,
    GAME,
    TIMER,
    CONTROL,
    CAMERA,
    COMMAND;

    public static DebugFeature parse(String raw) {
        return DebugFeature.valueOf(raw.toUpperCase().replace('-', '_'));
    }

    public String token() {
        return name().toLowerCase().replace('_', '-');
    }
}

