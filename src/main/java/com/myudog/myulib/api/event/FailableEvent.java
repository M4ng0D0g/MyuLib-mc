package com.myudog.myulib.api.event;

public interface FailableEvent extends Event {
    String getErrorMessage();

    void setErrorMessage(String errorMessage);
}
