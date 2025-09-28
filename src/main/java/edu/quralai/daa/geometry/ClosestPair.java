package edu.quralai.daa.geometry;

import edu.quralai.daa.core.Metrics;
import edu.quralai.daa.model.Point;

import java.util.*;

public class ClosestPair {

    public static double solve(Point[] pts, Metrics m) {
        return closest(pts, m);
    }

    public static double solve(Point[] pts) {
        return solve(pts, new Metrics());
    }

    public static double closest(Point[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        m.start();
        Arrays.sort(pts, Comparator.comparingDouble(Point::x));
        m.alloc();
        double res = solveRec(pts, 0, pts.length - 1, m);
        m.stop();
        return res;
    }

    public static double closest(Point[] pts) {
        return closest(pts, new Metrics());
    }

    private static double solveRec(Point[] pts, int l, int r, Metrics m) {
        int n = r - l + 1;
        if (n <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = l; i <= r; i++) {
                for (int j = i + 1; j <= r; j++) {
                    m.cmp();
                    d = Math.min(d, dist(pts[i], pts[j], m));
                }
            }
            Arrays.sort(pts, l, r + 1, Comparator.comparingDouble(Point::y));
            return d;
        }

        int mid = (l + r) / 2;
        double midX = pts[mid].x();
        double dl = solveRec(pts, l, mid, m);
        double dr = solveRec(pts, mid + 1, r, m);
        double d = Math.min(dl, dr);

        Point[] temp = new Point[n];
        mergeByY(pts, l, mid, r, temp, m);

        List<Point> strip = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            if (Math.abs(pts[i].x() - midX) < d) strip.add(pts[i]);
        }
        m.alloc();

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && j <= i + 7; j++) {
                m.cmp();
                d = Math.min(d, dist(strip.get(i), strip.get(j), m));
            }
        }
        return d;
    }

    private static void mergeByY(Point[] pts, int l, int mid, int r, Point[] temp, Metrics m) {
        int i = l, j = mid + 1, k = 0;
        while (i <= mid && j <= r) {
            m.cmp();
            if (pts[i].y() <= pts[j].y()) temp[k++] = pts[i++];
            else                          temp[k++] = pts[j++];
        }
        while (i <= mid) temp[k++] = pts[i++];
        while (j <= r)   temp[k++] = pts[j++];
        System.arraycopy(temp, 0, pts, l, r - l + 1);
        m.alloc();
    }

    private static double dist(Point a, Point b, Metrics m) {
        m.cmp();
        double dx = a.x() - b.x(), dy = a.y() - b.y();
        return Math.hypot(dx, dy);
    }

    // 🔹 Добавленный main для строкового вывода
    public static void main(String[] args) {
        Point[] pts = {
                new Point(2, 3), new Point(12, 30), new Point(40, 50),
                new Point(5, 1), new Point(12, 10), new Point(3, 4),
                new Point(6, 2), new Point(7, 9)
        };
        Metrics m = new Metrics();
        double d = ClosestPair.solve(pts, m);
        System.out.println("Closest distance = " + d);
        System.out.println("Comparisons: " + m.getComparisons() +
                ", Allocations: " + m.getAllocations() +
                ", Max depth: " + m.getMaxDepth());
    }
}
