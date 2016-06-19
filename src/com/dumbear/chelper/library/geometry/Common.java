package com.dumbear.chelper.library.geometry;

public class Common {
    public static final double EPS = 1e-8;
    public static final double PI = Math.acos(-1.0);

    public static int sgn(double d) {
        return d > EPS ? 1 : (d < -EPS ? -1 : 0);
    }

    public static double trim(double d) {
        return trim(d, 1.0);
    }

    public static double trim(double d, double l) {
        return d > l ? l : (d < -l ? -l : d);
    }
}
