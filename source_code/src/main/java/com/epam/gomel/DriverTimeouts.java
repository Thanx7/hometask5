package com.epam.gomel;

import java.util.concurrent.TimeUnit;

public enum DriverTimeouts {

    IMPLICIT_WAIT(10, TimeUnit.SECONDS),
    PAGE_LOAD(60, TimeUnit.SECONDS);

    private int value;
    private TimeUnit unit;

    DriverTimeouts(int value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public TimeUnit getUnit() {
        return unit;
    }
}
