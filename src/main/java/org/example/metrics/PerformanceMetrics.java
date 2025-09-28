package org.example.metrics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe performance metrics collector for algorithm analysis.
 * Tracks execution time, comparisons, array accesses, and memory allocations.
 */
public class PerformanceMetrics {
    private final AtomicLong comparisons = new AtomicLong(0);
    private final AtomicLong arrayAccesses = new AtomicLong(0);
    private final AtomicLong allocations = new AtomicLong(0);
    private final AtomicLong swaps = new AtomicLong(0);
    
    private long startTime;
    private long endTime;
    
    /**
     * Start timing the algorithm execution.
     */
    public void startTiming() {
        startTime = System.nanoTime();
    }
    
    /**
     * Stop timing the algorithm execution.
     */
    public void stopTiming() {
        endTime = System.nanoTime();
    }
    
    /**
     * Get execution time in nanoseconds.
     */
    public long getExecutionTimeNanos() {
        return endTime - startTime;
    }
    
    /**
     * Get execution time in milliseconds.
     */
    public double getExecutionTimeMillis() {
        return getExecutionTimeNanos() / 1_000_000.0;
    }
    
    /**
     * Increment comparison counter.
     */
    public void incrementComparisons() {
        comparisons.incrementAndGet();
    }
    
    /**
     * Increment comparison counter by specified amount.
     */
    public void incrementComparisons(long count) {
        comparisons.addAndGet(count);
    }
    
    /**
     * Get total number of comparisons.
     */
    public long getComparisons() {
        return comparisons.get();
    }
    
    /**
     * Increment array access counter.
     */
    public void incrementArrayAccesses() {
        arrayAccesses.incrementAndGet();
    }
    
    /**
     * Increment array access counter by specified amount.
     */
    public void incrementArrayAccesses(long count) {
        arrayAccesses.addAndGet(count);
    }
    
    /**
     * Get total number of array accesses.
     */
    public long getArrayAccesses() {
        return arrayAccesses.get();
    }
    
    /**
     * Increment allocation counter.
     */
    public void incrementAllocations() {
        allocations.incrementAndGet();
    }
    
    /**
     * Increment allocation counter by specified amount.
     */
    public void incrementAllocations(long count) {
        allocations.addAndGet(count);
    }
    
    /**
     * Get total number of allocations.
     */
    public long getAllocations() {
        return allocations.get();
    }
    
    /**
     * Increment swap counter.
     */
    public void incrementSwaps() {
        swaps.incrementAndGet();
    }
    
    /**
     * Increment swap counter by specified amount.
     */
    public void incrementSwaps(long count) {
        swaps.addAndGet(count);
    }
    
    /**
     * Get total number of swaps.
     */
    public long getSwaps() {
        return swaps.get();
    }
    
    /**
     * Reset all metrics to zero.
     */
    public void reset() {
        comparisons.set(0);
        arrayAccesses.set(0);
        allocations.set(0);
        swaps.set(0);
        startTime = 0;
        endTime = 0;
    }
    
    /**
     * Get a summary string of all metrics.
     */
    public String getSummary() {
        return String.format("Time: %.3fms, Comparisons: %d, ArrayAccesses: %d, Allocations: %d, Swaps: %d",
                getExecutionTimeMillis(), getComparisons(), getArrayAccesses(), getAllocations(), getSwaps());
    }
}
