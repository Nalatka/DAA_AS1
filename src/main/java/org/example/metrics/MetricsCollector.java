package org.example.metrics;

/**
 * Central metrics collector that combines performance metrics and recursion tracking.
 * Provides a unified interface for algorithm instrumentation.
 */
public class MetricsCollector {
    private final PerformanceMetrics performanceMetrics;
    private final RecursionDepthTracker depthTracker;
    
    public MetricsCollector() {
        this.performanceMetrics = new PerformanceMetrics();
        this.depthTracker = new RecursionDepthTracker();
    }
    
    /**
     * Get performance metrics instance.
     */
    public PerformanceMetrics getPerformanceMetrics() {
        return performanceMetrics;
    }
    
    /**
     * Get recursion depth tracker instance.
     */
    public RecursionDepthTracker getDepthTracker() {
        return depthTracker;
    }
    
    /**
     * Start timing and reset all metrics.
     */
    public void start() {
        performanceMetrics.reset();
        depthTracker.reset();
        performanceMetrics.startTiming();
    }
    
    /**
     * Stop timing.
     */
    public void stop() {
        performanceMetrics.stopTiming();
    }
    
    /**
     * Enter recursion - convenience method.
     */
    public int enterRecursion() {
        return depthTracker.enterRecursion();
    }
    
    /**
     * Exit recursion - convenience method.
     */
    public int exitRecursion() {
        return depthTracker.exitRecursion();
    }
    
    /**
     * Increment comparisons - convenience method.
     */
    public void incrementComparisons() {
        performanceMetrics.incrementComparisons();
    }
    
    /**
     * Increment comparisons by count - convenience method.
     */
    public void incrementComparisons(long count) {
        performanceMetrics.incrementComparisons(count);
    }
    
    /**
     * Increment array accesses - convenience method.
     */
    public void incrementArrayAccesses() {
        performanceMetrics.incrementArrayAccesses();
    }
    
    /**
     * Increment array accesses by count - convenience method.
     */
    public void incrementArrayAccesses(long count) {
        performanceMetrics.incrementArrayAccesses(count);
    }
    
    /**
     * Increment allocations - convenience method.
     */
    public void incrementAllocations() {
        performanceMetrics.incrementAllocations();
    }
    
    /**
     * Increment allocations by count - convenience method.
     */
    public void incrementAllocations(long count) {
        performanceMetrics.incrementAllocations(count);
    }
    
    /**
     * Increment swaps - convenience method.
     */
    public void incrementSwaps() {
        performanceMetrics.incrementSwaps();
    }
    
    /**
     * Increment swaps by count - convenience method.
     */
    public void incrementSwaps(long count) {
        performanceMetrics.incrementSwaps(count);
    }
    
    /**
     * Get comprehensive summary of all metrics.
     */
    public String getSummary() {
        return String.format("Performance: %s | Depth: %s", 
                performanceMetrics.getSummary(), 
                depthTracker.getSummary());
    }
    
    /**
     * Reset all metrics.
     */
    public void reset() {
        performanceMetrics.reset();
        depthTracker.reset();
    }
}
