package com.dumbear.chelper.library.geometry.twod;

import com.dumbear.chelper.library.geometry.Common;

public class Point implements Comparable<Point> {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double len() {
        return Math.sqrt(x * x + y * y);
    }

    public Point trunc(double l) {
        double r = l / len();
        return new Point(x * r, y * r);
    }

    public Point rotate_left() {
        return new Point(-y, x);
    }

    public Point rotate_left(double ang) {
        double c = Math.cos(ang), s = Math.sin(ang);
        return new Point(x * c - y * s, y * c + x * s);
    }

    public Point rotate_right() {
        return new Point(y, -x);
    }

    public Point rotate_right(double ang) {
        double c = Math.cos(ang), s = Math.sin(ang);
        return new Point(x * c + y * s, y * c - x * s);
    }

    @Override
    public int compareTo(Point p) {
        if (Common.sgn(x - p.x) == 0) {
            if (Common.sgn(y - p.y) == 0) {
                return 0;
            }
            return y < p.y ? -1 : 1;
        } else {
            return x < p.x ? -1 : 1;
        }
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public Point subtract(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    public double dot(Point p) {
        return x * p.x + y * p.y;
    }

    public double cross(Point p) {
        return x * p.y - y * p.x;
    }

    public Point multiple(double r) {
        return new Point(x * r, y * r);
    }

    public Point divide(double r) {
        return new Point(x / r, y / r);
    }
};
