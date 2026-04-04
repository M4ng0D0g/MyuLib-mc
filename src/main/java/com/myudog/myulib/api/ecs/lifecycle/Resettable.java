package com.myudog.myulib.api.ecs.lifecycle;

import com.myudog.myulib.api.ecs.Component;

public interface Resettable extends Component {
    void reset();
}

