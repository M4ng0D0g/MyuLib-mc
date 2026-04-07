package com.myudog.myulib.api.animation;

public final class TestSupport {
    private TestSupport() {
    }

    public static void assertEquals(double expected, double actual, double epsilon) {
        if (Math.abs(expected - actual) > epsilon) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("expected condition to be true");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("expected condition to be false");
        }
    }
}




