package com.dumbear.chelper;

public class TrySail {
    private static final int MAX_STRENGTH = 256;

    public int get(int[] strength) {
        int all = 0;
        boolean[][] can = new boolean[MAX_STRENGTH][MAX_STRENGTH];
        can[0][0] = true;
        for (int s : strength) {
            all ^= s;
            boolean[][] next = new boolean[MAX_STRENGTH][MAX_STRENGTH];
            for (int x = 0; x < MAX_STRENGTH; ++x) {
                for (int y = 0; y < MAX_STRENGTH; ++y) {
                    if (can[x][y]) {
                        next[x ^ s][y] = true;
                        next[x][y ^ s] = true;
                        next[x][y] = true;
                    }
                }
            }
            can = next;
        }
        int result = 0;
        for (int x = 0; x < MAX_STRENGTH; ++x) {
            for (int y = 0; y < MAX_STRENGTH; ++y) {
                if (can[x][y]) {
                    result = Math.max(result, x + y + (all ^ x ^ y));
                }
            }
        }
        return result;
    }
}
