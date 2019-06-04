package com.example.demo;

public enum LedStatus {

    Off(0),
    On(1),
    Phase1(2),
    Phase2(3);

    private final int value;

    LedStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
