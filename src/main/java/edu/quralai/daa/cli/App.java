package edu.quralai.daa.cli;

import edu.quralai.daa.core.CsvWriter;
import edu.quralai.daa.core.Metrics;
import edu.quralai.daa.model.Point;
import edu.quralai.daa.select.DeterministicSelect;
import edu.quralai.daa.sort.MergeSort;
import edu.quralai.daa.sort.QuickSort;
import edu.quralai.daa.geometry.ClosestPair;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

public class App {

    public static void main(String[] args) throws IOException {
        Path out = Path.of("data/out/metrics.csv");

        try (CsvWriter csv = new CsvWriter(out, false)) {
            csv.header();

            // какие размеры гоним
            int[] sizes = {1_000, 5_000, 10_000};
            int repeats = 3; // по сколько сидов на размер

            // -------- SORTS: MergeSort / QuickSort --------
            for (int n : sizes) {
                for (int rep = 0; rep < repeats; rep++) {
                    int seed = 100 + rep;
                    int[] a1 = new Random(seed).ints(n, -1_000_000, 1_000_000).toArray();
                    int[] a2 = a1.clone();

                    // MergeSort
                    {
                        Metrics m = new Metrics();
                        MergeSort.sort(a1, m);
                        csv.row(row("mergesort", n, seed, m, ""));
                    }

                    // QuickSort
                    {
                        Metrics m = new Metrics();
                        QuickSort.sort(a2, m);
                        csv.row(row("quicksort", n, seed, m, ""));
                    }
                }
            }

            // -------- SELECT: Deterministic (MoM) --------
            for (int n : sizes) {
                for (int rep = 0; rep < repeats; rep++) {
                    int seed = 200 + rep;
                    int[] a = new Random(seed).ints(n, -1_000_000, 1_000_000).toArray();
                    int k = n / 3; // какой порядок берём
                    Metrics m = new Metrics();
                    int val = DeterministicSelect.select(a, k, m);
                    csv.row(row("select_mom5", n, seed, m, "k=" + k + ",val=" + val));
                }
            }

            // -------- GEOMETRY: Closest Pair --------
            for (int n : new int[]{2_000, 5_000}) { // для геометрии лучше поменьше
                for (int rep = 0; rep < repeats; rep++) {
                    int seed = 300 + rep;
                    Random rng = new Random(seed);
                    Point[] pts = new Point[n];
                    for (int i = 0; i < n; i++) {
                        pts[i] = new Point(rng.nextDouble(), rng.nextDouble());
                    }
                    Metrics m = new Metrics();
                    double d = ClosestPair.solve(pts, m);
                    csv.row(row("closest_pair", n, seed, m, "d=" + d));
                }
            }

            csv.flush();
        }

        System.out.println("CSV written to: " + out.toAbsolutePath());
    }

    private static String row(String algo, int n, int seed, Metrics m, String notes) {
        return String.format(
                "%s,%d,%d,%d,%d,%d,%d,%s",
                algo, n, seed,
                m.getElapsedNs(), m.getComparisons(), m.getAllocations(), m.getMaxDepth(),
                notes == null ? "" : notes
        );
    }
}
