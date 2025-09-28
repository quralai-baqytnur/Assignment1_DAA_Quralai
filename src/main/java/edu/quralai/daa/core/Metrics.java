package edu.quralai.daa.core;

public class Metrics {
    private long comparisons, allocations, depth, maxDepth, start, elapsed;

    public void cmp(){ comparisons++; }
    public void alloc(){ allocations++; }
    public void enter(){ depth++; if (depth > maxDepth) maxDepth = depth; }
    public void exit(){ if (depth > 0) depth--; }
    public void start(){ start = System.nanoTime(); }
    public void stop(){ elapsed = System.nanoTime() - start; }

    public long getComparisons(){ return comparisons; }
    public long getAllocations(){ return allocations; }
    public long getMaxDepth(){ return maxDepth; }
    public long getElapsedNs(){ return elapsed; }
}
