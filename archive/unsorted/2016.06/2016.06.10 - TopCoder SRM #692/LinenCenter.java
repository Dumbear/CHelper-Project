package com.dumbear.chelper.workspace;

public class LinenCenter {
    static final int MODULO = 1000000009;

    public int countStrings(String S, int N, int K) {
        int[][] next = getNextArray(S);
        int[] count = countSingle(S, next, N, true);
        int[] suffixCount = countSingle(S, next, N, false);
        return countAll(count, suffixCount, N, K);
    }

    int[][] getNextArray(String s) {
        int[][] next = new int[s.length()][256];
        for (int i = 0; i < s.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                String prefix = s.substring(0, i) + c;
                for (int k = i + 1; k >= 0; --k) {
                    if (s.substring(0, k).equals(prefix.substring(i + 1 - k))) {
                        next[i][c] = k;
                        break;
                    }
                }
            }
        }
        return next;
    }

    int[] countSingle(String s, int[][] next, int n, boolean asPrefix) {
        int[][] count = new int[n + 1][s.length()];
        count[0][0] = 1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < s.length(); ++j) {
                for (char c = 'a'; c <= 'z'; ++c) {
                    int p = next[j][c];
                    if (p != s.length()) {
                        count[i + 1][p] = (count[i + 1][p] + count[i][j]) % MODULO;
                    }
                }
            }
        }
        int[] result = new int[n + 1];
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j < s.length(); ++j) {
                boolean isValid = true;
                int p = j;
                for (int k = 0; k < s.length() - 1; ++k) {
                    p = next[p][s.charAt(k)];
                    if (p == s.length()) {
                        isValid = false;
                        break;
                    }
                }
                if (!asPrefix || isValid) {
                    result[i] = (result[i] + count[i][j]) % MODULO;
                }
            }
        }
        return result;
    }

    int countAll(int[] count, int[] suffixCount, int n, int k) {
        int[] factor = count;
        int[] p = new int[n + 1];
        p[0] = 1;
        for (int i = k; i != 0; i >>= 1) {
            if ((i & 1) == 1) {
                p = multiply(p, factor);
            }
            factor = multiply(factor, factor);
        }
        p = multiply(p, suffixCount);
        int result = 0;
        for (int i = 0; i <= n; ++i) {
            result = (result + p[i]) % MODULO;
        }
        return result;
    }

    int[] multiply(int[] p1, int[] p2) {
        int[] p = new int[p1.length];
        for (int i = 0; i < p1.length; ++i) {
            for (int j = 0; j < p2.length; ++j) {
                if (i + j < p.length) {
                    p[i + j] = (p[i + j] + (int) ((long) p1[i] * p2[j] % MODULO)) % MODULO;
                }
            }
        }
        return p;
    }
}
