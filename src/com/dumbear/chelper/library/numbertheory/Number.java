package com.dumbear.chelper.library.numbertheory;

public class Number {
    public static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    public static long gcd(long x, long y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    public static int lcm(int x, int y) {
        return x / gcd(x, y) * y;
    }

    public static long lcm(long x, long y) {
        return x / gcd(x, y) * y;
    }
}
