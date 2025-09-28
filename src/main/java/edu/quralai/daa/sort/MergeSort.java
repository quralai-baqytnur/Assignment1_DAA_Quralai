package edu.quralai.daa.sort;

import edu.quralai.daa.core.Metrics;

public class MergeSort {

    private static final int CUTOFF = 16;

    public static void sort(int[] arr, Metrics m) {
        if (arr == null || arr.length < 2) return;
        m.start();
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1, m);
        m.stop();
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, Metrics m) {
        m.enter();
        if (right - left + 1 <= CUTOFF) {
            insertionSort(arr, left, right, m);
            m.exit();
            return;
        }
        int mid = (left + right) >>> 1;
        mergeSort(arr, buffer, left, mid, m);
        mergeSort(arr, buffer, mid + 1, right, m);

        m.cmp();
        if (arr[mid] <= arr[mid + 1]) { m.exit(); return; }

        merge(arr, buffer, left, mid, right, m);
        m.exit();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics m) {
        for (int i = left; i <= right; i++) { buffer[i] = arr[i]; m.alloc(); }

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            m.cmp();
            if (buffer[i] <= buffer[j]) { arr[k++] = buffer[i++]; m.alloc(); }
            else                        { arr[k++] = buffer[j++]; m.alloc(); }
        }
        while (i <= mid) { arr[k++] = buffer[i++]; m.alloc(); }
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics m) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                m.cmp();
                if (arr[j] <= key) break;
                arr[j + 1] = arr[j]; m.alloc();
                j--;
            }
            arr[j + 1] = key; m.alloc();
        }
    }
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 7, 3, 8, 4, 6, 0};
        MergeSort.sort(arr, new Metrics());
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }
}
