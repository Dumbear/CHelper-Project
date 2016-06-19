package com.dumbear.chelper;

import java.math.BigInteger;

public class BearCircleGame {
    public static final int MODULO = 1000000007;
    public static final BigInteger BIG_INTEGER_MODULO = BigInteger.valueOf(MODULO);

    public static class Fraction {
        public static final Fraction ZERO = new Fraction(0, 1);
        public static final Fraction ONE = new Fraction(1, 1);
        public static final Fraction HALF = new Fraction(1, 2);

        private long x;
        private long y;

        public Fraction(long x, long y) {
            x = (x % MODULO + MODULO) % MODULO;
            y = (y % MODULO + MODULO) % MODULO;
            this.x = x;
            this.y = y;
        }

        public Fraction add(Fraction f) {
            return new Fraction(x * f.y + f.x * y, y * f.y);
        }

        public Fraction subtract(Fraction f) {
            return add(new Fraction(-f.x, f.y));
        }

        public Fraction multiply(Fraction f) {
            return new Fraction(x * f.x, y * f.y);
        }

        public Fraction divide(Fraction f) {
            return multiply(new Fraction(f.y, f.x));
        }

        public int result() {
            return (int) (x * BigInteger.valueOf(y).modInverse(BIG_INTEGER_MODULO).longValue() % MODULO);
        }
    }

    private int k;
    private Fraction[][] cache;

    public int winProbability(int n, int k) {
        this.k = k;
        cache = new Fraction[n + 1][n];
        return getProbability(n, 0).result();
    }

    private Fraction getProbability(int n, int winner) {
        if (cache[n][winner] != null) {
            return cache[n][winner];
        }
        if (n == 1) {
            return cache[n][winner] = Fraction.ONE;
        }
        Fraction a = Fraction.ONE;
        Fraction b = Fraction.ZERO;
        for (int next = k % n; ; next = (next + k) % n) {
            int w = (winner + n - next) % n;
            Fraction nextA = a.multiply(Fraction.HALF);
            Fraction nextB = b;
            if (w != n - 1) {
                nextB = a.multiply(Fraction.HALF).multiply(getProbability(n - 1, w)).add(b);
            }
            a = nextA;
            b = nextB;
            if (next == 0) {
                break;
            }
        }
        cache[n][winner] = b.divide(Fraction.ONE.subtract(a));
        Fraction last = cache[n][winner];
        for (int next = k % n; ; next = (next + k) % n) {
            int w = (winner + n - next) % n;
            cache[n][w] = last.divide(Fraction.HALF).subtract(w != n - 1 ? getProbability(n - 1, w) : Fraction.ZERO);
            last = cache[n][w];
            if (next == 0) {
                break;
            }
        }
        return cache[n][winner];
    }
}
