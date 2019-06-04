package com.example.demo;

public enum LedPosition {

    Up (1),
    Down (2);

    private final int value;

     LedPosition(int value)
    {
        this.value = value;
    }

    public int getValue() {return  value;}


}
