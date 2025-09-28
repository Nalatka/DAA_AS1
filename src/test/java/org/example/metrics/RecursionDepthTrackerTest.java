package org.example.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecursionDepthTrackerTest {
    private RecursionDepthTracker tracker;
    
    @BeforeEach
    public void setUp() {
        tracker = new RecursionDepthTracker();
    }
    
    @Test
    public void testInitialState() {
        assertEquals(0, tracker.getCurrentDepth());
        assertEquals(0, tracker.getMaxDepth());
    }
    
    @Test
    public void testEnterExitRecursion() {
        int depth1 = tracker.enterRecursion();
        assertEquals(1, depth1);
        assertEquals(1, tracker.getCurrentDepth());
        assertEquals(1, tracker.getMaxDepth());
        
        int depth2 = tracker.enterRecursion();
        assertEquals(2, depth2);
        assertEquals(2, tracker.getCurrentDepth());
        assertEquals(2, tracker.getMaxDepth());
        
        int depth3 = tracker.exitRecursion();
        assertEquals(1, depth3);
        assertEquals(1, tracker.getCurrentDepth());
        assertEquals(2, tracker.getMaxDepth());
        
        int depth4 = tracker.exitRecursion();
        assertEquals(0, depth4);
        assertEquals(0, tracker.getCurrentDepth());
        assertEquals(2, tracker.getMaxDepth());
    }
    
    @Test
    public void testMaxDepthTracking() {
        tracker.enterRecursion(); // depth 1
        tracker.enterRecursion(); // depth 2
        tracker.enterRecursion(); // depth 3
        assertEquals(3, tracker.getMaxDepth());
        
        tracker.exitRecursion(); // depth 2
        tracker.exitRecursion(); // depth 1
        assertEquals(3, tracker.getMaxDepth()); // Max depth should remain 3
        
        tracker.enterRecursion(); // depth 2
        tracker.enterRecursion(); // depth 3
        tracker.enterRecursion(); // depth 4
        assertEquals(4, tracker.getMaxDepth()); // New max depth
    }
    
    @Test
    public void testReset() {
        tracker.enterRecursion();
        tracker.enterRecursion();
        tracker.enterRecursion();
        
        tracker.reset();
        
        assertEquals(0, tracker.getCurrentDepth());
        assertEquals(0, tracker.getMaxDepth());
    }
    
    @Test
    public void testSummary() {
        tracker.enterRecursion();
        tracker.enterRecursion();
        
        String summary = tracker.getSummary();
        assertTrue(summary.contains("CurrentDepth: 2"));
        assertTrue(summary.contains("MaxDepth: 2"));
    }
}
