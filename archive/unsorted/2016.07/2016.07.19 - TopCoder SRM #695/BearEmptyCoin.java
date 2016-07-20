package com.dumbear.chelper.workspace;

import com.dumbear.chelper.library.numbertheory.Number;

public class BearEmptyCoin {
    public long winProbability(int K, int S) {
        if (S % K == 0) {
            return 1L << K;
        }
        long[][] comb = new long[K + 1][K + 1];
        for (int i = 0; i <= K; ++i) {
            comb[i][0] = 1;
            for (int j = 1; j <= i; ++j) {
                comb[i][j] = comb[i - 1][j] + comb[i - 1][j - 1];
            }
        }
        long res = 0;
        for (int partTwo = 1; partTwo < K; ++partTwo) {
            long maxComb = 0;
            for (int y = 0; y <= partTwo; ++y) {
                int x = K - y;
                if (S % Number.gcd(x, y) != 0) {
                    continue;
                }
                maxComb = Math.max(maxComb, comb[partTwo - 1][y - 1]);
            }
            res += maxComb;
        }
        return res * 2;
    }
}
