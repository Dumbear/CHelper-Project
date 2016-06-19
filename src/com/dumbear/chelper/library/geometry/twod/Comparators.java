package com.dumbear.chelper.library.geometry.twod;

import java.util.Comparator;

public class Comparators {
    public static class RegionComparator implements Comparator<PointInt> {
        private PointInt center;

        public RegionComparator() {
            this(new PointInt(0, 0));
        }

        public RegionComparator(PointInt center) {
            this.center = center;
        }

        @Override
        public int compare(PointInt p1, PointInt p2) {
            int r1 = getRegion(p1.subtract(center));
            int r2 = getRegion(p2.subtract(center));
            return r1 < r2 ? -1 : (r1 > r2 ? 1 : 0);
        }

        public int getRegion(PointInt p) {
            return p.x > 0 ? (p.y >= 0 ? 0 : 1) : (p.y > 0 ? 0 : 1);
        }
    }

    public static class CrossComparator implements Comparator<PointInt> {
        private PointInt center;

        public CrossComparator() {
            this(new PointInt(0, 0));
        }

        public CrossComparator(PointInt center) {
            this.center = center;
        }

        @Override
        public int compare(PointInt p1, PointInt p2) {
            long c = (p1.subtract(center)).cross(p2.subtract(center));
            return c < 0 ? -1 : (c > 0 ? 1 : 0);
        }
    }

    public static class LengthComparator implements Comparator<PointInt> {
        private PointInt center;

        public LengthComparator() {
            this(new PointInt(0, 0));
        }

        public LengthComparator(PointInt center) {
            this.center = center;
        }

        @Override
        public int compare(PointInt p1, PointInt p2) {
            long l1 = (p1.subtract(center)).len2();
            long l2 = (p2.subtract(center)).len2();
            return l1 < l2 ? -1 : (l1 > l2 ? 1 : 0);
        }
    }

    public static Comparator<PointInt> getAngleComparator(PointInt center) {
        return new RegionComparator(center)
                .thenComparing(new CrossComparator(center))
                .thenComparing(new LengthComparator(center));
    }
}
