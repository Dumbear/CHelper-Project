package com.dumbear.chelper.workspace;

public class BearPasswordLexic {
    public String findPassword(int[] x) {
        int total = 0;
        for (int i = x.length - 1; i >= 0; --i) {
            if (x[i] < 0) {
                return "";
            } else if (x[i] > 0) {
                total += x[i] * (i + 1);
                for (int j = 0; j < i; ++j) {
                    x[j] -= x[i] * (i - j + 1);
                }
            }
        }
        if (total != x.length) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        while (result.length() < x.length) {
            for (int i = x.length - 1; i >= 0; --i) {
                if (x[i] != 0) {
                    --x[i];
                    for (int j = 0; j <= i; ++j) {
                        result.append('a');
                    }
                    break;
                }
            }
            for (int i = 0; i < x.length; ++i) {
                if (x[i] != 0) {
                    --x[i];
                    for (int j = 0; j <= i; ++j) {
                        result.append('b');
                    }
                    break;
                }
            }
        }
        return result.toString();
    }
}
