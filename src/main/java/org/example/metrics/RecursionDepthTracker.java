package org.example.metrics;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe recursion depth tracker for monitoring stack usage.
 * Tracks current depth and maximum depth reached during algorithm execution.
 */
public class RecursionDepthTracker {
    private final AtomicInteger currentDepth = new AtomicInteger(0);
    private final AtomicInteger maxDepth = new AtomicInteger(0);
    
    /**
     * Enter a recursive call - increment depth counter.
     * @return the new current depth
     */
    public int enterRecursion() {
        int newDepth = currentDepth.incrementAndGet();
        updateMaxDepth(newDepth);
        return newDepth;
    }
    
    /**
     * Exit a recursive call - decrement depth counter.
     * @return the new current depth
     */
    public int exitRecursion() {
        return currentDepth.decrementAndGet();
    }
    
    /**
     * Get current recursion depth.
     */
    public int getCurrentDepth() {
        return currentDepth.get();
    }
    
    /**
     * Get maximum recursion depth reached.
     */
    public int getMaxDepth() {
        return maxDepth.get();
    }
    
    /**
     * Reset depth counters to zero.
     */
    public void reset() {
        currentDepth.set(0);
        maxDepth.set(0);
    }
    
    /**
     * Update maximum depth if current depth exceeds it.
     */
    private void updateMaxDepth(int currentDepth) {
        int currentMax = maxDepth.get();
        while (currentDepth > currentMax && !maxDepth.compareAndSet(currentMax, currentDepth)) {
            currentMax = maxDepth.get();
        }
    }
    
    /**
     * Get a summary string of depth metrics.
     */
    public String getSummary() {
        return String.format("CurrentDepth: %d, MaxDepth: %d", getCurrentDepth(), getMaxDepth());
    }
}
