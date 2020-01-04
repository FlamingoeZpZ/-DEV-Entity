package dev.dbdh.Discord.Utilities;

import java.util.Random;

public class Color {
    public int deepRed = 0xb20c0c;
    public int red = 0xCC0000;
    public int yellow = 0xFFFF00;
    public int gold = 0xCCAC00;
    public int darkGreen = 0x228B22;
    public int babyBlue = 0xa1e5f3;
    public int purpleHaze = 0x5d4e64;
    public int darkSlateBlue = 0x483d8b;
    public int errorRed = 0xff4d4d;
    public int successGreen = 0x40b62a;

    public int getRandomColor(){
        Random obj = new Random();
        int random_number = obj.nextInt(0xffffff + 1);
        return random_number;
    }
}
