package com.example.demo;

public class Color {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color CYAN = new Color(0, 1, 1);
    public static final Color PINK = new Color(1, 0, 1);
    public static final Color YELLOW = new Color(1, 1, 0);
    public static final Color WHITE = new Color(1, 1, 1);

    private int Red;
    private int Green;
    private int Blue;

    private Color(int red, int green, int blue)
    {
        this.Red = red;
        this.Green = green;
        this.Blue = blue;
    }

    public int[] ToByte()
    {
        return new int[]{ Red, Green, Blue };
    }


}
