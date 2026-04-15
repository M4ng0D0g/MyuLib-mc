package com.myudog.myulib.api.projection;

public record ProjectionRenderStyle(
        boolean showPoints,
        boolean showLines,
        boolean showFaces,
        boolean showName,
        boolean showAxes
) {
    public static final int FLAG_POINTS = 1;
    public static final int FLAG_LINES = 1 << 1;
    public static final int FLAG_FACES = 1 << 2;
    public static final int FLAG_NAME = 1 << 3;
    public static final int FLAG_AXES = 1 << 4;

    public static ProjectionRenderStyle defaults() {
        return new ProjectionRenderStyle(false, true, false, false, true);
    }

    public static ProjectionRenderStyle full() {
        return new ProjectionRenderStyle(true, true, true, true, true);
    }

    public static ProjectionRenderStyle labelsOnly() {
        return new ProjectionRenderStyle(false, false, false, true, false);
    }

    public ProjectionRenderStyle withFeature(ProjectionFeature feature, boolean enabled) {
        return switch (feature) {
            case POINTS -> new ProjectionRenderStyle(enabled, showLines, showFaces, showName, showAxes);
            case LINES -> new ProjectionRenderStyle(showPoints, enabled, showFaces, showName, showAxes);
            case FACES -> new ProjectionRenderStyle(showPoints, showLines, enabled, showName, showAxes);
            case NAME -> new ProjectionRenderStyle(showPoints, showLines, showFaces, enabled, showAxes);
            case AXES -> new ProjectionRenderStyle(showPoints, showLines, showFaces, showName, enabled);
        };
    }

    public boolean isEnabled(ProjectionFeature feature) {
        return switch (feature) {
            case POINTS -> showPoints;
            case LINES -> showLines;
            case FACES -> showFaces;
            case NAME -> showName;
            case AXES -> showAxes;
        };
    }

    public byte toFlags() {
        int value = 0;
        if (showPoints) value |= FLAG_POINTS;
        if (showLines) value |= FLAG_LINES;
        if (showFaces) value |= FLAG_FACES;
        if (showName) value |= FLAG_NAME;
        if (showAxes) value |= FLAG_AXES;
        return (byte) value;
    }

    public static ProjectionRenderStyle fromFlags(byte flags) {
        int value = flags & 0xFF;
        return new ProjectionRenderStyle(
                (value & FLAG_POINTS) != 0,
                (value & FLAG_LINES) != 0,
                (value & FLAG_FACES) != 0,
                (value & FLAG_NAME) != 0,
                (value & FLAG_AXES) != 0
        );
    }
}

