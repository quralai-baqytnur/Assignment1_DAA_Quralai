package edu.quralai.daa.sort;

import edu.quralai.daa.core.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void basic() {
        int[] a = {5, 3, 1, 4, 2};
        int[] b = a.clone(); Arrays.sort(b);
        QuickSort.sort(a, new Metrics());
        assertArrayEquals(b, a);
    }

    @Test
    void random_and_depth_bound() {
        Random rng = new Random(2);
        int n = 30_000;
        int[] a = rng.ints(n, -1_000_000, 1_000_000).toArray();
        int[] b = a.clone(); Arrays.sort(b);

        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertArrayEquals(b, a);

        // мягкая верхняя граница глубины стека для рандом-пивота
        int bound = (int) (2 * (Math.log(n) / Math.log(2))) + 8;
        assertTrue(m.getMaxDepth() <= bound, "depth too big: " + m.getMaxDepth());
    }
}
