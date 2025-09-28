package org.example.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MetricsCollectorTest {
    private MetricsCollector collector;
    
    @BeforeEach
    public void setUp() {
        collector = new MetricsCollector();
    }
    
    @Test
    public void testStartStop() {
        collector.start();
        assertTrue(collector.getPerformanceMetrics().getExecutionTimeNanos() >= 0);
        
        collector.stop();
        assertTrue(collector.getPerformanceMetrics().getExecutionTimeNanos() > 0);
    }
    
    @Test
    public void testRecursionTracking() {
        collector.start();
        
        int depth1 = collector.enterRecursion();
        assertEquals(1, depth1);
        assertEquals(1, collector.getDepthTracker().getCurrentDepth());
        assertEquals(1, collector.getDepthTracker().getMaxDepth());
        
        int depth2 = collector.enterRecursion();
        assertEquals(2, depth2);
        assertEquals(2, collector.getDepthTracker().getCurrentDepth());
        assertEquals(2, collector.getDepthTracker().getMaxDepth());
        
        int depth3 = collector.exitRecursion();
        assertEquals(1, depth3);
        assertEquals(1, collector.getDepthTracker().getCurrentDepth());
        assertEquals(2, collector.getDepthTracker().getMaxDepth());
        
        collector.stop();
    }
    
    @Test
    public void testPerformanceTracking() {
        collector.start();
        
        collector.incrementComparisons(5);
        collector.incrementArrayAccesses(10);
        collector.incrementAllocations(2);
        collector.incrementSwaps(3);
        
        assertEquals(5, collector.getPerformanceMetrics().getComparisons());
        assertEquals(10, collector.getPerformanceMetrics().getArrayAccesses());
        assertEquals(2, collector.getPerformanceMetrics().getAllocations());
        assertEquals(3, collector.getPerformanceMetrics().getSwaps());
        
        collector.stop();
    }
    
    @Test
    public void testReset() {
        collector.start();
        collector.enterRecursion();
        collector.enterRecursion();
        collector.incrementComparisons(10);
        collector.incrementArrayAccesses(20);
        
        collector.reset();
        
        assertEquals(0, collector.getDepthTracker().getCurrentDepth());
        assertEquals(0, collector.getDepthTracker().getMaxDepth());
        assertEquals(0, collector.getPerformanceMetrics().getComparisons());
        assertEquals(0, collector.getPerformanceMetrics().getArrayAccesses());
    }
    
    @Test
    public void testSummary() {
        collector.start();
        collector.enterRecursion();
        collector.incrementComparisons(5);
        collector.incrementArrayAccesses(10);
        collector.stop();
        
        String summary = collector.getSummary();
        assertTrue(summary.contains("Performance:"));
        assertTrue(summary.contains("Depth:"));
        assertTrue(summary.contains("Comparisons: 5"));
        assertTrue(summary.contains("ArrayAccesses: 10"));
        assertTrue(summary.contains("CurrentDepth: 0"));
        assertTrue(summary.contains("MaxDepth: 1"));
    }
}
