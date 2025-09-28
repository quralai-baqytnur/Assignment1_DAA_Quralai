package edu.quralai.daa.sort;

import edu.quralai.daa.core.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test void basic(){
        int[] a = {5,3,1,4,2};
        int[] b = a.clone(); Arrays.sort(b);
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(b, a);
    }

    @Test void random(){
        Random rng = new Random(1);
        int[] a = rng.ints(1000, -1000, 1000).toArray();
        int[] b = a.clone(); Arrays.sort(b);
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(b, a);
    }
}
