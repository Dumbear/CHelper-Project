package com.dumbear.chelper.workspace;

import com.dumbear.chelper.library.geometry.twod.Comparators;
import com.dumbear.chelper.library.geometry.twod.PointInt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BearBall {
    public int countThrows(int[] x, int[] y) {
        int n = x.length;
        int sumCount = 0;
        for (int i = 0; i < n; ++i) {
            PointInt center = new PointInt(x[i], y[i]);
            List<PointInt> ps = new ArrayList<>();
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    ps.add(new PointInt(x[j], y[j]));
                }
            }
            Comparator<PointInt> regionComparator = new Comparators.RegionComparator(center);
            Comparator<PointInt> crossComparator = new Comparators.CrossComparator(center);
            Comparator<PointInt> lengthComparator = new Comparators.LengthComparator(center);
            Comparator<PointInt> comparator = regionComparator.thenComparing(crossComparator).thenComparing(lengthComparator);
            ps.sort(comparator);
            boolean align = true;
            for (PointInt p : ps) {
                if (crossComparator.compare(p, ps.get(0)) != 0) {
                    align = false;
                    break;
                }
            }
            PointInt lastP = null;
            int lastCount = 0;
            for (PointInt p : ps) {
                if (lastP == null || regionComparator.thenComparing(crossComparator).compare(p, lastP) != 0) {
                    lastCount = 1;
                } else {
                    if (align) {
                        ++lastCount;
                    } else {
                        lastCount = 2;
                    }
                }
                lastP = p;
                sumCount += lastCount;
            }
        }
        return sumCount;
    }
}
