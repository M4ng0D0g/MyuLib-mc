package com.myudog.myulib.api.animation;

public final class AnimationTestSuite {
    private AnimationTestSuite() {
    }

    public static void main(String[] args) {
        EasingTest.main(args);
        AnimatorComponentTest.main(args);
        com.myudog.myulib.client.internal.ui.system.AnimationSystemTest.main(args);
        System.out.println("Animation tests passed.");
    }
}


