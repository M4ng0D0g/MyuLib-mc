package com.myudog.myulib.client.internal.ui.system;

import com.myudog.myulib.api.animation.AnimationSpec;
import com.myudog.myulib.api.animation.AnimatorComponent;
import com.myudog.myulib.api.animation.Easing;
import com.myudog.myulib.api.animation.Interpolators;
import com.myudog.myulib.api.animation.PlayMode;
import com.myudog.myulib.api.animation.PlaybackState;
import com.myudog.myulib.api.animation.TestSupport;

import java.util.concurrent.atomic.AtomicReference;

public final class AnimationSystemTest {
    private AnimationSystemTest() {
    }

    public static void main(String[] args) {
        registerTickAndControlAnimationsFromTheSystem();
    }

    static void registerTickAndControlAnimationsFromTheSystem() {
        AnimationSystem system = new AnimationSystem();
        AtomicReference<Double> value = new AtomicReference<>(0.0);
        AnimationSpec<Double> spec = AnimationSpec.of(
            1000L,
            0.0,
            8.0,
            Easing.LINEAR,
            PlayMode.ONCE,
            Interpolators.DOUBLE
        );
        AnimatorComponent<Double> animator = new AnimatorComponent<>(spec, value::set);

        AnimationSystem.Handle<Double> handle = system.register("fade", animator);
        TestSupport.assertTrue(system.contains("fade"));
        TestSupport.assertEquals(1, system.size());

        handle.play();
        system.tick(500L);
        TestSupport.assertEquals(4.0, value.get(), 1.0e-9);
        TestSupport.assertEquals(PlaybackState.PLAYING, handle.animator().state());
        TestSupport.assertEquals(500L, handle.animator().elapsedMillis());

        handle.pause();
        system.tick(500L);
        TestSupport.assertEquals(4.0, value.get(), 1.0e-9);
        TestSupport.assertEquals(PlaybackState.PAUSED, handle.animator().state());

        handle.resume();
        system.tick(500L);
        TestSupport.assertEquals(8.0, value.get(), 1.0e-9);
        TestSupport.assertEquals(PlaybackState.FINISHED, handle.animator().state());

        handle.stop();
        TestSupport.assertEquals(0.0, value.get(), 1.0e-9);
        TestSupport.assertEquals(PlaybackState.STOPPED, handle.animator().state());

        handle.unregister();
        TestSupport.assertFalse(system.contains("fade"));
        TestSupport.assertEquals(0, system.size());
    }
}

