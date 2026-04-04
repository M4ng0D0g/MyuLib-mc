package com.myudog.myulib.internal.game.components;

import com.myudog.myulib.api.game.components.ComponentManager;
import com.myudog.myulib.api.game.components.ComponentModels;

public class DefaultComponentBridge {
    public static void publish(ComponentModels.ComponentSignal signal) { ComponentManager.publish(signal); }
}
