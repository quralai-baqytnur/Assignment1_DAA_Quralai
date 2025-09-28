package edu.quralai.daa.select;

import edu.quralai.daa.core.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void hundred_trials_matches_sorted_k() {
        Random rng = new Random(3);
        for (int t = 0; t < 100; t++) {
            int n = 1500 + rng.nextInt(500);        // 1500..1999
            int[] a = rng.ints(n, -1_000_000, 1_000_000).toArray();
            int[] b = a.clone(); Arrays.sort(b);
            int k = rng.nextInt(n);

            int got = DeterministicSelect.select(a, k, new Metrics());
            assertEquals(b[k], got, "k-th order statistic mismatch at trial " + t);
        }
    }

    @Test
    void edge_cases() {
        int[] a1 = {42};
        assertEquals(42, DeterministicSelect.select(a1, 0, new Metrics()));

        int[] a2 = {7, 7, 7, 7, 7};
        assertEquals(7, DeterministicSelect.select(a2, 3, new Metrics()));

        int[] a3 = {-5, 10, 0, 3, 3, 2};
        int[] b3 = a3.clone(); Arrays.sort(b3);
        assertEquals(b3[0], DeterministicSelect.select(a3.clone(), 0, new Metrics())); // min
        assertEquals(b3[b3.length-1], DeterministicSelect.select(a3.clone(), b3.length-1, new Metrics())); // max
    }
}
