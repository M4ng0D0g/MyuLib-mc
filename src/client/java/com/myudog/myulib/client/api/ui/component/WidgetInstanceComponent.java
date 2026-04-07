package com.myudog.myulib.client.api.ui.component;

import com.myudog.myulib.api.ecs.Component;

public class WidgetInstanceComponent implements Component {
    public String widgetId;
    public Object widget;
    public boolean dirty = true;
}
