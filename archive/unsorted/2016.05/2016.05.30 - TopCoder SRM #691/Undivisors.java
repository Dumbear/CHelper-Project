package com.dumbear.chelper;

import com.dumbear.chelper.library.util.PolynomialEncoder;

public class Undivisors {
    private static final int LIMIT = 62;
    private static final int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61};

    private PolynomialEncoder encoder;
    private int[] code = new int[LIMIT];
    private int[][] powers = new int[PRIMES.length][];

    public double getexp(String[] a) {
        int[][] matrix = new int[a.length][a[0].length()];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                matrix[i][j] = parseValue(a[i].charAt(j));
            }
        }
        return getexp(matrix);
    }

    private int parseValue(char c) {
        if (Character.isDigit(c)) {
            return c - '0';
        }
        if (Character.isUpperCase(c)) {
            return c - 'A' + 10;
        }
        if (Character.isLowerCase(c)) {
            return c - 'a' + 36;
        }
        return -1;
    }

    private double getexp(int[][] matrix) {
        initEncoder();
        long[] count = countMatrices(matrix);
        for (int i = 0; i < PRIMES.length; ++i) {
            for (int s = 0; s < count.length; ++s) {
                if (encoder.get(s, i) == 0) {
                    s = encoder.inc(s, i);
                }
                if (s < count.length) {
                    count[s] += count[encoder.dec(s, i)];
                }
            }
        }
        for (int s = 0; s < count.length; ++s) {
            count[s] *= count[s];
        }
        for (int i = 0; i < PRIMES.length; ++i) {
            for (int s = count.length - 1; s >= 0; --s) {
                if (encoder.get(s, i) == 0) {
                    s = encoder.dec(s, i);
                }
                if (s >= 0) {
                    count[s] -= count[encoder.dec(s, i)];
                }
            }
        }
        long totalCount = ((matrix.length + 1) * matrix.length) * ((matrix[0].length + 1) * matrix[0].length) / 4;
        totalCount *= totalCount;
        double e = 0.0;
        for (int s = 0; s < count.length; ++s) {
            if (count[s] == 0) {
                continue;
            }
            int p = Integer.MAX_VALUE;
            for (int i = 0; i < PRIMES.length; ++i) {
                p = Math.min(p, powers[i][encoder.get(s, i) + 1]);
            }
            e += (double) p * count[s] / totalCount;
        }
        return e;
    }

    private void initEncoder() {
        int[] weights = new int[PRIMES.length];
        for (int i = 0; i < weights.length; ++i) {
            weights[i] = 1;
            for (int x = PRIMES[i]; x < LIMIT; x *= PRIMES[i]) {
                ++weights[i];
            }
            powers[i] = new int[weights[i] + 1];
            powers[i][0] = 1;
            for (int j = 1; j < powers[i].length; ++j) {
                powers[i][j] = powers[i][j - 1] * PRIMES[i];
            }
        }
        encoder = new PolynomialEncoder(weights);
        for (int i = 1; i < LIMIT; ++i) {
            code[i] = 0;
            int k = i;
            for (int j = 0; j < PRIMES.length; ++j) {
                while (k % PRIMES[j] == 0) {
                    k /= PRIMES[j];
                    code[i] = encoder.inc(code[i], j);
                }
            }
        }
    }

    private long[] countMatrices(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][][] cols = new int[n][n][m];
        for (int y = 0; y < m; ++y) {
            for (int x1 = 0; x1 < n; ++x1) {
                int s = 0;
                for (int x2 = x1; x2 < n; ++x2) {
                    s = encoder.max(s, code[matrix[x2][y]]);
                    cols[x1][x2][y] = s;
                }
            }
        }
        long[] count = new long[encoder.maxValue()];
        for (int x1 = 0; x1 < n; ++x1) {
            for (int x2 = x1; x2 < n; ++x2) {
                for (int y1 = 0; y1 < m; ++y1) {
                    int s = 0;
                    for (int y2 = y1; y2 < m; ++y2) {
                        s = encoder.max(s, cols[x1][x2][y2]);
                        ++count[s];
                    }
                }
            }
        }
        return count;
    }
}
