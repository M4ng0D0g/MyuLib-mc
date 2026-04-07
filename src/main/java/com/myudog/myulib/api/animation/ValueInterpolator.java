package com.myudog.myulib.api.animation;

@FunctionalInterface
public interface ValueInterpolator<T> {
    T interpolate(T startValue, T endValue, double progress);
}

