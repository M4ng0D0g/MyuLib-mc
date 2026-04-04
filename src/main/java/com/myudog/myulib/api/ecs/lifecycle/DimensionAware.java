package com.myudog.myulib.api.ecs.lifecycle;

import com.myudog.myulib.api.ecs.Component;

public interface DimensionAware extends Component {
    default DimensionChangePolicy getDimensionPolicy() {
        return DimensionChangePolicy.KEEP;
    }
}

