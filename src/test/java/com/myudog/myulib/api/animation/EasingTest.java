package com.myudog.myulib.api.animation;

final class EasingTest {
    public static void main(String[] args) {
        linearClampsOutOfRangeProgress();
        commonCurvesMatchExpectedMidpointValues();
    }

    static void linearClampsOutOfRangeProgress() {
        TestSupport.assertEquals(0.0, Easing.LINEAR.apply(-0.5), 1.0e-9);
        TestSupport.assertEquals(1.0, Easing.LINEAR.apply(1.5), 1.0e-9);
    }

    static void commonCurvesMatchExpectedMidpointValues() {
        TestSupport.assertEquals(0.25, Easing.EASE_IN_QUAD.apply(0.5), 1.0e-9);
        TestSupport.assertEquals(0.75, Easing.EASE_OUT_QUAD.apply(0.5), 1.0e-9);
        TestSupport.assertEquals(0.5, Easing.EASE_IN_OUT_QUAD.apply(0.5), 1.0e-9);
        TestSupport.assertEquals(0.125, Easing.EASE_IN_CUBIC.apply(0.5), 1.0e-9);
        TestSupport.assertEquals(0.875, Easing.EASE_OUT_CUBIC.apply(0.5), 1.0e-9);
        TestSupport.assertEquals(0.5, Easing.SMOOTH_STEP.apply(0.5), 1.0e-9);
    }
}



