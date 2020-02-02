package dev.dbdh.Discord.Utilities;

import java.util.Random;

public class Color {
    public static int deepRed = 0xb20c0c;
    public static int red = 0xCC0000;
    public static int yellow = 0xFFFF00;
    public static int gold = 0xCCAC00;
    public static int darkGreen = 0x228B22;
    public static int babyBlue = 0xa1e5f3;
    public static int purpleHaze = 0x5d4e64;
    public static int darkSlateBlue = 0x483d8b;
    public static int errorRed = 0xff4d4d;
    public static int successGreen = 0x40b62a;
    public static int forestGreen = 0x2228b22;

    public int getRandomColor(){
        Random obj = new Random();
        int random_number = obj.nextInt(0xffffff + 1);
        return random_number;
    }
}
