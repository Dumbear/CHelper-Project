package com.dumbear.chelper;

public class Sunnygraphs {
    public long count(int[] a) {
        int[] tag = new int[a.length];
        mark(0, tag, a);
        mark(1, tag, a);
        int[] count = new int[1 << 2];
        for (int i = 0; i < a.length; ++i) {
            ++count[tag[i]];
        }
        if (count[3] == 0) {
            return (pow2(count[1]) - 1) * (pow2(count[2]) - 1) * pow2(count[0]);
        } else {
            long result = 0;
            result += pow2(count[0]);
            result += (pow2(count[3]) - 1) * pow2(a.length - count[3]);
            result += (pow2(count[1]) - 1) * (pow2(count[2]) - 1) * pow2(count[0]);
            return result;
        }
    }

    void mark(int v, int[] tag, int[] a) {
        for (int i = v; ((tag[i] >> v) & 1) == 0; i = a[i]) {
            tag[i] |= 1 << v;
        }
    }

    long pow2(int x) {
        return 1L << x;
    }
}
