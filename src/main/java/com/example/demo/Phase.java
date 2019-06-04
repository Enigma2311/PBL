package com.example.demo;

public enum Phase {

    Phase1(2),
    Phase2(3);

    private final int value;

    Phase(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
