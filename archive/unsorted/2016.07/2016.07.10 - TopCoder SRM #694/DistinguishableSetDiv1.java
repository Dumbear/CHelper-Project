package com.dumbear.chelper;

public class DistinguishableSetDiv1 {
    public int count(String[] answer) {
        int n = answer.length;
        int m = answer[0].length();
        boolean[] bad = new boolean[1 << m];
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int s = 0;
                for (int k = 0; k < m; ++k) {
                    if (answer[i].charAt(k) == answer[j].charAt(k)) {
                        s |= 1 << k;
                    }
                }
                bad[s] = true;
            }
        }
        int count = 0;
        for (int i = bad.length - 1; i >= 0; --i) {
            if (bad[i]) {
                for (int j = i; j != 0; j ^= j & -j) {
                    bad[i ^ (j & -j)] = true;
                }
            } else {
                ++count;
            }
        }
        return count;
    }
}
