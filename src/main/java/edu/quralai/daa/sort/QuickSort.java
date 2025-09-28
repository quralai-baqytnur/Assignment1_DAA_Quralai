

package edu.quralai.daa.sort;

import edu.quralai.daa.core.Metrics;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    public static void sort(int[] arr, Metrics m) {
        if (arr == null || arr.length < 2) return;
        m.start();
        quickSort(arr, 0, arr.length - 1, m);
        m.stop();
    }

    private static void quickSort(int[] a, int left, int right, Metrics m) {
        m.enter();
        while (left < right) {
            int pIdx = ThreadLocalRandom.current().nextInt(left, right + 1);
            swap(a, pIdx, right, m);
            int pivotPos = partition(a, left, right, m);
            int leftSize = pivotPos - 1 - left + 1;
            int rightSize = right - (pivotPos + 1) + 1;
            if (leftSize < rightSize) {
                quickSort(a, left, pivotPos - 1, m);
                left = pivotPos + 1;
            } else {
                quickSort(a, pivotPos + 1, right, m);
                right = pivotPos - 1;
            }
        }
        m.exit();
    }
    private static int partition(int[] a, int left, int right, Metrics m) {
        int pivot = a[right];
        int i = left;
        for (int j = left; j < right; j++) {
            m.cmp();
            if (a[j] <= pivot) {
                swap(a, i, j, m);
                i++;
            }
        }
        swap(a, i, right, m);
        return i;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
        m.alloc();
    }
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 7, 3, 8, 4, 6, 0, 5, 2};
        QuickSort.sort(arr, new Metrics());
        for (int x : arr) System.out.print(x + " ");
    }
}
