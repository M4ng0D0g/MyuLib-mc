package com.myudog.myulib.api.projection;

import java.util.Locale;

public enum ProjectionFeature {
    POINTS,
    LINES,
    FACES,
    NAME,
    AXES;

    public String token() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static ProjectionFeature parse(String raw) {
        return ProjectionFeature.valueOf(raw.toUpperCase(Locale.ROOT));
    }
}

