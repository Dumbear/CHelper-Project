package com.dumbear.chelper.library.geometry.twod;

public class PointInt implements Comparable<PointInt> {
    public int x, y;

    public PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double len() {
        return Math.sqrt((double) x * x + (double) y * y);
    }

    public long len2() {
        return (long) x * x + (long) y * y;
    }

    public PointInt rotate_left() {
        return new PointInt(-y, x);
    }

    public PointInt rotate_right() {
        return new PointInt(y, -x);
    }

    @Override
    public int compareTo(PointInt p) {
        if (x == p.x) {
            if (y == p.y) {
                return 0;
            }
            return y < p.y ? -1 : 1;
        } else {
            return x < p.x ? -1 : 1;
        }
    }

    public PointInt add(PointInt p) {
        return new PointInt(x + p.x, y + p.y);
    }

    public PointInt subtract(PointInt p) {
        return new PointInt(x - p.x, y - p.y);
    }

    public long dot(PointInt p) {
        return (long) x * p.x + (long) y * p.y;
    }

    public long cross(PointInt p) {
        return (long) x * p.y - (long) y * p.x;
    }
};
