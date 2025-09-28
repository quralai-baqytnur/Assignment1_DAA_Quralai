package edu.quralai.daa.geometry;

import edu.quralai.daa.core.Metrics;
import edu.quralai.daa.model.Point;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {

    private static double brute(Point[] pts){
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x() - pts[j].x();
                double dy = pts[i].y() - pts[j].y();
                double d  = Math.hypot(dx, dy);
                if (d < best) best = d;
            }
        }
        return best;
    }

    @Test
    void small_n_equals_bruteforce() {
        Random rng = new Random(4);
        int n = 800; // малое n — можно сравнить с O(n^2)
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) pts[i] = new Point(rng.nextDouble(), rng.nextDouble());

        double fast = ClosestPair.solve(pts, new Metrics());
        double slow = brute(pts);
        assertEquals(slow, fast, 1e-9);
    }

    @Test
    void trivial_two_points() {
        Point[] pts = { new Point(0,0), new Point(3,4) };
        double d = ClosestPair.solve(pts, new Metrics());
        assertEquals(5.0, d, 1e-12);
    }
}
