package edu.quralai.daa.select;

import edu.quralai.daa.core.Metrics;
import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("Array is empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        m.start();
        int res = selectRange(a, 0, a.length - 1, k, m);
        m.stop();
        return res;
    }

    public static int select(int[] a, int k) {
        return select(a, k, new Metrics());
    }

    private static int selectRange(int[] a, int left, int right, int k, Metrics m) {
        while (true) {
            if (left == right) return a[left];
            int pivotIdx = medianOfMediansIndex(a, left, right, m);
            swap(a, pivotIdx, right, m);
            int p = partition(a, left, right, m);
            if (k == p) return a[p];
            else if (k < p) right = p - 1;
            else            left  = p + 1;
        }
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

    private static int medianOfMediansIndex(int[] a, int left, int right, Metrics m) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(a, left, right, m);
            return left + n / 2;
        }
        int numGroups = 0;
        for (int start = left; start <= right; start += 5) {
            int end = Math.min(start + 4, right);
            insertionSort(a, start, end, m);
            int medianIdx = start + (end - start) / 2;
            swap(a, left + numGroups, medianIdx, m);
            numGroups++;
        }
        int medOfMedsPos = left + numGroups / 2;
        return selectRangeIndexAmongMedians(a, left, left + numGroups - 1, medOfMedsPos, m);
    }

    private static int selectRangeIndexAmongMedians(int[] a, int L, int R, int targetPos, Metrics m) {
        int left = L, right = R, k = targetPos;
        while (true) {
            if (left == right) return left;
            int pivotIdx = medianOfMediansIndex(a, left, right, m);
            swap(a, pivotIdx, right, m);
            int p = partition(a, left, right, m);
            if (k == p) return p;
            else if (k < p) right = p - 1;
            else            left  = p + 1;
        }
    }

    private static void insertionSort(int[] a, int left, int right, Metrics m) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                m.cmp();
                if (a[j] <= key) break;
                a[j + 1] = a[j]; m.alloc();
                j--;
            }
            a[j + 1] = key; m.alloc();
        }
    }

    public static void main(String[] args) {
        int[] arr = {7, 2, 9, 4, 1, 5, 3, 8, 6, 0, 11, 10};
        int k = 5;
        int val = DeterministicSelect.select(arr, k, new Metrics());
        System.out.println("k-th smallest (k=" + k + ") = " + val);
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);
        System.out.println("Check: " + copy[k]);
    }
}
