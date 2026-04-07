package com.myudog.myulib.client.api.ui.component;

import com.myudog.myulib.api.ecs.Component;

public class WidgetStateComponent implements Component {
    public boolean visible = true;
    public boolean enabled = true;
    public boolean hovered;
    public boolean pressed;
    public boolean focused;
}
