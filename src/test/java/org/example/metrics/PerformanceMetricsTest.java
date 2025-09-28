package org.example.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PerformanceMetricsTest {
    private PerformanceMetrics metrics;
    
    @BeforeEach
    public void setUp() {
        metrics = new PerformanceMetrics();
    }
    
    @Test
    public void testInitialState() {
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getArrayAccesses());
        assertEquals(0, metrics.getAllocations());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getExecutionTimeNanos());
    }
    
    @Test
    public void testTiming() throws InterruptedException {
        metrics.startTiming();
        Thread.sleep(10); // Sleep for 10ms
        metrics.stopTiming();
        
        assertTrue(metrics.getExecutionTimeNanos() > 0);
        assertTrue(metrics.getExecutionTimeMillis() > 0);
    }
    
    @Test
    public void testIncrementComparisons() {
        metrics.incrementComparisons();
        assertEquals(1, metrics.getComparisons());
        
        metrics.incrementComparisons(5);
        assertEquals(6, metrics.getComparisons());
    }
    
    @Test
    public void testIncrementArrayAccesses() {
        metrics.incrementArrayAccesses();
        assertEquals(1, metrics.getArrayAccesses());
        
        metrics.incrementArrayAccesses(3);
        assertEquals(4, metrics.getArrayAccesses());
    }
    
    @Test
    public void testIncrementAllocations() {
        metrics.incrementAllocations();
        assertEquals(1, metrics.getAllocations());
        
        metrics.incrementAllocations(2);
        assertEquals(3, metrics.getAllocations());
    }
    
    @Test
    public void testIncrementSwaps() {
        metrics.incrementSwaps();
        assertEquals(1, metrics.getSwaps());
        
        metrics.incrementSwaps(4);
        assertEquals(5, metrics.getSwaps());
    }
    
    @Test
    public void testReset() {
        metrics.incrementComparisons(10);
        metrics.incrementArrayAccesses(20);
        metrics.incrementAllocations(5);
        metrics.incrementSwaps(3);
        
        metrics.reset();
        
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getArrayAccesses());
        assertEquals(0, metrics.getAllocations());
        assertEquals(0, metrics.getSwaps());
    }
    
    @Test
    public void testSummary() {
        metrics.incrementComparisons(5);
        metrics.incrementArrayAccesses(10);
        metrics.incrementAllocations(2);
        metrics.incrementSwaps(3);
        
        String summary = metrics.getSummary();
        assertTrue(summary.contains("Comparisons: 5"));
        assertTrue(summary.contains("ArrayAccesses: 10"));
        assertTrue(summary.contains("Allocations: 2"));
        assertTrue(summary.contains("Swaps: 3"));
    }
}
